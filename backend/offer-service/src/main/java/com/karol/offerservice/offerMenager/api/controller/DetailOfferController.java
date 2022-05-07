package com.karol.offerservice.offerMenager.api.controller;

import com.karol.offerservice.offerMenager.api.request.DateAndHourOfReservationRequest;
import com.karol.offerservice.offerMenager.api.response.AccessoryInformationInOrderView;
import com.karol.offerservice.offerMenager.api.response.detailsInfoPackage.Builder.DetailBikeInfoView;
import com.karol.offerservice.offerMenager.business.service.OfferService;
import com.karol.offerservice.offerMenager.dto.AvailableHoursResponse;
import io.swagger.models.auth.In;
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

    @GetMapping("/detail-information/{id}")
    public DetailBikeInfoView getBikeInformation(@PathVariable(value = "id") Long id) {
        return offerService.getBikeInformation(id);
    }

    @PostMapping("/available-hours")
    public AvailableHoursResponse getAvailableHours(@Valid @RequestBody DateAndHourOfReservationRequest dateAndHourOfReservationRequest) {
        return offerService.getAvailableHours(dateAndHourOfReservationRequest);
    }

    @GetMapping("/detail-information/accessories/all/{range}")
    public List<AccessoryInformationInOrderView> getAccessories(@PathVariable(value = "range") Integer range) {
        return offerService.getAllAccessories(range);
    }

}
