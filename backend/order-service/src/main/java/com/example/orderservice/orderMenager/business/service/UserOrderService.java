package com.example.orderservice.orderMenager.business.service;


import com.example.orderservice.orderMenager.api.request.OrderNameProductWithOrderIdRequest;
import com.example.orderservice.orderMenager.api.request.ServiceIdRequest;
import com.example.orderservice.orderMenager.api.response.OrderHistory;
import com.example.orderservice.orderMenager.api.response.OrderHistoryResponse;
import com.example.orderservice.orderMenager.api.response.OrderNameProductResponse;
import com.example.orderservice.orderMenager.api.response.ServiceGeneralInfoView;
import com.example.orderservice.orderMenager.business.service.orderHistoryFactoryPackage.OrderHistoryFactory;
import com.example.orderservice.orderMenager.business.service.orderHistoryFactoryPackage.OrderHistoryI;
import com.example.orderservice.orderMenager.business.service.orderHistoryFactoryPackage.OrderHistoryType;
import com.example.orderservice.orderMenager.data.entity.UserOrder;
import com.example.orderservice.orderMenager.data.repository.UserOrderRepo;
import com.example.orderservice.orderMenager.feignClient.OfferServiceFeignClient;
import com.example.orderservice.security.business.exception.authorize.AuthorizationException;
import com.example.orderservice.security.business.request.UserByIdRequest;
import com.example.orderservice.security.business.service.JwtTokenNonUserOrderProvider;
import com.example.orderservice.security.business.service.JwtTokenNonUserProvider;
import com.example.orderservice.security.business.service.JwtTokenProvider;
import com.example.orderservice.userMenager.api.request.RoleEnum;
import com.example.orderservice.userMenager.api.request.User;
import com.example.orderservice.userMenager.business.exception.user.UserNotFoundException;
import com.example.orderservice.userMenager.feignClient.UserServiceFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserOrderService {
    private JwtTokenNonUserOrderProvider jwtTokenNonUserOrderProvider;
    private JwtTokenNonUserProvider jwtTokenNonUserProvider;
    private JwtTokenProvider jwtTokenProvider;
    private UserOrderRepo userOrderRepo;
    private OfferServiceFeignClient offerServiceFeignClient;
    private UserServiceFeignClient userServiceFeignClient;
    private ImageService imageService;

    public OrderHistoryResponse getHistory(Long id, int pageNr, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization");
        List<OrderHistory> orderHistoryList = new ArrayList<>();

        if (token != null) {
            token = token.substring(7);
            Long userId = jwtTokenProvider.extractUserId(token);
            User user = userServiceFeignClient.getUserById(new UserByIdRequest(userId, jwtTokenNonUserProvider.generateToken()));
            if (user == null) throw new UserNotFoundException(" with id: " + userId);

            OrderHistoryType orderHistoryType = null;
            if(id == null) {
                orderHistoryType = OrderHistoryType.MY;
            } else orderHistoryType = OrderHistoryType.OTHERS;
            OrderHistoryI orderHistoryImpl = OrderHistoryFactory.getOrderHistory(orderHistoryType, jwtTokenNonUserOrderProvider);

            if(RoleEnum.CLIENT.name().equalsIgnoreCase(user.getRole().getRole()) &&
                    orderHistoryType == OrderHistoryType.OTHERS) {
                User userFromSearch = userServiceFeignClient.getUserById(new UserByIdRequest(id, jwtTokenNonUserProvider.generateToken()));
                if(userFromSearch.getUserId() != userId) throw new AuthorizationException();
            }





            List<UserOrder> userOrderList = userOrderRepo.findAll();
            List<OrderNameProductWithOrderIdRequest> orderBikeOrAccessoryList = orderHistoryImpl
                    .getOrderNameProductWithOrderIdRequestList(userOrderList, id, userId);


            orderBikeOrAccessoryList.forEach(orderBikeOrAccessory -> {
                OrderNameProductResponse orderNameProductResponse = offerServiceFeignClient.getOrderNames(orderBikeOrAccessory);
                String name = "Rower: " + orderNameProductResponse.getBike() + "\n o ramie: " + orderNameProductResponse.getFrame();
                if (orderNameProductResponse.getAccessory() != null)
                    name += " wraz z " + orderNameProductResponse.getAccessory();
                byte[] qrCode = imageService.getImagesForUrls(orderBikeOrAccessory.getUserOrder().getUrl());
                orderHistoryList.add(
                        new OrderHistory(name, orderBikeOrAccessory.getUserOrder().getBeginOrder(),
                                orderBikeOrAccessory.getUserOrder().getEndOrder(), orderBikeOrAccessory.getUserOrder().getPrice(),
                                orderBikeOrAccessory.getUserOrder().getCurrency(), qrCode));
            });


            List<ServiceIdRequest> serviceIdRequests = orderHistoryImpl
                    .getServiceIdRequestList(userOrderList, id, userId);

            serviceIdRequests.forEach(serviceIdRequest -> {
                ServiceGeneralInfoView serviceNameInfo = offerServiceFeignClient.getServiceNameInfo(serviceIdRequest.getId());
                String name = "Usługa: " + serviceNameInfo.getName();
                byte[] qrCode = imageService.getImagesForUrls(serviceIdRequest.getUserOrder().getUrl());
                orderHistoryList.add(
                        new OrderHistory(name, serviceIdRequest.getUserOrder().getBeginOrder(),
                                serviceIdRequest.getUserOrder().getEndOrder(), serviceIdRequest.getUserOrder().getPrice(),
                                serviceIdRequest.getUserOrder().getCurrency(), qrCode));
            });

        }


        List<OrderHistory> sortedOrderHistoryList = orderHistoryList.stream()
                .sorted((e1, e2) -> e2.getBeginDate().compareTo(e1.getBeginDate()))
                .collect(Collectors.toList());

        PagedListHolder page = new PagedListHolder(sortedOrderHistoryList);
        page.setPageSize(10);
        page.setPage(pageNr);
        int maxPagesNumber = (int) Math.ceil((float) page.getSource().size()/(float) page.getMaxLinkedPages());
        return new OrderHistoryResponse(page.getPageList(), maxPagesNumber);

    }
}