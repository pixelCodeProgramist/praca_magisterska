package com.example.orderservice.orderMenager.business.service.orderHistoryFactoryPackage;

import com.example.orderservice.security.business.service.JwtTokenNonUserOrderProvider;

public class OrderHistoryFactory {

    public static OrderHistoryI getOrderHistory(OrderHistoryType orderHistoryType, JwtTokenNonUserOrderProvider jwtTokenNonUserOrderProvider){
        OrderHistoryI orderHistoryI = null;
        if(orderHistoryType == OrderHistoryType.MY) orderHistoryI = new MyOrderHistory(jwtTokenNonUserOrderProvider);
        if(orderHistoryType == OrderHistoryType.OTHERS) orderHistoryI = new OtherUserOrderHistory(jwtTokenNonUserOrderProvider);
        return orderHistoryI;
    }
}

