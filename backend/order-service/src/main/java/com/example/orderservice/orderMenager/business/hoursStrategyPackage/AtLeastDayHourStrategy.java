package com.example.orderservice.orderMenager.business.hoursStrategyPackage;

import com.example.orderservice.orderMenager.api.request.DateAndHourOfReservationRequest;
import com.example.orderservice.orderMenager.data.entity.UserOrder;
import com.example.orderservice.security.business.service.JwtTokenNonUserProvider;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class AtLeastDayHourStrategy implements HoursStrategy {
    @Override
    public List<String> getAvailableHours(String hourUnit, List<UserOrder> userOrdersFromToday, DateAndHourOfReservationRequest dateAndHourOfReservationRequest, JwtTokenNonUserProvider tokenNonUserProvider) {
        List<String> hours = new ArrayList<>(Arrays.asList("6:00", "7:00", "8:00", "9:00", "10:00", "11:00","12:00"
                ,"13:00","14:00","15:00","16:00","17:00"));
        Set<String> bikeIdSet = userOrdersFromToday.stream()
                .map(userOrder -> tokenNonUserProvider.extractValueFromClaims(userOrder.getTransactionToken(), "bikeId")+"#"+
                        tokenNonUserProvider.extractValueFromClaims(userOrder.getTransactionToken(), "bikeFrameId")+"#"+
                        tokenNonUserProvider.extractValueFromClaims(userOrder.getTransactionToken(), "typeProduct"))
                .collect(Collectors.toSet());

        if(dateAndHourOfReservationRequest.getPossibleAvailableBikeNumber()-bikeIdSet.size() > 0) {
            return hours;
        }else {
            userOrdersFromToday.stream().forEach(userOrder -> {
                long diff = userOrder.getEndOrder().getTime() - userOrder.getBeginOrder().getTime();
                TimeUnit time = TimeUnit.HOURS;
                long diffrence = time.convert(diff, TimeUnit.MILLISECONDS);
                Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
                calendar.setTime(userOrder.getBeginOrder());
                String min = "";

                int hourBeginHour = calendar.get(Calendar.HOUR_OF_DAY);
                if(calendar.get(Calendar.MINUTE)<10) min = "0"+calendar.get(Calendar.MINUTE);

                for(int i=0;i<diffrence;i++) {
                    String beginHourStr = hourBeginHour+":"+min;
                    hours.removeIf(hour -> hour.equalsIgnoreCase(beginHourStr));
                    hourBeginHour++;
                }

            });

            return hours;
        }


    }
}
