package com.example.orderservice.orderMenager.api.controller;

import com.example.orderservice.OrderServiceApplication;
import com.example.orderservice.orderMenager.api.request.OrderRepairBikeRequest;
import com.example.orderservice.orderMenager.api.request.OrderRequest;
import com.example.orderservice.orderMenager.api.response.AvailableHoursResponse;
import com.example.orderservice.orderMenager.api.response.OrderHistory;
import com.example.orderservice.orderMenager.api.response.OrderHistoryResponse;
import com.example.orderservice.orderMenager.api.response.ResponseView;
import com.example.orderservice.orderMenager.business.service.OrderService;
import com.example.orderservice.orderMenager.api.request.DateAndHourOfReservationRequest;
import com.example.orderservice.orderMenager.business.service.UserOrderService;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;
    private UserOrderService userOrderService;

    
    @PostMapping("/available-hours")
    public AvailableHoursResponse getAvailableHours(@Valid @RequestBody DateAndHourOfReservationRequest dateAndHourOfReservationRequest)
    {
        return new AvailableHoursResponse(orderService.getAvailableHours(dateAndHourOfReservationRequest));
    }

    @PostMapping("/makeOrder")
    public ResponseView makeOrder(@Valid @RequestBody OrderRequest orderRequest, HttpServletRequest httpServletRequest)
    {
        return new ResponseView(orderService.makeOrder(orderRequest, httpServletRequest).getPayLink());
    }

    @PostMapping("/makeOrder/repairBike")
    public ResponseView makeOrderRepairBike(@Valid @RequestBody OrderRepairBikeRequest orderRepairBikeRequest, HttpServletRequest httpServletRequest)
    {

        return new ResponseView(orderService.makeOrder(orderRepairBikeRequest, httpServletRequest).getPayLink());

    }

    @CrossOrigin(origins = "https://www.sandbox.paypal.com/**")
    @GetMapping("/pay/cancel")
    public String cancelPay( @RequestParam("orderId") Long orderId,
                            HttpServletResponse httpServletResponse) {
        orderService.cancelOrder(orderId);
        httpServletResponse.setHeader("Location", OrderServiceApplication.FRONT_SITE);
        httpServletResponse.setStatus(302);
        return "redirect:localhost:4200/";
    }

    @CrossOrigin(origins = "https://www.sandbox.paypal.com/**")
    @GetMapping("/pay/success")
    public String successPay(@RequestParam("paymentId") String paymentId,
                             @RequestParam("token") String token,
                             @RequestParam("PayerID") String payerId,
                             @RequestParam("orderId") Long orderId,
                             HttpServletResponse httpServletResponse) throws IOException {
        try {
            Payment payment = orderService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                orderService.changeStatusOfOrder(orderId);
                httpServletResponse.setHeader("Location", OrderServiceApplication.FRONT_SITE);
                httpServletResponse.setStatus(302);
                return "redirect:localhost:4200/";
            }
        } catch (PayPalRESTException ex) {
            httpServletResponse.setHeader("Location", OrderServiceApplication.FRONT_SITE);
            httpServletResponse.setStatus(302);
        }
        return "redirect:localhost:4200/";
    }

    @GetMapping(value = {"/order-history/{page}","/order-history/{userId}/{page}"})
    public OrderHistoryResponse getOrderHistory(@PathVariable(value = "userId", required = false) Long userId, @PathVariable(value = "page") int pageNr, HttpServletRequest httpServletRequest) {
        return userOrderService.getHistory(userId, pageNr, httpServletRequest);
    }





}
