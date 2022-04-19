package com.karol.offerservice.offerMenager.business.service;

import com.karol.offerservice.offerMenager.api.mapper.generalOfferMapper.AccessoryMapper;
import com.karol.offerservice.offerMenager.api.mapper.generalOfferMapper.ElectricBikePriceMapper;
import com.karol.offerservice.offerMenager.api.mapper.generalOfferMapper.GeneralAccessoryOfferMapper;
import com.karol.offerservice.offerMenager.api.mapper.generalOfferMapper.SearchBikeMapper;
import com.karol.offerservice.offerMenager.api.response.detailsInfoPackage.Builder.DetailBikeInfoView;
import com.karol.offerservice.offerMenager.api.response.generalInfoPackage.*;
import com.karol.offerservice.offerMenager.business.exception.OfferError;
import com.karol.offerservice.offerMenager.business.exception.OfferException;
import com.karol.offerservice.offerMenager.business.service.detailBikeFactory.DetailBikeFactory;
import com.karol.offerservice.offerMenager.business.service.generalBikeFactory.GeneralBikeFactory;
import com.karol.offerservice.offerMenager.data.entity.*;
import com.karol.offerservice.offerMenager.data.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class OfferService {
    
    private AccessoryRepo accessoryRepo;

    private ClassicBikeRepo classicBikeRepo;
    private ElectricBikePriceRepo electricBikePriceRepo;
    private BikeTypeRepo bikeTypeRepo;
    private ImageService imageService;
    private ElectricBikeRepo electricBikeRepo;
    private ServiceRepo serviceRepo;
    private ProductRepo productRepo;

    private static final int MAX_PRODUCT_ON_PAGE = 9;

    public List<ClassicBikeGeneralInformationView> getAllGeneralClassicOfferInformation() {
        List<Accessory> accessories = accessoryRepo.findAll();

        return accessories.stream()
                .map(AccessoryMapper::mapDataToResponse)
                .collect(Collectors.toList());
    }

    public List<ElectricBikeGeneralInformationView> getAllGeneralElectricOfferInformation() {
        List<ElectricBikePrice> electricBikePrices = electricBikePriceRepo.findAll();

        return electricBikePrices.stream()
                .map(ElectricBikePriceMapper::mapDataToResponse)
                .collect(Collectors.toList());
    }

    public BikeGeneralOfferResponseView getClassicProductsGeneralOffer(String section, int pageNr) {
        GeneralBikeFactory bikeFactory = new GeneralBikeFactory(classicBikeRepo, bikeTypeRepo);
        return bikeFactory.getBike("classic")
                .getGeneralOfferView(imageService, pageNr, section);

    }

    public BikeGeneralOfferResponseView getElectricProductsGeneralOffer(int pageNr) {
        GeneralBikeFactory bikeFactory = new GeneralBikeFactory(electricBikeRepo, electricBikePriceRepo);
        return bikeFactory.getBike("electric")
                .getGeneralOfferView(imageService, pageNr, null);

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

    public DetailBikeInfoView getBikeInformation(Long id) {
        Product product = productRepo.findById(id).orElseThrow(() -> new OfferException(OfferError.OFFER_NOT_FOUND_EXCEPTION));
        String type = product.getProductType().getType();
        if("bike".equalsIgnoreCase(type)) {
            ClassicBike classicBike = classicBikeRepo.findByProduct(product).orElse(null);
            ElectricBike electricBike = electricBikeRepo.findByProduct(product).orElse(null);
            if(classicBike == null && electricBike == null) throw new OfferException(OfferError.OFFER_NOT_FOUND_EXCEPTION);


            if(classicBike != null) {
                DetailBikeFactory detailBikeFactory = new DetailBikeFactory(classicBike, bikeTypeRepo, serviceRepo);
                return detailBikeFactory.getBike("classic").getDetailOfferView(imageService);
            }

            if(electricBike != null) {
                DetailBikeFactory detailBikeFactory = new DetailBikeFactory(electricBike, electricBikePriceRepo, serviceRepo);
                return detailBikeFactory.getBike("electric").getDetailOfferView(imageService);
            }


        }
        return null;
    }

    public List<BikeForSearchView> getAllBikes() {
        List<ClassicBike> classicBikes = classicBikeRepo.findAll();
        List<ElectricBike> electricBikes = electricBikeRepo.findAll();
        List<BikeForSearchView> classicBikesView = classicBikes.stream()
                .map(product-> SearchBikeMapper.mapDataToResponse(product, imageService.getImagesForUrls(product.getUrl())))
                .collect(Collectors.toList());
        List<BikeForSearchView> electricBikesView = electricBikes.stream()
                .map(product-> SearchBikeMapper.mapDataToResponse(product, imageService.getImagesForUrls(product.getUrl())))
                .collect(Collectors.toList());

        return Stream.concat(classicBikesView.stream(), electricBikesView.stream()).distinct().collect(Collectors.toList());

    }
}