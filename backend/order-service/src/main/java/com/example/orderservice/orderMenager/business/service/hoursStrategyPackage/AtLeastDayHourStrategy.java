package com.example.orderservice.orderMenager.business.service.hoursStrategyPackage;

import com.example.orderservice.orderMenager.api.request.DateAndHourOfReservationRequest;
import com.example.orderservice.orderMenager.data.entity.UserOrder;
import com.example.orderservice.security.business.service.JwtTokenNonUserOrderProvider;

import java.util.*;
import java.util.stream.Collectors;

public class AtLeastDayHourStrategy implements HoursStrategy {
    @Override
    public List<String> getAvailableHours(String hourUnit, List<UserOrder> userOrdersFromToday, DateAndHourOfReservationRequest dateAndHourOfReservationRequest, JwtTokenNonUserOrderProvider tokenNonUserProvider) {
        List<String> hours = new ArrayList<>(Arrays.asList("6:00", "7:00", "8:00", "9:00", "10:00", "11:00","12:00"
                ,"13:00","14:00","15:00","16:00","17:00"));

        Map<String, Integer> instanceOfHourMap = createMap(hours, userOrdersFromToday);

        List<Integer> tooManyTakeBikes = instanceOfHourMap.values().stream()
                .filter(instance -> instance >= dateAndHourOfReservationRequest.getPossibleAvailableBikeNumber())
                .collect(Collectors.toList());

        if(tooManyTakeBikes.size() > 0) return new ArrayList<>();

        return hours;
    }
}
