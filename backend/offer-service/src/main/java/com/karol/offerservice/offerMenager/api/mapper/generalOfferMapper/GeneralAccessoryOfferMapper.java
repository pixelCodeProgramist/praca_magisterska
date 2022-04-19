package com.karol.offerservice.offerMenager.api.mapper.generalOfferMapper;

import com.karol.offerservice.offerMenager.api.response.generalInfoPackage.AccessoryGeneralOfferView;
import com.karol.offerservice.offerMenager.data.entity.Accessory;
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
