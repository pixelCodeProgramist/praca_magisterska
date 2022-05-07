package com.example.orderservice.orderMenager.business.hoursStrategyPackage;

import com.example.orderservice.orderMenager.api.request.DateAndHourOfReservationRequest;
import com.example.orderservice.orderMenager.data.entity.UserOrder;
import com.example.orderservice.security.business.service.JwtTokenNonUserProvider;

import java.util.List;

public interface HoursStrategy {
    List<String> getAvailableHours(String hourUnit, List<UserOrder> userOrdersFromToday, DateAndHourOfReservationRequest possibleAvailableBikeNumber, JwtTokenNonUserProvider tokenNonUserProvider);
}
