package com.karol.offerservice.offerMenager.business.service;

import com.karol.offerservice.business.exception.AuthorizationException;
import com.karol.offerservice.business.request.User;
import com.karol.offerservice.business.request.UserByIdRequest;
import com.karol.offerservice.business.service.JwtTokenNonUserProvider;
import com.karol.offerservice.business.service.JwtTokenProvider;
import com.karol.offerservice.offerMenager.api.mapper.generalOfferMapper.*;
import com.karol.offerservice.offerMenager.api.request.DateAndHourOfReservationRequest;
import com.karol.offerservice.offerMenager.api.request.DateAndHourOfReservationWithTokenRequest;
import com.karol.offerservice.offerMenager.api.request.GradeRequest;
import com.karol.offerservice.offerMenager.api.response.AccessoryInformationInOrderView;
import com.karol.offerservice.offerMenager.api.response.detailsInfoPackage.Builder.DetailBikeInfoView;
import com.karol.offerservice.offerMenager.api.response.generalInfoPackage.*;
import com.karol.offerservice.offerMenager.business.exception.offer.BikeWithFrameNotAvailableException;
import com.karol.offerservice.offerMenager.business.exception.offer.OfferNotFoundException;
import com.karol.offerservice.offerMenager.business.service.detailBikeFactory.DetailBikeFactory;
import com.karol.offerservice.offerMenager.business.service.generalBikeFactory.GeneralBikeFactory;
import com.karol.offerservice.offerMenager.data.entity.*;
import com.karol.offerservice.offerMenager.data.repository.*;
import com.karol.offerservice.offerMenager.dto.AvailableHoursResponse;
import com.karol.offerservice.offerMenager.feignClient.OrderServiceFeignClient;
import com.karol.offerservice.offerMenager.feignClient.UserServiceFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.math.BigDecimal;
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
    private ClassicBikePriceRepo classicBikePriceRepo;
    private BikeTypeRepo bikeTypeRepo;
    private ImageService imageService;
    private ElectricBikeRepo electricBikeRepo;
    private ServiceRepo serviceRepo;
    private ProductRepo productRepo;
    private JwtTokenProvider jwtTokenProvider;
    private JwtTokenNonUserProvider tokenNonUserProvider;
    private UserServiceFeignClient userServiceFeignClient;
    private OrderServiceFeignClient orderServiceFeignClient;
    private UserGradeRepo userGradeRepo;
    private UserGradeProductRepo userGradeProductRepo;

    private static final int MAX_PRODUCT_ON_PAGE = 9;

    public List<ClassicBikeGeneralInformationView> getAllGeneralClassicOfferInformation() {
        List<Accessory> accessories = accessoryRepo.findAll();
        List<ClassicBikePrice> classicBikePrices = classicBikePriceRepo.findAll();

        return Stream.concat(
                classicBikePrices.stream()
                        .map(classicBikePrice -> ClassicBikePriceMapper.mapDataToResponse(classicBikePrice, classicBikePrice.getBikeType().getType()))
                        .collect(Collectors.toList()).stream(),
                accessories.stream()
                        .map(AccessoryMapper::mapDataToResponse)
                        .collect(Collectors.toList()).stream()
        ).collect(Collectors.toList());
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
                .map(product -> GeneralAccessoryOfferMapper.mapDataToResponse(product, imageService.getImagesForUrls(product.getUrl())))
                .collect(Collectors.toList());

        productGeneralOfferViews.sort(Comparator.comparingLong(AccessoryGeneralOfferView::getId));


        return new AccessoryGeneralOfferResponseView(productGeneralOfferViews, page.getTotalPages());

    }

    public DetailBikeInfoView getBikeInformation(Long id) {
        Product product = productRepo.findById(id).orElseThrow(OfferNotFoundException::new);
        String type = product.getProductType().getType();
        if ("bike".equalsIgnoreCase(type)) {
            ClassicBike classicBike = classicBikeRepo.findByProduct(product).orElse(null);
            ElectricBike electricBike = electricBikeRepo.findByProduct(product).orElse(null);
            if (classicBike == null && electricBike == null) throw new OfferNotFoundException();


            if (classicBike != null) {
                DetailBikeFactory detailBikeFactory = new DetailBikeFactory(classicBike, bikeTypeRepo, serviceRepo);
                return detailBikeFactory.getBike("classic").getDetailOfferView(imageService);
            }

            if (electricBike != null) {
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
                .map(product -> SearchBikeMapper.mapDataToResponse(product, imageService.getImagesForUrls(product.getUrl())))
                .collect(Collectors.toList());
        List<BikeForSearchView> electricBikesView = electricBikes.stream()
                .map(product -> SearchBikeMapper.mapDataToResponse(product, imageService.getImagesForUrls(product.getUrl())))
                .collect(Collectors.toList());

        return Stream.concat(classicBikesView.stream(), electricBikesView.stream()).distinct().collect(Collectors.toList());

    }


    public AvailableHoursResponse getAvailableHours(DateAndHourOfReservationRequest dateAndHourOfReservationRequest) {
        ClassicBike classicBike = classicBikeRepo.findById(dateAndHourOfReservationRequest.getBikeId()).orElse(null);
        ElectricBike electricBike = electricBikeRepo.findById(dateAndHourOfReservationRequest.getBikeId()).orElse(null);
        AvailableHoursResponse availableHoursResponse = null;
        if (classicBike == null && electricBike == null) throw new OfferNotFoundException();
        if (classicBike != null) {
            ClassicBikeFrameInventory classicBikeFrameInventory = classicBike.getClassicBikeFrameInventories().stream().filter(inventory ->
                            inventory.getFrame().getName().equalsIgnoreCase(dateAndHourOfReservationRequest.getFrame()) &&
                                    inventory.getAvailableNumber() > 0).findFirst()
                    .orElseThrow(() -> new BikeWithFrameNotAvailableException(dateAndHourOfReservationRequest.getFrame()));
            DateAndHourOfReservationWithTokenRequest dateAndHourOfReservationWithTokenRequest =
                    new DateAndHourOfReservationWithTokenRequest(dateAndHourOfReservationRequest, classicBikeFrameInventory.getAvailableNumber(),
                            classicBike.getId(), classicBikeFrameInventory.getId(), tokenNonUserProvider.generateToken());
            availableHoursResponse = orderServiceFeignClient.getAvailableTime(dateAndHourOfReservationWithTokenRequest);
        }
        if (electricBike != null) {
            ElectricBikeFrameInventory electricBikeFrameInventory = electricBike.getElectricBikeFrameInventories().stream().filter(inventory ->
                            inventory.getFrame().getName().equalsIgnoreCase(dateAndHourOfReservationRequest.getFrame()) &&
                                    inventory.getAvailableNumber() > 0).findFirst()
                    .orElseThrow(() -> new BikeWithFrameNotAvailableException(dateAndHourOfReservationRequest.getFrame()));
            DateAndHourOfReservationWithTokenRequest dateAndHourOfReservationWithTokenRequest =
                    new DateAndHourOfReservationWithTokenRequest(dateAndHourOfReservationRequest, electricBikeFrameInventory.getAvailableNumber(),
                            electricBike.getId(), electricBikeFrameInventory.getId(), tokenNonUserProvider.generateToken());
            availableHoursResponse = orderServiceFeignClient.getAvailableTime(dateAndHourOfReservationWithTokenRequest);
        }
        return availableHoursResponse;
    }

    public List<AccessoryInformationInOrderView> getAllAccessories(Integer range) {
        List<Accessory> accessories = accessoryRepo.findAll();
        return accessories.stream()
                .map(accessory -> AccessoryMapper.mapDataAccessoryInOrderToResponse(accessory, range))
                .collect(Collectors.toList());
    }

    @Transactional
    public void grade(GradeRequest gradeRequest, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization");
        if (token != null) {
            token = token.substring(7);
            Long userId = jwtTokenProvider.extractUserId(token);
            User user = userServiceFeignClient.getUserById(new UserByIdRequest(userId, tokenNonUserProvider.generateToken()));
            if(user==null) throw new UsernameNotFoundException(" with id: "+ userId);
            Product product = productRepo.findById(gradeRequest.getProductId()).orElseThrow(OfferNotFoundException::new);
            UserGrade userGrade = userGradeRepo.findByUserId(userId).orElse(null);
            if(userGrade == null) {
                userGrade = UserGrade.builder()
                        .userId(userId)
                        .build();
            }

            UserGradeProduct userGradeProduct = UserGradeProduct.builder()
                    .product(product)
                    .userGrade(userGrade)
                    .rating(gradeRequest.getRating())
                    .build();

            userGradeRepo.save(userGrade);
            userGradeProductRepo.save(userGradeProduct);
            double rating = product.getUserGradeProducts().stream()
                    .mapToDouble(usp -> usp.getRating().doubleValue())
                    .average().getAsDouble();
            product.setRating(new BigDecimal(rating));
            productRepo.save(product);


        }
    }

    public boolean canGrade(Long productId, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization");
        if (token != null) {
            token = token.substring(7);
            Long userId = jwtTokenProvider.extractUserId(token);
            User user = userServiceFeignClient.getUserById(new UserByIdRequest(userId, tokenNonUserProvider.generateToken()));
            if (user == null) throw new UsernameNotFoundException(" with id: " + userId);
            Product product = productRepo.findById(productId).orElseThrow(OfferNotFoundException::new);
            UserGradeProduct userGradeProduct = userGradeProductRepo.findByProductAndUserGrade_UserId(product, userId).orElse(null);
            if (userGradeProduct == null) return true;
            return false;
        }

        throw new AuthorizationException();
    }
}