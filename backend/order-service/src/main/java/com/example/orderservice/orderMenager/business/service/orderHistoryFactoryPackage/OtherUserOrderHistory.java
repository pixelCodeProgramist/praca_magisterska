package com.example.orderservice.orderMenager.business.service.orderHistoryFactoryPackage;

import com.example.orderservice.orderMenager.api.request.OrderNameProductWithOrderIdRequest;
import com.example.orderservice.orderMenager.api.request.ServiceIdRequest;
import com.example.orderservice.orderMenager.data.entity.UserOrder;
import com.example.orderservice.security.business.service.JwtTokenNonUserOrderProvider;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class OtherUserOrderHistory implements OrderHistoryI{

    private JwtTokenNonUserOrderProvider jwtTokenNonUserOrderProvider;
    @Override
    public List<OrderNameProductWithOrderIdRequest> getOrderNameProductWithOrderIdRequestList(List<UserOrder> userOrderList, Long id, Long userId) {
        return userOrderList.stream()
                .filter(userOrder -> {
                    Long userIdFromToken = Long.valueOf(jwtTokenNonUserOrderProvider.extractValueFromClaims(userOrder.getTransactionToken(), "userId"));
                    String bikeId = jwtTokenNonUserOrderProvider.extractValueFromClaims(userOrder.getTransactionToken(), "bikeId");
                    return id == userIdFromToken && !"null".equalsIgnoreCase(bikeId) && userId!=userIdFromToken;
                })
                .map(userOrder -> {
                    String bikeIdStr = jwtTokenNonUserOrderProvider.extractValueFromClaims(userOrder.getTransactionToken(), "bikeId");
                    Long bikeId = NumberUtils.isCreatable(bikeIdStr) ? Long.valueOf(bikeIdStr) : null;
                    String accessoryIdStr = jwtTokenNonUserOrderProvider.extractValueFromClaims(userOrder.getTransactionToken(), "accessoryId");
                    Long accessoryId = NumberUtils.isCreatable(accessoryIdStr) ? Long.valueOf(accessoryIdStr) : null;
                    return new OrderNameProductWithOrderIdRequest(bikeId, accessoryId, userOrder);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ServiceIdRequest> getServiceIdRequestList(List<UserOrder> userOrderList, Long id, Long userId) {
        return userOrderList.stream()
                .filter(userOrder -> {
                    Long userIdFromToken = Long.valueOf(jwtTokenNonUserOrderProvider.extractValueFromClaims(userOrder.getTransactionToken(), "userId"));
                    String serviceId = jwtTokenNonUserOrderProvider.extractValueFromClaims(userOrder.getTransactionToken(), "serviceId");
                    return userId == userIdFromToken && !"null".equalsIgnoreCase(serviceId) && userId!=userIdFromToken;
                })
                .map(userOrder ->{
                    String serviceIdStr = jwtTokenNonUserOrderProvider.extractValueFromClaims(userOrder.getTransactionToken(), "serviceId");
                    Long serviceId = NumberUtils.isCreatable(serviceIdStr) ? Long.valueOf(serviceIdStr) : null;
                    return new ServiceIdRequest(userOrder, serviceId);
                })
                .collect(Collectors.toList());
    }
}
