package com.karol.offerservice.offerMenager.api.mapper.generalOfferMapper;

import com.karol.offerservice.offerMenager.api.response.generalInfoPackage.ElectricBikeGeneralInformationView;
import com.karol.offerservice.offerMenager.data.entity.ElectricBikePrice;
import lombok.experimental.UtilityClass;


@UtilityClass
public class ElectricBikePriceMapper {
    public ElectricBikeGeneralInformationView mapDataToResponse(ElectricBikePrice electricBikePrice) {
        return new ElectricBikeGeneralInformationView().builder()
                .id(electricBikePrice.getId())
                .price(electricBikePrice.getPrice())
                .currency(electricBikePrice.getCurrency())
                .time(electricBikePrice.getTime())
                .build();
    }
}
