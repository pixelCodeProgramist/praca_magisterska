package com.karol.offerservice.offerMenager.api.mapper;

import com.karol.offerservice.offerMenager.api.response.ElectricProductGeneralInformationView;
import com.karol.offerservice.offerMenager.data.entity.ElectricBikePrice;
import lombok.experimental.UtilityClass;


@UtilityClass
public class ElectricBikePriceMapper {
    public ElectricProductGeneralInformationView mapDataToResponse(ElectricBikePrice electricBikePrice) {
        return new ElectricProductGeneralInformationView().builder()
                .price(electricBikePrice.getPrice())
                .currency(electricBikePrice.getCurrency())
                .time(electricBikePrice.getTime())
                .build();
    }
}
