package com.example.orderservice.orderMenager.feignClient;

import com.example.orderservice.orderMenager.api.request.OrderNameProductForStatisticRequest;
import com.example.orderservice.orderMenager.api.request.OrderNameProductRequest;
import com.example.orderservice.orderMenager.api.request.ServiceRequest;
import com.example.orderservice.orderMenager.api.response.OrderNameProductForStatisticResponse;
import com.example.orderservice.orderMenager.api.response.OrderNameProductResponse;
import com.example.orderservice.orderMenager.api.response.ServiceGeneralInfoView;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "OFFER-SERVICE")
public interface OfferServiceFeignClient {
    @GetMapping("offer/detail-information/{type}/{id}")
    Boolean isProductWithIdAndType(@PathVariable(value = "type") String type, @PathVariable(value = "id") Long id);

    @GetMapping("offer/detail-information/frame/{type}/{id}")
    Integer isFrameInBike(@PathVariable(value = "type") String type, @PathVariable(value = "id") Long id);

    @PostMapping("offer/offer-in-order")
    OrderNameProductResponse getOrderNames(@RequestBody @Valid OrderNameProductRequest orderNameProductRequest);

    @PostMapping("offer/offer-in-order/statistic")
    OrderNameProductForStatisticResponse getOrderNamesForStatistic(@RequestBody @Valid OrderNameProductForStatisticRequest orderNameProductForStatisticRequest);

    @PostMapping("offer/service-info")
    ServiceGeneralInfoView getRepairBikeServiceInfo(@RequestBody @Valid ServiceRequest serviceRequest);

    @GetMapping("offer/service-info/{id}")
    ServiceGeneralInfoView getServiceNameInfo(@PathVariable(value = "id") Long id);
}
