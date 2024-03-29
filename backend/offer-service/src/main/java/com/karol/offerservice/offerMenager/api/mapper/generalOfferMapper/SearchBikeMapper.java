package com.karol.offerservice.offerMenager.api.mapper.generalOfferMapper;

import com.karol.offerservice.offerMenager.api.response.generalInfoPackage.BikeForSearchView;
import com.karol.offerservice.offerMenager.api.response.generalInfoPackage.ElectricBikeGeneralInformationView;
import com.karol.offerservice.offerMenager.data.entity.ClassicBike;
import com.karol.offerservice.offerMenager.data.entity.ElectricBike;
import com.karol.offerservice.offerMenager.data.entity.ElectricBikePrice;
import lombok.experimental.UtilityClass;


@UtilityClass
public class SearchBikeMapper {
    public BikeForSearchView mapDataToResponse(ElectricBike electricBike, byte[] image) {
        return BikeForSearchView.builder()
                .id(electricBike.getProduct().getProductId())
                .name(electricBike.getProduct().getName())
                .image(image)
                .active(electricBike.getProduct().getActive())
                .type("Elektryczny")
                .build();
    }

    public BikeForSearchView mapDataToResponse(ClassicBike classicBike, byte[] image) {
        return BikeForSearchView.builder()
                .id(classicBike.getProduct().getProductId())
                .name(classicBike.getProduct().getName())
                .image(image)
                .active(classicBike.getProduct().getActive())
                .type("Klasyczny " + classicBike.getBikeType().getType())
                .build();
    }
}
