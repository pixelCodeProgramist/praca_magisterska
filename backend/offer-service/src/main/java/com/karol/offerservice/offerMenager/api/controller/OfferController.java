package com.karol.offerservice.offerMenager.api.controller;

import com.karol.offerservice.business.request.AddBikeRequest;
import com.karol.offerservice.offerMenager.api.response.ResponseView;
import com.karol.offerservice.offerMenager.business.service.OfferService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController()
@RequestMapping("/offer")
@AllArgsConstructor
@Validated
public class OfferController {

    private OfferService offerService;
    @PostMapping("/add")
    public ResponseView addBike(@Valid @RequestBody AddBikeRequest addBikeRequest) throws IOException {
        offerService.addOffer(addBikeRequest);
        return new ResponseView("Rower dodano poprawnie");
    }

    @GetMapping("/activity/{id}")
    public ResponseView changeOfferActivity(@PathVariable(value = "id") Long id) {
        offerService.changeOfferActivity(id);
        return new ResponseView("Status zmieniono poprawnie");
    }
}
