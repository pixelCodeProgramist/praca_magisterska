package com.karol.offerservice.offerMenager.api.mapper.detailOfferMapper;

import com.karol.offerservice.offerMenager.api.dto.TimePriceDto;
import com.karol.offerservice.offerMenager.api.response.detailsInfoPackage.Builder.DetailBikeInfoView;
import com.karol.offerservice.offerMenager.api.response.detailsInfoPackage.Builder.DetailBikeInfoViewBuilder;
import com.karol.offerservice.offerMenager.data.entity.ClassicBike;
import com.karol.offerservice.offerMenager.data.entity.ElectricBike;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class DetailOfferMapper {
    public DetailBikeInfoView mapDataToResponse(ClassicBike classicBike, byte[] image, List<TimePriceDto> timePriceDtoList, BigDecimal guidePrice){
        return new DetailBikeInfoViewBuilder()
                .id(classicBike.getId())
                .name(classicBike.getProduct().getName())
                .image(image)
                .ratingNumber(10)
                .rating(classicBike.getRating())
                .bikeType(classicBike.getBikeType().getType())
                .offerType(classicBike.getProduct().getProductType().getType())
                .bikeOfferType("Classic")
                .timePriceDtoList(timePriceDtoList)
                .guidePrice(guidePrice)
                .frames(classicBike.getFrames().stream().map(frame -> frame.getName()).collect(Collectors.toSet()))
                .build();
    }

    public DetailBikeInfoView mapDataToResponse(ElectricBike electricBike, byte[] image, BigDecimal guidePrice, List<TimePriceDto> timePriceDtoList){
        return new DetailBikeInfoViewBuilder()
                .id(electricBike.getId())
                .name(electricBike.getProduct().getName())
                .image(image)
                .ratingNumber(20)
                .rating(electricBike.getRating())
                .offerType(electricBike.getProduct().getProductType().getType())
                .guidePrice(guidePrice)
                .timePriceDtoList(timePriceDtoList)
                .bikeOfferType("Electric")
                .frames(electricBike.getFrames().stream().map(frame -> frame.getName()).collect(Collectors.toSet()))
                .build();
    }
}
