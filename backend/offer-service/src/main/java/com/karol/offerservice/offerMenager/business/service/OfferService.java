package com.karol.offerservice.offerMenager.business.service;

import com.karol.offerservice.business.exception.AuthorizationException;
import com.karol.offerservice.business.request.AddBikeRequest;
import com.karol.offerservice.business.request.User;
import com.karol.offerservice.business.request.UserByIdRequest;
import com.karol.offerservice.business.service.JwtTokenNonUserProvider;
import com.karol.offerservice.business.service.JwtTokenProvider;
import com.karol.offerservice.offerMenager.api.mapper.ClassicBikeInventoryMapper;
import com.karol.offerservice.offerMenager.api.mapper.ElectricBikeInventoryMapper;
import com.karol.offerservice.offerMenager.api.mapper.generalOfferMapper.*;
import com.karol.offerservice.offerMenager.api.request.*;
import com.karol.offerservice.offerMenager.api.response.AccessoryInformationInOrderView;
import com.karol.offerservice.offerMenager.api.response.ClassicBikePriceView;
import com.karol.offerservice.offerMenager.api.response.OrderNameProductForStatisticResponse;
import com.karol.offerservice.offerMenager.api.response.OrderNameProductResponse;
import com.karol.offerservice.offerMenager.api.response.detailsInfoPackage.Builder.DetailBikeInfoView;
import com.karol.offerservice.offerMenager.api.response.generalInfoPackage.*;
import com.karol.offerservice.offerMenager.business.exception.bikePrice.BikePriceArraySizeException;
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
import org.apache.commons.lang.ArrayUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
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
    private ProductTypeRepo productTypeRepo;
    private EntityManager entityManager;

    private ClassicBikeFrameInventoryRepo classicBikeFrameInventoryRepo;

    private ElectricBikeFrameInventoryRepo electricBikeFrameInventoryRepo;
    private FrameRepo frameRepo;

    private static final int MAX_PRODUCT_ON_PAGE = 9;

    public List<ClassicBikeGeneralInformationView> getAllGeneralClassicOfferInformation() {
        List<Accessory> accessories = accessoryRepo.findAllByProduct_Active(true);
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

        Page<Accessory> page = accessoryRepo.findAllByProduct_Active(pageRequest, true);

        List<AccessoryGeneralOfferView> productGeneralOfferViews = page.getContent().stream()
                .map(product -> GeneralAccessoryOfferMapper.mapDataToResponse(product, imageService.getImagesForUrls(product.getUrl(), false)))
                .collect(Collectors.toList());

        productGeneralOfferViews.sort(Comparator.comparingLong(AccessoryGeneralOfferView::getId));


        return new AccessoryGeneralOfferResponseView(productGeneralOfferViews, page.getTotalPages());

    }

    @Transactional
    public DetailBikeInfoView getBikeInformation(Long id, boolean all) {
        Product product = all ? productRepo.findByProductId(id).orElseThrow(OfferNotFoundException::new) :
                productRepo.findByProductIdAndActive(id, true).orElseThrow(OfferNotFoundException::new);
        String type = product.getProductType().getType();
        if ("bike".equalsIgnoreCase(type)) {
            ClassicBike classicBike = all ? classicBikeRepo.findByProduct(product).orElse(null):
                    classicBikeRepo.findByProductAndProduct_Active(product, true).orElse(null);
            ElectricBike electricBike =  all ? electricBikeRepo.findByProduct(product).orElse(null):
                    electricBikeRepo.findByProductAndProduct_Active(product, true).orElse(null);
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

    public List<BikeForSearchView> getAllBikes(boolean all, boolean isTest) {
        List<ClassicBike> classicBikes = all ? classicBikeRepo.findAll() : classicBikeRepo.findByProduct_Active(true);
        List<ElectricBike> electricBikes = all ? electricBikeRepo.findAll() : electricBikeRepo.findByProduct_Active(true);
        List<BikeForSearchView> classicBikesView = classicBikes.stream()
                .map(product -> SearchBikeMapper.mapDataToResponse(product, imageService.getImagesForUrls(product.getUrl(), isTest)))
                .collect(Collectors.toList());
        List<BikeForSearchView> electricBikesView = electricBikes.stream()
                .map(product -> SearchBikeMapper.mapDataToResponse(product, imageService.getImagesForUrls(product.getUrl(), isTest)))
                .collect(Collectors.toList());

        return Stream.concat(classicBikesView.stream(), electricBikesView.stream()).distinct().collect(Collectors.toList());

    }


    public AvailableHoursResponse getAvailableHours(DateAndHourOfReservationRequest dateAndHourOfReservationRequest) {
        ClassicBike classicBike = classicBikeRepo.findByIdAndProduct_Active(dateAndHourOfReservationRequest.getBikeId(), true).orElse(null);
        ElectricBike electricBike = electricBikeRepo.findByIdAndProduct_Active(dateAndHourOfReservationRequest.getBikeId(), true).orElse(null);
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
        List<Accessory> accessories = accessoryRepo.findAllByProduct_Active(true);
        return accessories.stream()
                .map(accessory -> AccessoryMapper.mapDataAccessoryInOrderToResponse(accessory, range))
                .collect(Collectors.toList());
    }

    @Transactional
    public void grade(GradeRequest gradeRequest, HttpServletRequest httpServletRequest, boolean isTest) {
        String token = httpServletRequest.getHeader("Authorization");
        if (token != null) {
            token = token.substring(7);
            Long userId = jwtTokenProvider.extractUserId(token);
            User user = null;
            if(!isTest) user = userServiceFeignClient.getUserById(new UserByIdRequest(userId, tokenNonUserProvider.generateToken()));
            else user = User.builder().userId(1L).build();
            if (user == null) throw new UsernameNotFoundException(" with id: " + userId);
            Product product = productRepo.findByProductIdAndActive(gradeRequest.getProductId(), true).orElseThrow(OfferNotFoundException::new);
            UserGrade userGrade = userGradeRepo.findByUserId(userId).orElse(null);
            if (userGrade == null) {
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

    @Transactional
    public boolean canGrade(Long productId, HttpServletRequest httpServletRequest, boolean isTest) {
        String token = httpServletRequest.getHeader("Authorization");
        if (token != null) {
            token = token.substring(7);
            Long userId = jwtTokenProvider.extractUserId(token);
            User user =null;
            if(!isTest) user= userServiceFeignClient.getUserById(new UserByIdRequest(userId, tokenNonUserProvider.generateToken()));
            else user = User.builder().userId(1L).build();
            if (user == null) throw new UsernameNotFoundException(" with id: " + userId);
            Product product = productRepo.findByProductIdAndActive(productId, true).orElseThrow(OfferNotFoundException::new);
            UserGradeProduct userGradeProduct = userGradeProductRepo.findByProductAndUserGrade_UserId(product, userId).orElse(null);
            if (userGradeProduct == null) return true;
            return false;
        }

        throw new AuthorizationException();
    }

    public Boolean isProductInDb(String type, Long id) {
        Product product = productRepo.findById(id).orElse(null);
        if (product == null) return false;
        if (type.equalsIgnoreCase(product.getProductType().getType())) return true;
        return false;
    }

    public Integer getFrameId(String frameType, Long id) {
        ClassicBikeFrameInventory classicBikeFrameInventory =
                classicBikeFrameInventoryRepo.findByClassicBike_IdAndFrame_Name(id, frameType).orElse(null);
        ElectricBikeFrameInventory electricBikeFrameInventory =
                electricBikeFrameInventoryRepo.findByElectricBike_IdAndFrame_Name(id, frameType).orElse(null);

        if (classicBikeFrameInventory != null) return classicBikeFrameInventory.getFrame().getFrameId();
        if (electricBikeFrameInventory != null) return electricBikeFrameInventory.getFrame().getFrameId();
        return -1;
    }

    public OrderNameProductResponse getOrderNames(OrderNameProductRequest orderNameProductRequest) {
        ClassicBikeFrameInventory classicBikeFrameInventory =
                classicBikeFrameInventoryRepo.findById(orderNameProductRequest.getBikeId()).orElse(null);
        ElectricBikeFrameInventory electricBikeFrameInventory =
                electricBikeFrameInventoryRepo.findById(orderNameProductRequest.getBikeId()).orElse(null);
        Accessory accessory = orderNameProductRequest.getAccessoryId() != null ?
                accessoryRepo.findByIdAndProduct_Active(orderNameProductRequest.getAccessoryId(), true).orElse(null) : null;

        OrderNameProductResponse orderNameProductResponse = new OrderNameProductResponse();

        if (classicBikeFrameInventory == null && electricBikeFrameInventory == null) return null;

        if (classicBikeFrameInventory != null) {
            orderNameProductResponse.setBike(classicBikeFrameInventory.getClassicBike().getProduct().getName());
            orderNameProductResponse.setFrame(classicBikeFrameInventory.getFrame().getName());
        }
        if (electricBikeFrameInventory != null) {
            orderNameProductResponse.setBike(electricBikeFrameInventory.getElectricBike().getProduct().getName());
            orderNameProductResponse.setFrame(electricBikeFrameInventory.getFrame().getName());
        }
        if (accessory != null) orderNameProductResponse.setAccessory(accessory.getProduct().getName());

        return orderNameProductResponse;
    }

    public ServiceGeneralInfoView getServiceInfo(ServiceRequest serviceRequest) {
        com.karol.offerservice.offerMenager.data.entity.Service service = serviceRepo.findByProduct_Name(serviceRequest.getName()).orElseThrow(() -> new OfferNotFoundException());

        return ServiceGeneralInfoView.builder()
                .id(service.getId())
                .currency(service.getCurrency())
                .name(service.getProduct().getName())
                .price(service.getPrice())
                .build();
    }

    public ServiceGeneralInfoView getServiceInfo(Long id) {
        com.karol.offerservice.offerMenager.data.entity.Service service = serviceRepo.findById(id).orElseThrow(() -> new OfferNotFoundException());
        return ServiceGeneralInfoView.builder()
                .id(service.getId())
                .currency(service.getCurrency())
                .name(service.getProduct().getName())
                .price(service.getPrice())
                .build();
    }

    @Transactional
    public void addOffer(AddBikeRequest addBikeRequest, boolean isTest) throws IOException {
        if (addBikeRequest.getSelectedProductTypeOption().toLowerCase().contains("rower")) {
            ProductType productType = productTypeRepo.findByTypeIgnoreCase("bike").orElseThrow(() ->
                    new OfferNotFoundException("with type option " + addBikeRequest.getSelectedProductTypeOption()));

            Product product = Product.builder()
                    .name(addBikeRequest.getName())
                    .productType(productType)
                    .active(true)
                    .rating(new BigDecimal(0))
                    .build();

            product = productRepo.save(product);


            entityManager.createNativeQuery("ALTER TABLE classic_bike AUTO_INCREMENT =" + (product.getProductId())).executeUpdate();
            entityManager.createNativeQuery("ALTER TABLE electric_bike AUTO_INCREMENT =" + (product.getProductId())).executeUpdate();


            if (addBikeRequest.getSelectedProductTypeOption().toLowerCase().contains("elektryczny")) {

                Set<ElectricBikeFrameInventory> electricBikeFrameInventories = addBikeRequest.getFrames().stream()
                        .map(frame -> {
                            Frame frameFromDb = frameRepo.findByName(frame.getFrameSize()).orElse(null);
                            if (frameFromDb == null) {
                                frameFromDb = Frame.builder()
                                        .name(frame.getFrameSize())
                                        .build();
                                frameRepo.save(frameFromDb);
                            }
                            return ElectricBikeInventoryMapper.mapDataToResponse(frame, frameFromDb);
                        })
                        .collect(Collectors.toSet());
                ElectricBike electricBike = ElectricBike.builder()
                        .electricBikeFrameInventories(electricBikeFrameInventories)
                        .product(product)
                        .url(saveImage(addBikeRequest, isTest))
                        .build();

                product.setElectricBike(electricBike);
                electricBikeFrameInventories.stream().forEach(electricBikeFrameInventory -> electricBikeFrameInventory.setElectricBike(electricBike));
                electricBikeRepo.save(electricBike);


            }
            if (addBikeRequest.getSelectedProductTypeOption().toLowerCase().contains("klasyczny")) {
                BikeType bikeType = bikeTypeRepo.findByType(addBikeRequest.getSelectedBikeOrAccessoryTypeOption())
                        .orElseThrow(() -> new OfferNotFoundException(addBikeRequest.getSelectedBikeOrAccessoryTypeOption()));

                Set<ClassicBikeFrameInventory> classicBikeFrameInventories = addBikeRequest.getFrames().stream()
                        .map(frame -> {
                            Frame frameFromDb = frameRepo.findByName(frame.getFrameSize()).orElse(null);
                            if (frameFromDb == null) {
                                frameFromDb = Frame.builder()
                                        .name(frame.getFrameSize())
                                        .build();
                                frameRepo.save(frameFromDb);
                            }
                            return ClassicBikeInventoryMapper.mapDataToResponse(frame, frameFromDb);
                        })
                        .collect(Collectors.toSet());


                ClassicBike classicBike = ClassicBike.builder()
                        .classicBikeFrameInventories(classicBikeFrameInventories)
                        .product(product)
                        .url(saveImage(addBikeRequest, isTest))
                        .build();

                product.setClassicBike(classicBike);


                classicBike.setBikeType(bikeType);

                classicBikeFrameInventories.stream().forEach(classicBikeFrameInventory -> classicBikeFrameInventory.setClassicBike(classicBike));


                classicBikeRepo.save(classicBike);
            }
        }
    }

    public String saveImage(AddBikeRequest addBikeRequest, boolean isTest) throws IOException {
        if(!isTest) {
            Byte[] qrImage = addBikeRequest.getImage();
            ByteArrayInputStream bis = new ByteArrayInputStream(ArrayUtils.toPrimitive(qrImage));
            BufferedImage bImage2 = ImageIO.read(bis);
            String fileName = UUID.randomUUID().toString();
            String userDirectory = System.getProperty("user.dir");
            String[] parts = userDirectory.split("/");
            userDirectory = "";
            for (int i = 0; i < parts.length - 1; i++)
                userDirectory += parts[i] + "/";

            userDirectory += "offer-service/src/main/resources/static/offer/";
            boolean isElectric = "Rower elektryczny".equalsIgnoreCase(addBikeRequest.getSelectedProductTypeOption());
            boolean isClassic = "Rower klasyczny".equalsIgnoreCase(addBikeRequest.getSelectedProductTypeOption());
            if (isElectric) {
                userDirectory += "electric/";
            }

            if (isClassic) {
                userDirectory += addBikeRequest.getSelectedBikeOrAccessoryTypeOption().toLowerCase() + "/";
            }

            File file = new File(userDirectory + fileName + ".png");

            ImageIO.write(bImage2, "png", file);
            if (isElectric)
                return "offer/electric" + "/" + fileName + ".png";
            return "offer/" + addBikeRequest.getSelectedBikeOrAccessoryTypeOption().toLowerCase() + "/" + fileName + ".png";
        }
        return "url test";
    }

    public void changeOfferActivity(Long id) {
        Product product = productRepo.findById(id).orElseThrow(()-> new OfferNotFoundException(" with id: "+id));
        product.setActive(!product.getActive());
        productRepo.save(product);
    }

    public List<ClassicBikePriceView> getClassicBikePrices() {
        return classicBikePriceRepo.findAll().stream()
                .map(ClassicBikePriceMapper::mapDataToResponse)
                .collect(Collectors.toList());
    }

    public void updateClassicBikePrices(List<ClassicBikePriceRequest> classicBikePriceRequest) {
        List<ClassicBikePrice> classicBikePrices = classicBikePriceRepo.findAll();
        if(classicBikePrices.size() != classicBikePriceRequest.size()) throw new BikePriceArraySizeException();
        for(int i=0;i<classicBikePrices.size();i++) {
            classicBikePrices.get(i).setEveryBeginHourPrice(classicBikePriceRequest.get(i).getEveryBeginHourPrice());
            classicBikePrices.get(i).setDayAndNightPrice(classicBikePriceRequest.get(i).getDayAndNightPrice());
            classicBikePrices.get(i).setDayPrice(classicBikePriceRequest.get(i).getDayPrice());
            classicBikePriceRepo.save(classicBikePrices.get(i));
        }

    }

    public void updateElectricBikePrices(List<ElectricBikePriceRequest> electricBikePriceRequestList) {
        List<ElectricBikePrice> electricBikePrices = electricBikePriceRepo.findAll();
        if(electricBikePrices.size() != electricBikePriceRequestList.size()) throw new BikePriceArraySizeException();
        for(int i=0;i<electricBikePrices.size();i++) {
            electricBikePrices.get(i).setPrice(electricBikePriceRequestList.get(i).getPrice());
            electricBikePriceRepo.save(electricBikePrices.get(i));
        }
    }

    public OrderNameProductForStatisticResponse getOrderNamesForStatistic(OrderNameProductForStatisticRequest orderNameProductRequest) {
        ClassicBike classicBike =
                classicBikeRepo.findById(orderNameProductRequest.getBikeId()).orElse(null);
        ElectricBike electricBike =
                electricBikeRepo.findById(orderNameProductRequest.getBikeId()).orElse(null);
        OrderNameProductForStatisticResponse orderNameProductResponse = new OrderNameProductForStatisticResponse();

        if (classicBike == null && electricBike == null) return null;

        if (classicBike != null) {
            orderNameProductResponse.setProductType("Klasyczny");
            orderNameProductResponse.setBikeType(classicBike.getBikeType().getType());
        }
        if (electricBike != null) {
            orderNameProductResponse.setProductType("Elektryczny");
        }


        return orderNameProductResponse;
    }
}