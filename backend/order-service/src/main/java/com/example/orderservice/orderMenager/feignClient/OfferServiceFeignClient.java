package com.example.orderservice.orderMenager.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "OFFER-SERVICE")
public interface OfferServiceFeignClient {
    @GetMapping("offer/detail-information/{type}/{id}")
    Boolean isProductWithIdAndType(@PathVariable(value = "type") String type, @PathVariable(value = "id") Long id);

    @GetMapping("offer/detail-information/frame/{type}/{id}")
    Integer isFrameInBike(@PathVariable(value = "type") String type, @PathVariable(value = "id") Long id);

}
