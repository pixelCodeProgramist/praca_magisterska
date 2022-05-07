package com.karol.offerservice.offerMenager.api.mapper.generalOfferMapper;

import com.karol.offerservice.offerMenager.api.response.generalInfoPackage.BikeGeneralOfferView;
import com.karol.offerservice.offerMenager.data.entity.ClassicBike;
import lombok.experimental.UtilityClass;


@UtilityClass
public class GeneralClassicOfferMapper {
    public BikeGeneralOfferView mapDataToResponse(ClassicBike classicBike, byte[] image) {
        return new BikeGeneralOfferView().builder()
                .id(classicBike.getId())
                .name(classicBike.getProduct().getName())
                .image(image)
                .rating(classicBike.getProduct().getRating())
                .build();
    }
}
