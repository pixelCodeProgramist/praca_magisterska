package com.karol.offerservice.offerMenager.api.mapper.generalOfferMapper;


import com.karol.offerservice.offerMenager.api.response.AccessoryInformationInOrderView;
import com.karol.offerservice.offerMenager.api.response.generalInfoPackage.ClassicBikeGeneralInformationView;
import com.karol.offerservice.offerMenager.data.entity.Accessory;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

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
    public AccessoryInformationInOrderView mapDataAccessoryInOrderToResponse(Accessory accessory, int range) {
        BigDecimal price = null;

        if(range < 5) price = accessory.getEveryBeginHourPrice().multiply(new BigDecimal(range));
        if(range == 5) price = accessory.getDayPrice();
        if(range > 5) price = accessory.getDayAndNightPrice();


        return new AccessoryInformationInOrderView().builder()
                .id(accessory.getId())
                .productName(accessory.getProduct().getName())
                .currency(accessory.getCurrency())
                .price(price)
                .build();
    }
}
