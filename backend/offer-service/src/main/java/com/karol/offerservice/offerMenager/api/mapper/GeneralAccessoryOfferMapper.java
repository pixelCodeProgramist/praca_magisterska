package com.karol.offerservice.offerMenager.api.mapper;

import com.karol.offerservice.offerMenager.api.response.AccessoryGeneralOfferView;
import com.karol.offerservice.offerMenager.api.response.ProductGeneralOfferView;
import com.karol.offerservice.offerMenager.data.entity.Accessory;
import com.karol.offerservice.offerMenager.data.entity.ElectricBike;
import lombok.experimental.UtilityClass;


@UtilityClass
public class GeneralAccessoryOfferMapper {
    public AccessoryGeneralOfferView mapDataToResponse(Accessory accessory, byte[] image) {
        return new AccessoryGeneralOfferView().builder()
                .id(accessory.getId())
                .name(accessory.getProduct().getName())
                .image(image)
                .rating(accessory.getRating())
                .minimalPrice(accessory.getEveryBeginHourPrice())
                .build();
    }
}
