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
        if("godzina".equalsIgnoreCase(range)) range = "1";
        else {
            range = range.replace("do ","").replace(" h","");
        }
        int rangeInt = Integer.parseInt(range);
        List<String> hours = new ArrayList<>();
        int iter = 18 - rangeInt;
        for(int i=6;i<=iter;i++) {
            hours.add(i+":00");
        }

        Set<String> bikeIdSet = userOrdersFromToday.stream()
                .map(userOrder ->
                        tokenNonUserProvider.extractValueFromClaims(userOrder.getTransactionToken(), "bikeFrameId"))
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
                Calendar calendar2 = GregorianCalendar.getInstance(); // creates a new calendar instance
                calendar2.setTime(userOrder.getEndOrder());
                String min = "";

                int hourBeginHour = calendar.get(Calendar.HOUR_OF_DAY);
                int hourEndHour = calendar2.get(Calendar.HOUR_OF_DAY);

                if(calendar.get(Calendar.MINUTE)<10) min = "0"+calendar.get(Calendar.MINUTE);

                for(int i=0;i<diffrence;i++) {
                    String beginHourStr = hourBeginHour+":"+min;
                    hours.removeIf(hour -> hour.equalsIgnoreCase(beginHourStr));
                    hourBeginHour++;
                }
                hourBeginHour -= diffrence;

                List<String> hoursToReturn = new ArrayList<>();
               for(int i=0; i<hours.size();i++) {
                   int hourInt = Integer.parseInt(hours.get(i).split(":")[0]);
                   if((hourInt+rangeInt)<=hourBeginHour && hourInt<hourBeginHour || (hourInt+rangeInt)>=hourEndHour && hourInt>=hourEndHour) hoursToReturn.add(hours.get(i));
               }
               hours.removeIf(hour-> !hoursToReturn.contains(hour));
            });

            return hours;
        }

    }
}
