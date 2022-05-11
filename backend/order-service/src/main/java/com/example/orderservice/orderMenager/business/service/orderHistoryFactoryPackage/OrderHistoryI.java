package com.example.orderservice.orderMenager.business.service.orderHistoryFactoryPackage;

import com.example.orderservice.orderMenager.api.request.OrderNameProductWithOrderIdRequest;
import com.example.orderservice.orderMenager.api.request.ServiceIdRequest;
import com.example.orderservice.orderMenager.data.entity.UserOrder;

import java.util.List;

public interface OrderHistoryI {
    List<OrderNameProductWithOrderIdRequest> getOrderNameProductWithOrderIdRequestList(List<UserOrder> userOrderList, Long id, Long userId);
    List<ServiceIdRequest> getServiceIdRequestList(List<UserOrder> userOrderList, Long id, Long userId);
}
