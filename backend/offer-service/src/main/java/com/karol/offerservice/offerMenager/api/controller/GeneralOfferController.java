package com.karol.offerservice.offerMenager.api.controller;

import com.karol.offerservice.offerMenager.api.request.OrderNameProductForStatisticRequest;
import com.karol.offerservice.offerMenager.api.request.OrderNameProductRequest;
import com.karol.offerservice.offerMenager.api.request.ServiceRequest;
import com.karol.offerservice.offerMenager.api.response.ClassicBikePriceView;
import com.karol.offerservice.offerMenager.api.response.OrderNameProductForStatisticResponse;
import com.karol.offerservice.offerMenager.api.response.OrderNameProductResponse;
import com.karol.offerservice.offerMenager.api.response.generalInfoPackage.*;
import com.karol.offerservice.offerMenager.business.service.OfferService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController()
@RequestMapping("/offer")
@AllArgsConstructor
@Validated
public class GeneralOfferController {

    private OfferService offerService;

    @GetMapping("/general-information/classic")
    public List<ClassicBikeGeneralInformationView> getAllGeneralClassicOfferInformation() {
        return offerService.getAllGeneralClassicOfferInformation();
    }

    @GetMapping("/general-information/electric")
    public List<ElectricBikeGeneralInformationView> getAllElectricClassicOfferInformation() {
        return offerService.getAllGeneralElectricOfferInformation();
    }

    @GetMapping("/general-offer/classic/{section}/{page}")
    public BikeGeneralOfferResponseView getGeneralClassicOfferAfterPage(@PathVariable(value = "section") String section,
                                                                        @PathVariable(value = "page") int pageNr) {
        return offerService.getClassicProductsGeneralOffer(section, pageNr);
    }

    @GetMapping("/general-offer/electric/{page}")
    public BikeGeneralOfferResponseView getGeneralElectricOfferAfterPage(@PathVariable(value = "page") int pageNr) {
        return offerService.getElectricProductsGeneralOffer(pageNr);
    }

    @GetMapping("/general-offer/accessories/{page}")
    public AccessoryGeneralOfferResponseView getGeneralAccessoryOfferAfterPage(@PathVariable(value = "page") int pageNr) {
        return offerService.getAccessoryProductsGeneralOffer(pageNr);
    }

    @GetMapping("/bikes/{all}")
    public List<BikeForSearchView> getBikes(@PathVariable(value = "all", required = false) boolean all) {
        return offerService.getAllBikes(all, false);
    }

    @PostMapping("/offer-in-order")
    OrderNameProductResponse getOrderNames(@RequestBody @Valid OrderNameProductRequest orderNameProductRequest){
        return offerService.getOrderNames(orderNameProductRequest);
    }

    @PostMapping("/offer-in-order/statistic")
    OrderNameProductForStatisticResponse getOrderNamesForStatistic(@RequestBody @Valid OrderNameProductForStatisticRequest orderNameProductForStatisticRequest){
       return offerService.getOrderNamesForStatistic(orderNameProductForStatisticRequest);
    }


    @PostMapping("/service-info")
    public ServiceGeneralInfoView getService(@RequestBody @Valid ServiceRequest serviceRequest) {
        return offerService.getServiceInfo(serviceRequest);
    }

    @GetMapping("service-info/{id}")
    ServiceGeneralInfoView getServiceNameInfo(@PathVariable(value = "id") Long id){
        return offerService.getServiceInfo(id);
    }

    @GetMapping("/general-information/classic/prices")
    public List<ClassicBikePriceView> getClassicBikePrices() {
        return offerService.getClassicBikePrices();
    }


}

