package com.karol.offerservice.offerMenager.api.mapper;


import com.karol.offerservice.offerMenager.api.response.ClassicProductGeneralInformationView;
import com.karol.offerservice.offerMenager.data.entity.Accessory;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AccessoryMapper {
    public ClassicProductGeneralInformationView mapDataToResponse(Accessory accessory) {
        return new ClassicProductGeneralInformationView().builder()
                .productName(accessory.getProduct().getName())
                .currency(accessory.getCurrency())
                .dayAndNightPrice(accessory.getDayAndNightPrice())
                .dayPrice(accessory.getDayPrice())
                .everyBeginHourPrice(accessory.getEveryBeginHourPrice())
                .build();
    }
}
