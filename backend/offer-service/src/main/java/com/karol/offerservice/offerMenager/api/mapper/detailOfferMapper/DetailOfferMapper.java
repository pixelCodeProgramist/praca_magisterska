package com.karol.offerservice.offerMenager.api.mapper.detailOfferMapper;

import com.karol.offerservice.offerMenager.api.dto.TimePriceDto;
import com.karol.offerservice.offerMenager.api.response.detailsInfoPackage.Builder.DetailBikeInfoView;
import com.karol.offerservice.offerMenager.api.response.detailsInfoPackage.Builder.DetailBikeInfoViewBuilder;
import com.karol.offerservice.offerMenager.api.response.detailsInfoPackage.Builder.FrameView;
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
                .ratingNumber(classicBike.getProduct().getUserGradeProducts().size())
                .rating(classicBike.getProduct().getRating())
                .bikeType(classicBike.getBikeType().getType())
                .offerType(classicBike.getProduct().getProductType().getType())
                .bikeOfferType("Classic")
                .timePriceDtoList(timePriceDtoList)
                .guidePrice(guidePrice)
                .frames(classicBike.getClassicBikeFrameInventories().stream().map(frame->
                        new FrameView(frame.getFrame().getName(), frame.getAvailableNumber())).collect(Collectors.toSet()))
                .build();
    }

    public DetailBikeInfoView mapDataToResponse(ElectricBike electricBike, byte[] image, BigDecimal guidePrice, List<TimePriceDto> timePriceDtoList){
        return new DetailBikeInfoViewBuilder()
                .name(electricBike.getProduct().getName())
                .image(image)
                .ratingNumber(electricBike.getProduct().getUserGradeProducts().size())
                .rating(electricBike.getProduct().getRating())
                .offerType(electricBike.getProduct().getProductType().getType())
                .guidePrice(guidePrice)
                .timePriceDtoList(timePriceDtoList)
                .bikeOfferType("Electric")
                .frames(electricBike.getElectricBikeFrameInventories().stream().map(frame->
                        new FrameView(frame.getFrame().getName(), frame.getAvailableNumber())).collect(Collectors.toSet()))
                .build();
    }
}
