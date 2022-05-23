package com.karol.offerservice.offerMenager.business.service.generalBikeFactory;

import com.karol.offerservice.offerMenager.api.mapper.generalOfferMapper.GeneralClassicOfferMapper;
import com.karol.offerservice.offerMenager.api.response.generalInfoPackage.BikeGeneralOfferResponseView;
import com.karol.offerservice.offerMenager.api.response.generalInfoPackage.BikeGeneralOfferView;
import com.karol.offerservice.offerMenager.business.service.ImageService;
import com.karol.offerservice.offerMenager.data.entity.ClassicBike;
import com.karol.offerservice.offerMenager.data.repository.BikeTypeRepo;
import com.karol.offerservice.offerMenager.data.repository.ClassicBikeRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ClassicGeneralBikeGeneralOffer implements IGeneralBikeGeneralOffer {

    ClassicBikeRepo classicBikeRepo;
    BikeTypeRepo bikeTypeRepo;


    @Override
    public BikeGeneralOfferResponseView getGeneralOfferView(ImageService imageService, int pageNr, String section) {
        Pageable pageRequest = PageRequest.of(pageNr, MAX_PRODUCT_ON_PAGE, Sort.by(Sort.Direction.ASC, "product.name"));

        Page<ClassicBike> page = classicBikeRepo.findAllByBikeType_TypeAndProduct_Active(pageRequest, section, true);

        List<BikeGeneralOfferView> bikeGeneralOfferViews = page.getContent().stream()
                .map(product -> GeneralClassicOfferMapper.mapDataToResponse(product, imageService.getImagesForUrls(product.getUrl(), false)))
                .collect(Collectors.toList());

        BigDecimal minimalPrice = bikeTypeRepo.findLowestPriceBySection(section);

        return new BikeGeneralOfferResponseView(bikeGeneralOfferViews, page.getTotalPages(), minimalPrice);

    }
}
