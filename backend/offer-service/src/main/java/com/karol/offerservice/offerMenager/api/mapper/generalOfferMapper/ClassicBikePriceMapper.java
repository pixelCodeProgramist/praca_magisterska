package com.karol.offerservice.offerMenager.api.mapper.generalOfferMapper;

import com.karol.offerservice.offerMenager.api.response.generalInfoPackage.ClassicBikeGeneralInformationView;
import com.karol.offerservice.offerMenager.api.response.generalInfoPackage.ElectricBikeGeneralInformationView;
import com.karol.offerservice.offerMenager.data.entity.Accessory;
import com.karol.offerservice.offerMenager.data.entity.ClassicBikePrice;
import com.karol.offerservice.offerMenager.data.entity.ElectricBikePrice;
import lombok.experimental.UtilityClass;


@UtilityClass
public class ClassicBikePriceMapper {
    public ClassicBikeGeneralInformationView mapDataToResponse(ClassicBikePrice classicBikePrice, String type) {
        return new ClassicBikeGeneralInformationView().builder()
                .productName("Rower typu "+type)
                .currency(classicBikePrice.getCurrency())
                .dayAndNightPrice(classicBikePrice.getDayAndNightPrice())
                .dayPrice(classicBikePrice.getDayPrice())
                .everyBeginHourPrice(classicBikePrice.getEveryBeginHourPrice())
                .build();
    }
}
