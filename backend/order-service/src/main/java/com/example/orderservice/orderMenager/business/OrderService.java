package com.example.orderservice.orderMenager.business;


import com.example.orderservice.orderMenager.api.request.DateAndHourOfReservationRequest;
//import com.example.orderservice.orderMenager.data.repository.OrderRepo;
import com.example.orderservice.orderMenager.business.hoursStrategyPackage.AtLeastDayHourStrategy;
import com.example.orderservice.orderMenager.business.hoursStrategyPackage.HoursStrategy;
import com.example.orderservice.orderMenager.business.hoursStrategyPackage.LessThanDayHourStrategy;
import com.example.orderservice.orderMenager.data.entity.UserOrder;
import com.example.orderservice.orderMenager.data.repository.UserOrderRepo;
import com.example.orderservice.security.business.exception.authorize.AuthorizationException;
import com.example.orderservice.security.business.service.JwtTokenNonUserOrderProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {

    private JwtTokenNonUserOrderProvider jwtTokenNonUserProvider;
    private UserOrderRepo userOrderRepo;

    public List<String> getAvailableHours(DateAndHourOfReservationRequest dateAndHourOfReservationRequest) {
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("bikeFrameId", "1");
//        claims.put("bikeId", "18");
//        claims.put("userId", "14");
//        claims.put("typeProduct", "bike");
//        claims.put("bikeType", "electric");
//
//        String s = jwtTokenNonUserProvider.generateToken(claims);
//        System.out.println();

        if (jwtTokenNonUserProvider.validateToken(dateAndHourOfReservationRequest.getToken())) {
            if (!jwtTokenNonUserProvider.isTokenExpire(dateAndHourOfReservationRequest.getToken()) &&
                    "COMPUTER".equalsIgnoreCase(jwtTokenNonUserProvider.extractUserIdName(dateAndHourOfReservationRequest.getToken()))) {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String s = df.format(dateAndHourOfReservationRequest.getReservationTime());
                List<UserOrder> userOrdersFromToday = userOrderRepo.getUserOrderByDate(s);

                userOrdersFromToday = userOrdersFromToday.stream().filter(userOrder -> jwtTokenNonUserProvider
                        .extractValueFromClaims(userOrder.getTransactionToken(), "bikeId")
                        .equalsIgnoreCase(String.valueOf(dateAndHourOfReservationRequest.getBikeId())) &&
                        jwtTokenNonUserProvider.extractValueFromClaims(userOrder.getTransactionToken(), "bikeFrameId")
                                .equalsIgnoreCase(String.valueOf(dateAndHourOfReservationRequest.getBikeFrameId())))
                        .collect(Collectors.toList());

                HoursStrategy hoursStrategy = null;

                if (dateAndHourOfReservationRequest.getReservationRange().equalsIgnoreCase("doba") ||
                        dateAndHourOfReservationRequest.getReservationRange().equalsIgnoreCase("dzień"))
                    hoursStrategy = new AtLeastDayHourStrategy();
                else
                    hoursStrategy = new LessThanDayHourStrategy();
                return hoursStrategy.getAvailableHours(dateAndHourOfReservationRequest.getReservationRange(), userOrdersFromToday,
                        dateAndHourOfReservationRequest, jwtTokenNonUserProvider);

            } else throw new AuthorizationException();
        } else throw new AuthorizationException();

    }
}