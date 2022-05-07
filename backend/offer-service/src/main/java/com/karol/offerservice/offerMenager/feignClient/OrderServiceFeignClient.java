package com.karol.offerservice.offerMenager.feignClient;

import com.karol.offerservice.offerMenager.api.request.DateAndHourOfReservationWithTokenRequest;
import com.karol.offerservice.offerMenager.dto.AvailableHoursResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ORDER-SERVICE")
public interface OrderServiceFeignClient {
    @PostMapping("/order/available-hours")
    AvailableHoursResponse getAvailableTime(@RequestBody DateAndHourOfReservationWithTokenRequest dateAndHourOfReservationWithTokenRequest);
}

