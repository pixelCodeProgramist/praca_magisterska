package com.karol.offerservice.offerMenager.api.controller;

import com.karol.offerservice.offerMenager.api.response.AccessoryGeneralOfferResponseView;
import com.karol.offerservice.offerMenager.api.response.ClassicProductGeneralInformationView;
import com.karol.offerservice.offerMenager.api.response.ElectricProductGeneralInformationView;
import com.karol.offerservice.offerMenager.api.response.ProductGeneralOfferResponseView;
import com.karol.offerservice.offerMenager.business.service.OfferService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController()
@RequestMapping("/offer")
@AllArgsConstructor
@Validated
public class OfferController {

    private OfferService offerService;

    @GetMapping("/general-information/classic")
    public List<ClassicProductGeneralInformationView> getAllGeneralClassicOfferInformation() {
        return offerService.getAllGeneralClassicOfferInformation();
    }

    @GetMapping("/general-information/electric")
    public List<ElectricProductGeneralInformationView> getAllElectricClassicOfferInformation() {
        return offerService.getAllGeneralElectricOfferInformation();
    }

    @GetMapping("/general-offer/classic/{section}/{page}")
    public ProductGeneralOfferResponseView getGeneralClassicOfferAfterPage(@PathVariable(value = "section") String section,
                                                                    @PathVariable(value = "page") int pageNr) {
        return offerService.getClassicProductsGeneralOffer(section, pageNr);
    }

    @GetMapping("/general-offer/electric/{page}")
    public ProductGeneralOfferResponseView getGeneralElectricOfferAfterPage(@PathVariable(value = "page") int pageNr) {
        return offerService.getElectricProductsGeneralOffer(pageNr);
    }

    @GetMapping("/general-offer/accessories/{page}")
    public AccessoryGeneralOfferResponseView getGeneralAccessoryOfferAfterPage(@PathVariable(value = "page") int pageNr) {
        return offerService.getAccessoryProductsGeneralOffer(pageNr);
    }


}

