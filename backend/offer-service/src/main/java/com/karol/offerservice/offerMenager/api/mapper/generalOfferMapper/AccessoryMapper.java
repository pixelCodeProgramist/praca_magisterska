package com.karol.offerservice.offerMenager.api.mapper.generalOfferMapper;


import com.karol.offerservice.offerMenager.api.response.generalInfoPackage.ClassicBikeGeneralInformationView;
import com.karol.offerservice.offerMenager.data.entity.Accessory;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AccessoryMapper {
    public ClassicBikeGeneralInformationView mapDataToResponse(Accessory accessory) {
        return new ClassicBikeGeneralInformationView().builder()
                .productName(accessory.getProduct().getName())
                .currency(accessory.getCurrency())
                .dayAndNightPrice(accessory.getDayAndNightPrice())
                .dayPrice(accessory.getDayPrice())
                .everyBeginHourPrice(accessory.getEveryBeginHourPrice())
                .build();
    }
}
