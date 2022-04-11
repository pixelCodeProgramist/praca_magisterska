package com.karol.offerservice.offerMenager.api.mapper;

import com.karol.offerservice.offerMenager.api.response.ProductGeneralOfferView;
import com.karol.offerservice.offerMenager.data.entity.ClassicBike;
import lombok.experimental.UtilityClass;


@UtilityClass
public class GeneralClassicOfferMapper {
    public ProductGeneralOfferView mapDataToResponse(ClassicBike classicBike, byte[] image) {
        return new ProductGeneralOfferView().builder()
                .id(classicBike.getId())
                .name(classicBike.getProduct().getName())
                .image(image)
                .rating(classicBike.getRating())
                .build();
    }
}
