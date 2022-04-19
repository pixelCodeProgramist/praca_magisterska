package com.karol.offerservice.offerMenager.api.mapper.generalOfferMapper;

import com.karol.offerservice.offerMenager.api.response.generalInfoPackage.BikeGeneralOfferView;
import com.karol.offerservice.offerMenager.data.entity.ElectricBike;
import lombok.experimental.UtilityClass;


@UtilityClass
public class GeneralElectricOfferMapper {
    public BikeGeneralOfferView mapDataToResponse(ElectricBike electricBike, byte[] image) {
        return new BikeGeneralOfferView().builder()
                .id(electricBike.getId())
                .name(electricBike.getProduct().getName())
                .image(image)
                .rating(electricBike.getRating())
                .build();
    }
}
