package com.karol.offerservice.offerMenager.business.service;

import com.karol.offerservice.offerMenager.api.mapper.*;
import com.karol.offerservice.offerMenager.api.response.*;
import com.karol.offerservice.offerMenager.data.entity.Accessory;
import com.karol.offerservice.offerMenager.data.entity.ClassicBike;
import com.karol.offerservice.offerMenager.data.entity.ElectricBike;
import com.karol.offerservice.offerMenager.data.entity.ElectricBikePrice;
import com.karol.offerservice.offerMenager.data.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OfferService {
    
    private AccessoryRepo accessoryRepo;
    private ClassicBikeRepo classicBikeRepo;
    private ElectricBikePriceRepo electricBikePriceRepo;
    private BikeTypeRepo bikeTypeRepo;
    private ImageService imageService;
    private ElectricBikeRepo electricBikeRepo;

    private static final int MAX_PRODUCT_ON_PAGE = 9;

    public List<ClassicProductGeneralInformationView> getAllGeneralClassicOfferInformation() {
        List<Accessory> accessories = accessoryRepo.findAll();

        return accessories.stream()
                .map(AccessoryMapper::mapDataToResponse)
                .collect(Collectors.toList());
    }

    public List<ElectricProductGeneralInformationView> getAllGeneralElectricOfferInformation() {
        List<ElectricBikePrice> electricBikePrices = electricBikePriceRepo.findAll();

        return electricBikePrices.stream()
                .map(ElectricBikePriceMapper::mapDataToResponse)
                .collect(Collectors.toList());
    }

    public ProductGeneralOfferResponseView getClassicProductsGeneralOffer(String section, int pageNr) {
        Pageable pageRequest = PageRequest.of(pageNr, MAX_PRODUCT_ON_PAGE, Sort.by(Sort.Direction.ASC, "product.name"));

        Page<ClassicBike> page = classicBikeRepo.findAllByBikeType_Type(pageRequest, section);

        List<ProductGeneralOfferView> productGeneralOfferViews = page.getContent().stream()
                .map(product-> GeneralClassicOfferMapper.mapDataToResponse(product, imageService.getImagesForUrls(product.getUrl())))
                .collect(Collectors.toList());

        productGeneralOfferViews.sort(Comparator.comparingInt(ProductGeneralOfferView::getId));
        BigDecimal minimalPrice = bikeTypeRepo.findLowestPriceBySection(section);

        return new ProductGeneralOfferResponseView(productGeneralOfferViews, page.getTotalPages(), minimalPrice);
    }

    public ProductGeneralOfferResponseView getElectricProductsGeneralOffer(int pageNr) {
        Pageable pageRequest = PageRequest.of(pageNr, MAX_PRODUCT_ON_PAGE, Sort.by(Sort.Direction.ASC, "product.name"));

        Page<ElectricBike> page = electricBikeRepo.findAll(pageRequest);

        List<ProductGeneralOfferView> productGeneralOfferViews = page.getContent().stream()
                .map(product-> GeneralElectricOfferMapper.mapDataToResponse(product, imageService.getImagesForUrls(product.getUrl())))
                .collect(Collectors.toList());

        productGeneralOfferViews.sort(Comparator.comparingInt(ProductGeneralOfferView::getId));
        BigDecimal minimalPrice = electricBikePriceRepo.findMinimalPrice();

        return new ProductGeneralOfferResponseView(productGeneralOfferViews, page.getTotalPages(), minimalPrice);

    }

    public AccessoryGeneralOfferResponseView getAccessoryProductsGeneralOffer(int pageNr) {
        Pageable pageRequest = PageRequest.of(pageNr, MAX_PRODUCT_ON_PAGE, Sort.by(Sort.Direction.ASC, "product.name"));

        Page<Accessory> page = accessoryRepo.findAll(pageRequest);

        List<AccessoryGeneralOfferView> productGeneralOfferViews = page.getContent().stream()
                .map(product-> GeneralAccessoryOfferMapper.mapDataToResponse(product, imageService.getImagesForUrls(product.getUrl())))
                .collect(Collectors.toList());

        productGeneralOfferViews.sort(Comparator.comparingInt(AccessoryGeneralOfferView::getId));


        return new AccessoryGeneralOfferResponseView(productGeneralOfferViews, page.getTotalPages());

    }

}