package com.example.orderservice.orderMenager.business.service;


import com.example.orderservice.orderMenager.api.request.DateAndHourOfReservationRequest;
import com.example.orderservice.orderMenager.api.request.OrderRequest;
import com.example.orderservice.orderMenager.api.response.Link;
import com.example.orderservice.orderMenager.business.exception.date.DateIncorrectException;
import com.example.orderservice.orderMenager.data.entity.UserOrder;
import com.example.orderservice.orderMenager.data.repository.UserOrderRepo;
import com.example.orderservice.security.business.exception.authorize.AuthorizationException;
import com.example.orderservice.security.business.service.JwtTokenNonUserOrderProvider;
import com.example.orderservice.security.business.service.JwtTokenNonUserProvider;
import com.example.orderservice.security.business.service.JwtTokenProvider;
import com.example.orderservice.userMenager.api.request.User;
import com.google.common.net.HttpHeaders;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderServiceTest {

    @Autowired
    private JwtTokenNonUserOrderProvider jwtTokenNonUserOrderProvider;
    @Autowired
    private JwtTokenNonUserProvider jwtTokenNonUserProvider;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserOrderRepo userOrderRepo;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserOrderService userOrderService;

    @Before
    public void init() {
       makeInitialTransaction(7,3);
    }

    private void makeInitialTransaction(int hour, int range) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("bikeId",1L);
        claims.put("bikeFrameId",1L);
        claims.put("userId",1L);
        claims.put("withBikeTrip",true);

        UserOrder userOrder = UserOrder.builder()
                .price(new BigDecimal(10.00))
                .currency("PLN")
                .paid(true)
                .timeToPaid(new Date())
                .beginOrder(new Date(new Date().getYear(),Calendar.MAY,23,hour,0))
                .endOrder(new Date(new Date().getYear(),Calendar.MAY,23,hour+range,0))
                .transactionToken(jwtTokenNonUserOrderProvider.generateToken(claims))
                .build();
        userOrderRepo.save(userOrder);
    }

    @Test
    public void should_get_available_hours() {
        Date date = new Date(new Date().getYear(), Calendar.MAY,23,7,0);
        DateAndHourOfReservationRequest dateAndHourOfReservationRequest = DateAndHourOfReservationRequest.builder()
                .bikeId(1L)
                .bikeFrameId(1L)
                .frame("S")
                .offerId(1L)
                .reservationRange("do 3 h")
                .reservationTime(date)
                .possibleAvailableBikeNumber(1)
                .token(jwtTokenNonUserProvider.generateToken())
                .build();
        List<String> availableHours = orderService.getAvailableHours(dateAndHourOfReservationRequest);
        Assertions.assertEquals(6, availableHours.size());
        dateAndHourOfReservationRequest.setReservationRange("godzina");
        availableHours = orderService.getAvailableHours(dateAndHourOfReservationRequest);
        Assertions.assertEquals(9, availableHours.size());
        dateAndHourOfReservationRequest.setReservationRange("dzień");
        availableHours = orderService.getAvailableHours(dateAndHourOfReservationRequest);
        Assertions.assertEquals(0, availableHours.size());

    }

    @Test
    public void should_make_offer_order() throws IOException {
        String token = issueTokenForUser(User.builder().userId(1L).build());
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/detailUser");
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        OrderRequest orderRequest = OrderRequest.builder()
                .accessoryId(1L)
                .beginDateOrder(new Date(new Date().getYear(),Calendar.MAY,23,14,0))
                .endDateOrder(new Date(new Date().getYear(),Calendar.MAY,23,15,0))
                .selectedFrameOption("S")
                .bikeId(1L)
                .price(new BigDecimal(10))
                .withBikeTrip(true)
                .build();
        Link link = orderService.makeOrder(orderRequest, request, true);
        Assertions.assertNotNull(link);
        Assertions.assertTrue(link.getPayLink().startsWith("https"));

        List<UserOrder> userOrders = userOrderRepo.findAll();
        UserOrder userOrder = userOrders.get(userOrders.size()-1);
        Assertions.assertFalse(userOrder.getPaid());
        orderService.cancelOrder(userOrder.getOrderId());
        List<UserOrder> userOrdersAfter = userOrderRepo.findAll();
        Assertions.assertTrue(userOrdersAfter.size()<userOrders.size());
        MockHttpServletRequest request2 = new MockHttpServletRequest("GET", "/detailUser");
        Assertions.assertThrows(AuthorizationException.class,()->orderService.makeOrder(orderRequest, request2, true));
        orderRequest.setBeginDateOrder(new Date(new Date().getYear(),Calendar.MAY,23,15,0));
        orderRequest.setEndDateOrder(new Date(new Date().getYear(),Calendar.MAY,23,14,0));
        Assertions.assertThrows(DateIncorrectException.class,()->orderService.makeOrder(orderRequest, request, true));
        orderRequest.setBeginDateOrder(new Date(new Date().getYear(),Calendar.MAY,23,14,0));
        orderRequest.setEndDateOrder(new Date(new Date().getYear(),Calendar.MAY,23,15,0));
        orderService.makeOrder(orderRequest, request, true);
        userOrders = userOrderRepo.findAll();
        userOrder = userOrders.get(userOrders.size()-1);
        orderService.changeStatusOfOrder(userOrder.getOrderId(), true);
        userOrders = userOrderRepo.findAll();
        Assertions.assertTrue(userOrders.get(userOrders.size()-1).getPaid());
    }


    private String issueTokenForUser(User user) {
        return jwtTokenProvider.generateToken(user);
    }

    @After
    public void finish() {
        userOrderRepo.deleteAll();
    }

}