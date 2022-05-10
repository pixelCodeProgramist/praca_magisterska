package com.karol.offerservice.offerMenager.business.service.detailBikeFactory;

import com.karol.offerservice.offerMenager.api.dto.TimePriceDto;
import com.karol.offerservice.offerMenager.api.mapper.detailOfferMapper.DetailOfferMapper;
import com.karol.offerservice.offerMenager.api.response.detailsInfoPackage.Builder.DetailBikeInfoView;
import com.karol.offerservice.offerMenager.business.exception.offer.OfferNotFoundException;
import com.karol.offerservice.offerMenager.business.service.ImageService;
import com.karol.offerservice.offerMenager.data.entity.BikeType;
import com.karol.offerservice.offerMenager.data.entity.ClassicBike;
import com.karol.offerservice.offerMenager.data.entity.Service;
import com.karol.offerservice.offerMenager.data.repository.BikeTypeRepo;
import com.karol.offerservice.offerMenager.data.repository.ServiceRepo;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ClassicDetailBikeDetailOffer implements IDetailBikeDetailOffer {

    ClassicBike classicBike;

    BikeTypeRepo bikeTypeRepo;

    ServiceRepo serviceRepo;

    @Override
    public DetailBikeInfoView getDetailOfferView(ImageService imageService) {
        BikeType bikeType = bikeTypeRepo.findByType(classicBike.getBikeType().getType())
                .orElseThrow(OfferNotFoundException::new);
        List<TimePriceDto> timePriceDtoList = new ArrayList<>();
        timePriceDtoList.add(new TimePriceDto("godzina", bikeType.getClassicBikePrice().getEveryBeginHourPrice()));
        timePriceDtoList.add(new TimePriceDto("dzień", bikeType.getClassicBikePrice().getDayPrice()));
        timePriceDtoList.add(new TimePriceDto("doba", bikeType.getClassicBikePrice().getDayAndNightPrice()));
        Service guide = serviceRepo
                .findByProduct_Name("Wycieczka z przewodnikiem")
                .orElseThrow(OfferNotFoundException::new);

        return DetailOfferMapper.mapDataToResponse(classicBike,imageService.getImagesForUrls(classicBike.getUrl()),
                timePriceDtoList, guide.getPrice());
    }
}
