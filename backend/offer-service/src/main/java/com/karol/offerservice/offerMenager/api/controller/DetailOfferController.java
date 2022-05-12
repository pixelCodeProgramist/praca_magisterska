package com.karol.offerservice.offerMenager.api.controller;

import com.karol.offerservice.offerMenager.api.request.DateAndHourOfReservationRequest;
import com.karol.offerservice.offerMenager.api.response.AccessoryInformationInOrderView;
import com.karol.offerservice.offerMenager.api.response.detailsInfoPackage.Builder.DetailBikeInfoView;
import com.karol.offerservice.offerMenager.business.service.OfferService;
import com.karol.offerservice.offerMenager.dto.AvailableHoursResponse;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController()
@RequestMapping("/offer")
@AllArgsConstructor
@Validated
public class DetailOfferController {
    private OfferService offerService;

    @GetMapping("/detail-information/product/{id}/{all}")
    public DetailBikeInfoView getBikeInformation(@PathVariable(value = "id") Long id, @PathVariable(value = "all", required = false) Boolean all) {
        return offerService.getBikeInformation(id, all);
    }

    @PostMapping("/available-hours")
    public AvailableHoursResponse getAvailableHours(@Valid @RequestBody DateAndHourOfReservationRequest dateAndHourOfReservationRequest) {
        return offerService.getAvailableHours(dateAndHourOfReservationRequest);
    }

    @GetMapping("/detail-information/accessories/all/{range}")
    public List<AccessoryInformationInOrderView> getAccessories(@PathVariable(value = "range") Integer range) {
        return offerService.getAllAccessories(range);
    }

    @GetMapping("/detail-information/{type}/{id}")
    public Boolean existBikeWithFrame(@PathVariable(value = "type") String type, @PathVariable(value = "id") Long id) {
        return offerService.isProductInDb(type, id);
    }

    @GetMapping("/detail-information/frame/{type}/{id}")
    public Integer isFrameInBike(@PathVariable(value = "type") String type, @PathVariable(value = "id") Long id){
        return offerService.getFrameId(type, id);
    }

}
