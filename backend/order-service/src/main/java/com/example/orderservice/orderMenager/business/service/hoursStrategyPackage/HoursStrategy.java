package com.example.orderservice.orderMenager.business.service.hoursStrategyPackage;

import com.example.orderservice.orderMenager.api.request.DateAndHourOfReservationRequest;
import com.example.orderservice.orderMenager.data.entity.UserOrder;
import com.example.orderservice.security.business.service.JwtTokenNonUserOrderProvider;
import lombok.*;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
class BeginEndOrderInInteger {
    private int begin;
    private int end;
}
public interface HoursStrategy {
    default Map<String, Integer> createMap(List<String> hours, List<UserOrder> userOrdersFromToday) {
        List<Integer> distinctHoursIntegers = hours.stream()
                .map(hour -> new Integer(hour.replace(":", "")))
                .distinct().collect(Collectors.toList());

        List<BeginEndOrderInInteger> beginEndOrderInIntegers = userOrdersFromToday.stream().map(userOrder -> {
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(userOrder.getBeginOrder());
            calendar.setTime(userOrder.getBeginOrder());
            Calendar calendar2 = GregorianCalendar.getInstance();
            calendar2.setTime(userOrder.getEndOrder());
            int hourBeginHour = calendar.get(Calendar.HOUR_OF_DAY);
            int hourEndHour = calendar2.get(Calendar.HOUR_OF_DAY);
            int minuteBeginHour = calendar.get(Calendar.MINUTE) < 10 ? calendar.get(Calendar.MINUTE) * 10 : calendar.get(Calendar.MINUTE);
            int minuteEndHour = calendar2.get(Calendar.MINUTE) < 10 ? calendar2.get(Calendar.MINUTE) * 10 : calendar2.get(Calendar.MINUTE);
            return new BeginEndOrderInInteger(hourBeginHour * 100 + minuteBeginHour, hourEndHour * 100 + minuteEndHour);
        }).collect(Collectors.toList());

        Map<String, Integer> hourInstanceMap = new LinkedHashMap<>();

        for (int i = 0; i < distinctHoursIntegers.size(); i++) {
            int hourInstance = 0;
            for (BeginEndOrderInInteger beginEndOrderInInteger : beginEndOrderInIntegers) {
                if (beginEndOrderInInteger.getBegin() <= distinctHoursIntegers.get(i) && beginEndOrderInInteger.getEnd() > distinctHoursIntegers.get(i))
                    hourInstance++;
            }
            hourInstanceMap.put(hours.get(i), hourInstance);
        }

        return hourInstanceMap;

    }
    List<String> getAvailableHours(String hourUnit, List<UserOrder> userOrdersFromToday, DateAndHourOfReservationRequest possibleAvailableBikeNumber, JwtTokenNonUserOrderProvider tokenNonUserProvider);
}
