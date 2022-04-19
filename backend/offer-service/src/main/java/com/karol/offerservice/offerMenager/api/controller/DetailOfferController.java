package com.karol.offerservice.offerMenager.api.controller;

import com.karol.offerservice.offerMenager.api.response.detailsInfoPackage.Builder.DetailBikeInfoView;
import com.karol.offerservice.offerMenager.business.service.OfferService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
