package com.example.orderservice.orderMenager.business.hoursStrategyPackage;

import com.example.orderservice.orderMenager.api.request.DateAndHourOfReservationRequest;
import com.example.orderservice.orderMenager.data.entity.UserOrder;
import com.example.orderservice.security.business.service.JwtTokenNonUserProvider;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


public class LessThanDayHourStrategy implements HoursStrategy {
    @Override
    public List<String> getAvailableHours(String hourUnit, List<UserOrder> userOrdersFromToday, DateAndHourOfReservationRequest dateAndHourOfReservationRequest, JwtTokenNonUserProvider tokenNonUserProvider) {
        String range = dateAndHourOfReservationRequest.getReservationRange();
        if ("godzina".equalsIgnoreCase(range)) range = "1";
        else {
            range = range.replace("do ", "").replace(" h", "");
        }
        int rangeInt = Integer.parseInt(range);
        List<String> hours = new ArrayList<>();
        int iter = 18 - rangeInt;
        for (int num = 0; num < dateAndHourOfReservationRequest.getPossibleAvailableBikeNumber(); num++) {
            for (int i = 6; i <= iter; i++) {
                hours.add(i + ":00");
            }
        }

        List<String> distinctHours = hours.stream()
                .distinct().collect(Collectors.toList());

        Map<String, Integer> instanceOfHourMap = createMap(distinctHours, userOrdersFromToday);
        Iterator<Map.Entry<String, Integer>> itr = instanceOfHourMap.entrySet().iterator();

        while (itr.hasNext()) {
            Map.Entry<String, Integer> entry = itr.next();
            for (int i = 0; i < entry.getValue(); i++) {
                hours.removeIf(hour->hour.equalsIgnoreCase(entry.getKey()));
            }
        }

        return hours.stream()
                .distinct().collect(Collectors.toList());

    }
}
