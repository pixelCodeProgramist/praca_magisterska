package com.karol.offerservice.offerMenager.api.controller;

import com.karol.offerservice.business.request.AddBikeRequest;
import com.karol.offerservice.offerMenager.api.request.ClassicBikePriceRequest;
import com.karol.offerservice.offerMenager.api.request.ElectricBikePriceRequest;
import com.karol.offerservice.offerMenager.api.response.ResponseView;
import com.karol.offerservice.offerMenager.business.service.OfferService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController()
@RequestMapping("/offer")
@AllArgsConstructor
@Validated
public class OfferController {

    private OfferService offerService;
    @PostMapping("/add")
    public ResponseView addBike(@Valid @RequestBody AddBikeRequest addBikeRequest) throws IOException {
        offerService.addOffer(addBikeRequest, false);
        return new ResponseView("Rower dodano poprawnie");
    }

    @GetMapping("/activity/{id}")
    public ResponseView changeOfferActivity(@PathVariable(value = "id") Long id) {
        offerService.changeOfferActivity(id);
        return new ResponseView("Status zmieniono poprawnie");
    }

    @PutMapping("/classicPrices")
    public ResponseView changeClassicPrices(@Valid @RequestBody List<ClassicBikePriceRequest> classicBikePriceRequestList) {
        offerService.updateClassicBikePrices(classicBikePriceRequestList);
        return new ResponseView("Ceny zmieniono poprawnie");
    }

    @PutMapping("/electricPrices")
    public ResponseView changeElectricPrices(@Valid @RequestBody List<ElectricBikePriceRequest> electricBikePriceRequestList) {
        offerService.updateElectricBikePrices(electricBikePriceRequestList);
        return new ResponseView("Ceny zmieniono poprawnie");
    }
}
