package com.example.orderservice.orderMenager.business.service;


import com.example.orderservice.OrderServiceApplication;
import com.example.orderservice.orderMenager.api.request.DateAndHourOfReservationRequest;
//import com.example.orderservice.orderMenager.data.repository.OrderRepo;
import com.example.orderservice.orderMenager.api.request.OrderNameProductRequest;
import com.example.orderservice.orderMenager.api.request.OrderRequest;
import com.example.orderservice.orderMenager.api.request.QRMailRequest;
import com.example.orderservice.orderMenager.api.response.Link;
import com.example.orderservice.orderMenager.api.response.OrderNameProductResponse;
import com.example.orderservice.orderMenager.business.exception.date.DateIncorrectException;
import com.example.orderservice.orderMenager.business.exception.frame.FrameNotFoundException;
import com.example.orderservice.orderMenager.business.exception.offer.OfferNotFoundException;
import com.example.orderservice.orderMenager.business.exception.order.OrderNotFoundException;
import com.example.orderservice.orderMenager.business.exception.paypal.PaypalErrorException;
import com.example.orderservice.orderMenager.business.service.hoursStrategyPackage.AtLeastDayHourStrategy;
import com.example.orderservice.orderMenager.business.service.hoursStrategyPackage.HoursStrategy;
import com.example.orderservice.orderMenager.business.service.hoursStrategyPackage.LessThanDayHourStrategy;
import com.example.orderservice.orderMenager.data.entity.UserOrder;
import com.example.orderservice.orderMenager.data.repository.UserOrderRepo;
import com.example.orderservice.orderMenager.feignClient.MailServiceFeignClient;
import com.example.orderservice.orderMenager.feignClient.OfferServiceFeignClient;
import com.example.orderservice.security.business.exception.authorize.AuthorizationException;
import com.example.orderservice.security.business.request.UserByIdRequest;
import com.example.orderservice.security.business.service.JwtTokenNonUserOrderProvider;
import com.example.orderservice.security.business.service.JwtTokenNonUserProvider;
import com.example.orderservice.security.business.service.JwtTokenProvider;
import com.example.orderservice.userMenager.api.request.User;
import com.example.orderservice.userMenager.business.exception.user.UserNotFoundException;
import com.example.orderservice.userMenager.feignClient.UserServiceFeignClient;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {

    private JwtTokenNonUserOrderProvider jwtTokenNonUserOrderProvider;
    private JwtTokenNonUserProvider jwtTokenNonUserProvider;
    private JwtTokenProvider jwtTokenProvider;
    private UserOrderRepo userOrderRepo;
    private OfferServiceFeignClient offerServiceFeignClient;
    private QrImageService qrImageService;
    private MailServiceFeignClient mailServiceFeignClient;

    private final String SUCCESS_PAYMENT_URL = OrderServiceApplication.MAIN_SITE + "order/pay/success?orderId=";
    private final String CANCEL_PAYMENT_URL = OrderServiceApplication.MAIN_SITE + "order/pay/cancel?orderId=";

    private final int PAYMENT_TIME = 30;
    private APIContext apiContext;

    private UserServiceFeignClient userServiceFeignClient;
    public List<String> getAvailableHours(DateAndHourOfReservationRequest dateAndHourOfReservationRequest) {
        if (jwtTokenNonUserOrderProvider.validateToken(dateAndHourOfReservationRequest.getToken())) {
            if (!jwtTokenNonUserOrderProvider.isTokenExpire(dateAndHourOfReservationRequest.getToken()) &&
                    "COMPUTER".equalsIgnoreCase(jwtTokenNonUserOrderProvider.extractUserIdName(dateAndHourOfReservationRequest.getToken()))) {
                Date from = new Date(dateAndHourOfReservationRequest.getReservationTime().getTime());
                from.setHours(0);
                from.setMinutes(0);
                Date to = new Date(dateAndHourOfReservationRequest.getReservationTime().getTime());
                to.setHours(23);
                to.setMinutes(59);
                List<UserOrder> userOrdersFromToday = userOrderRepo.findByBeginOrderBetween(from,to);

                userOrdersFromToday = userOrdersFromToday.stream().filter(userOrder -> {
                            String bikeId = jwtTokenNonUserOrderProvider.extractValueFromClaims(userOrder.getTransactionToken(), "bikeId");
                            String bikeFrameId = jwtTokenNonUserOrderProvider.extractValueFromClaims(userOrder.getTransactionToken(), "bikeFrameId");
                            return  bikeId.equalsIgnoreCase(String.valueOf(dateAndHourOfReservationRequest.getBikeId())) &&
                                    bikeFrameId.equalsIgnoreCase(String.valueOf(dateAndHourOfReservationRequest.getBikeFrameId()));

                })
                        .collect(Collectors.toList());

                HoursStrategy hoursStrategy = null;

                if (dateAndHourOfReservationRequest.getReservationRange().equalsIgnoreCase("doba") ||
                        dateAndHourOfReservationRequest.getReservationRange().equalsIgnoreCase("dzień"))
                    hoursStrategy = new AtLeastDayHourStrategy();
                else
                    hoursStrategy = new LessThanDayHourStrategy();
                return hoursStrategy.getAvailableHours(dateAndHourOfReservationRequest.getReservationRange(), userOrdersFromToday,
                        dateAndHourOfReservationRequest, jwtTokenNonUserOrderProvider);

            } else throw new AuthorizationException();
        } else throw new AuthorizationException();

    }

    public Link makeOrder(OrderRequest orderRequest, HttpServletRequest httpServletRequest) {
        isDateValid(orderRequest.getBeginDateOrder(), orderRequest.getEndDateOrder());

        String token = httpServletRequest.getHeader("Authorization");

        if (token != null) {
            token = token.substring(7);
            Long userId = jwtTokenProvider.extractUserId(token);
            User user = userServiceFeignClient.getUserById(new UserByIdRequest(userId, jwtTokenNonUserProvider.generateToken()));
            if (user == null) throw new UserNotFoundException(" with id: " + userId);
            Boolean isBike = offerServiceFeignClient.isProductWithIdAndType("bike", orderRequest.getBikeId());
            if(!isBike) throw new OfferNotFoundException();
            Map<String, Object> claims = new HashMap<>();
            Boolean accessory = null;
            if(orderRequest.getAccessoryId()==null) claims.put("accessoryId", "");
            else {
                accessory = offerServiceFeignClient.isProductWithIdAndType("accessory", orderRequest.getAccessoryId());
                if(accessory == null) throw new OfferNotFoundException();
                claims.put("accessoryId", orderRequest.getAccessoryId());
            }
            claims.put("bikeId", orderRequest.getBikeId());
            claims.put("userId", user.getUserId());
            Integer frameId = offerServiceFeignClient.isFrameInBike(orderRequest.getSelectedFrameOption(), orderRequest.getBikeId());
            if(frameId<0) throw new FrameNotFoundException(orderRequest.getSelectedFrameOption(), orderRequest.getBikeId());
            claims.put("bikeFrameId", frameId);
            String orderToken = jwtTokenNonUserOrderProvider.generateToken(claims);
            LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(PAYMENT_TIME);

            UserOrder userOrder = UserOrder.builder()
                    .beginOrder(orderRequest.getBeginDateOrder())
                    .endOrder(orderRequest.getEndDateOrder())
                    .transactionToken(orderToken)
                    .paid(false)
                    .timeToPaid(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()))
                    .price(orderRequest.getPrice())
                    .currency("PLN")
                    .build();

            userOrder = userOrderRepo.save(userOrder);
            return buyTicket(orderRequest, userOrder.getOrderId());

        }
        throw new AuthorizationException();
    }

    private Link buyTicket(OrderRequest orderRequest, Long orderId) {
        try {
            Payment payment = createPayment(orderRequest, orderId);
            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    return new Link(link.getHref());
                }
            }
        } catch (PayPalRESTException ex) {
            throw new PaypalErrorException(ex.getMessage());
        }
        return new Link("");
    }

    private Payment createPayment(OrderRequest orderRequest, Long orderId) throws PayPalRESTException {


        Amount amount = new Amount();
        amount.setCurrency("PLN");
        amount.setTotal(String.valueOf(orderRequest.getPrice()));

        Transaction transaction = new Transaction();
        transaction.setDescription(getPaymentDescription(orderRequest));
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(CANCEL_PAYMENT_URL + orderId);
        redirectUrls.setReturnUrl(SUCCESS_PAYMENT_URL + orderId);
        payment.setRedirectUrls(redirectUrls);
        return payment.create(apiContext);
    }

    private String getPaymentDescription(OrderRequest orderRequest) {
        return "Zarezerwowano rower o id: "+orderRequest.getBikeId()+ " i rezerwacja trwa od: "+
                orderRequest.getBeginDateOrder()+" do "+orderRequest.getEndDateOrder();
    }

    private void isDateValid(Date beginDateOrder, Date endDateOrder) {
        if(beginDateOrder.after(endDateOrder)) throw new DateIncorrectException(beginDateOrder, endDateOrder);
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecute);
    }

    public void changeStatusOfOrder(Long orderId) {

        UserOrder userOrder = userOrderRepo.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));

        String userIdStr = jwtTokenNonUserOrderProvider.extractValueFromClaims(userOrder.getTransactionToken(),"userId");
        String bikeIdStr = jwtTokenNonUserOrderProvider.extractValueFromClaims(userOrder.getTransactionToken(),"bikeId");
        String accessoryIdStr = jwtTokenNonUserOrderProvider.extractValueFromClaims(userOrder.getTransactionToken(),"accessoryId");

        Long userId = Long.parseLong(userIdStr);

        User user = userServiceFeignClient.getUserById(new UserByIdRequest(userId, jwtTokenNonUserProvider.generateToken()));
        Long accessoryIdLong = null;
        if(!StringUtils.isBlank(accessoryIdStr)) accessoryIdLong = Long.parseLong(accessoryIdStr);
        OrderNameProductResponse orderNameProductResponse = offerServiceFeignClient.getOrderNames(
                new OrderNameProductRequest(Long.parseLong(bikeIdStr), accessoryIdLong));

        byte[] qrImage = qrImageService.createQrImage(userOrder, user);

        userOrder.setPaid(true);
        userOrderRepo.save(userOrder);
        QRMailRequest qrMailRequest = QRMailRequest.builder()
                .orderId(orderId)
                .beginOrder(userOrder.getBeginOrder())
                .currency(userOrder.getCurrency())
                .endOrder(userOrder.getEndOrder())
                .price(userOrder.getPrice())
                .mailTo(user.getEmail())
                .bikeName(orderNameProductResponse.getBike())
                .frameName(orderNameProductResponse.getFrame())
                .accessoryName(orderNameProductResponse.getAccessory())
                .image(qrImage)
                .token(jwtTokenNonUserProvider.generateToken())
                .build();
        mailServiceFeignClient.sendQrCode(qrMailRequest);
    }

    public void cancelOrder(Long orderId) {
        UserOrder userOrder = userOrderRepo.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
        userOrderRepo.delete(userOrder);
    }
}