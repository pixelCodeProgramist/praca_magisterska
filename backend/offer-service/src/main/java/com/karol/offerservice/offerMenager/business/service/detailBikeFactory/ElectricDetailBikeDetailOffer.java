package com.karol.offerservice.offerMenager.business.service.detailBikeFactory;

import com.karol.offerservice.offerMenager.api.dto.TimePriceDto;
import com.karol.offerservice.offerMenager.api.mapper.detailOfferMapper.DetailOfferMapper;
import com.karol.offerservice.offerMenager.api.response.detailsInfoPackage.Builder.DetailBikeInfoView;
import com.karol.offerservice.offerMenager.business.exception.offer.OfferNotFoundException;
import com.karol.offerservice.offerMenager.business.service.ImageService;
import com.karol.offerservice.offerMenager.data.entity.ElectricBike;
import com.karol.offerservice.offerMenager.data.entity.ElectricBikePrice;
import com.karol.offerservice.offerMenager.data.entity.Service;
import com.karol.offerservice.offerMenager.data.repository.ElectricBikePriceRepo;
import com.karol.offerservice.offerMenager.data.repository.ServiceRepo;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ElectricDetailBikeDetailOffer implements IDetailBikeDetailOffer {

    private ElectricBike electricBike;

    private ElectricBikePriceRepo electricBikePriceRepo;

    private ServiceRepo serviceRepo;

    @Override
    public DetailBikeInfoView getDetailOfferView(ImageService imageService) {

        Service guide = serviceRepo
                .findByProduct_Name("Wycieczka z przewodnikiem")
                .orElseThrow(OfferNotFoundException::new);
        List<ElectricBikePrice> electricBikePrices = electricBikePriceRepo.findAll();
        List<TimePriceDto> timePriceDtoList = electricBikePrices.stream()
                .map(electricBikePrice -> new TimePriceDto(electricBikePrice.getTime(), electricBikePrice.getPrice()))
                .collect(Collectors.toList());

        return DetailOfferMapper.mapDataToResponse(electricBike,imageService.getImagesForUrls(electricBike.getUrl()),guide.getPrice(), timePriceDtoList);
    }
}
