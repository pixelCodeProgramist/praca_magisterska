package com.karol.offerservice.offerMenager.api.mapper;

import com.karol.offerservice.offerMenager.api.response.ProductGeneralOfferView;
import com.karol.offerservice.offerMenager.data.entity.ClassicBike;
import com.karol.offerservice.offerMenager.data.entity.ElectricBike;
import lombok.experimental.UtilityClass;


@UtilityClass
public class GeneralElectricOfferMapper {
    public ProductGeneralOfferView mapDataToResponse(ElectricBike electricBike, byte[] image) {
        return new ProductGeneralOfferView().builder()
                .id(electricBike.getId())
                .name(electricBike.getProduct().getName())
                .image(image)
                .rating(electricBike.getRating())
                .build();
    }
}
