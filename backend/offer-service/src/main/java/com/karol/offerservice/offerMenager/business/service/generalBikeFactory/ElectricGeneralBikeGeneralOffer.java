package com.karol.offerservice.offerMenager.business.service.generalBikeFactory;

import com.karol.offerservice.offerMenager.api.mapper.generalOfferMapper.GeneralElectricOfferMapper;
import com.karol.offerservice.offerMenager.api.response.generalInfoPackage.BikeGeneralOfferResponseView;
import com.karol.offerservice.offerMenager.api.response.generalInfoPackage.BikeGeneralOfferView;
import com.karol.offerservice.offerMenager.business.service.ImageService;
import com.karol.offerservice.offerMenager.data.entity.ElectricBike;
import com.karol.offerservice.offerMenager.data.repository.ElectricBikePriceRepo;
import com.karol.offerservice.offerMenager.data.repository.ElectricBikeRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ElectricGeneralBikeGeneralOffer implements IGeneralBikeGeneralOffer {

    private ElectricBikeRepo electricBikeRepo;

    private ElectricBikePriceRepo electricBikePriceRepo;
    @Override
    public BikeGeneralOfferResponseView getGeneralOfferView(ImageService imageService, int pageNr, String section) {
        Pageable pageRequest = PageRequest.of(pageNr, MAX_PRODUCT_ON_PAGE, Sort.by(Sort.Direction.ASC, "product.name"));

        Page<ElectricBike> page = electricBikeRepo.findAllByProduct_Active(pageRequest, true);

        List<BikeGeneralOfferView> bikeGeneralOfferViews = page.getContent().stream()
                .map(product-> GeneralElectricOfferMapper.mapDataToResponse(product, imageService.getImagesForUrls(product.getUrl())))
                .collect(Collectors.toList());

        bikeGeneralOfferViews.sort(Comparator.comparingLong(BikeGeneralOfferView::getId));
        BigDecimal minimalPrice = electricBikePriceRepo.findMinimalPrice();

        return new BikeGeneralOfferResponseView(bikeGeneralOfferViews, page.getTotalPages(), minimalPrice);
    }
}
