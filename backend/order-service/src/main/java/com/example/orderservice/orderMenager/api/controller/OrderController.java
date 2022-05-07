package com.example.orderservice.orderMenager.api.controller;

import com.example.orderservice.orderMenager.api.response.AvailableHoursResponse;
import com.example.orderservice.orderMenager.business.OrderService;
import com.example.orderservice.orderMenager.api.request.DateAndHourOfReservationRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;
    
    @PostMapping("/available-hours")
    public AvailableHoursResponse getAvailableHours(@Valid @RequestBody DateAndHourOfReservationRequest dateAndHourOfReservationRequest)
    {
        return new AvailableHoursResponse(orderService.getAvailableHours(dateAndHourOfReservationRequest));
    }
}
