package com.karol.offerservice.offerMenager.business.service;


import com.google.common.net.HttpHeaders;
import com.karol.offerservice.business.exception.AuthorizationException;
import com.karol.offerservice.business.request.AddBikeRequest;
import com.karol.offerservice.business.request.FrameRequest;
import com.karol.offerservice.business.request.User;
import com.karol.offerservice.business.service.JwtTokenProvider;
import com.karol.offerservice.offerMenager.api.request.*;
import com.karol.offerservice.offerMenager.api.response.AccessoryInformationInOrderView;
import com.karol.offerservice.offerMenager.api.response.ClassicBikePriceView;
import com.karol.offerservice.offerMenager.api.response.OrderNameProductForStatisticResponse;
import com.karol.offerservice.offerMenager.api.response.detailsInfoPackage.Builder.DetailBikeInfoView;
import com.karol.offerservice.offerMenager.api.response.generalInfoPackage.*;
import com.karol.offerservice.offerMenager.business.exception.offer.OfferNotFoundException;
import com.karol.offerservice.offerMenager.data.entity.ClassicBike;
import com.karol.offerservice.offerMenager.data.entity.ElectricBikePrice;
import com.karol.offerservice.offerMenager.data.entity.Product;
import com.karol.offerservice.offerMenager.data.entity.UserGradeProduct;
import com.karol.offerservice.offerMenager.data.repository.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class OfferServiceTest {
    @Autowired
    private ClassicBikeRepo classicBikeRepo;
    @Autowired
    private ElectricBikePriceRepo electricBikePriceRepo;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserGradeRepo userGradeRepo;
    @Autowired
    private UserGradeProductRepo userGradeProductRepo;
    @Autowired
    private OfferService offerService;
    @Autowired
    private EntityManager entityManager;

    @Before
    @Transactional
    public void init() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("offer-test-db.sql");
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                entityManager.createNativeQuery(line).executeUpdate();

            }
        }
    }

    @Test
    public void should_get_all_classic_product(){
        List<ClassicBikeGeneralInformationView> allGeneralClassicOfferInformation = offerService.getAllGeneralClassicOfferInformation();
        Assertions.assertEquals(9, allGeneralClassicOfferInformation.size());
    }

    @Test
    public void should_get_all_electric_product(){
        List<ElectricBikeGeneralInformationView> allGeneralElectricOfferInformation = offerService.getAllGeneralElectricOfferInformation();
        Assertions.assertEquals(4, allGeneralElectricOfferInformation.size());
    }

    @Test
    public void should_get_classic_product_general_offer(){
        BikeGeneralOfferResponseView bikeGeneralOfferResponseView = offerService.getClassicProductsGeneralOffer("Standard", 0);
        Assertions.assertEquals(9, bikeGeneralOfferResponseView.getProducts().size());
        Assertions.assertEquals(2, bikeGeneralOfferResponseView.getMaxPages());
        Assertions.assertEquals(new BigDecimal("10.00"), bikeGeneralOfferResponseView.getMinimalPrice());
        bikeGeneralOfferResponseView = offerService.getClassicProductsGeneralOffer("Standard", 1);
        Assertions.assertEquals(1, bikeGeneralOfferResponseView.getProducts().size());
        bikeGeneralOfferResponseView = offerService.getClassicProductsGeneralOffer("Standard", 2);
        Assertions.assertEquals(0, bikeGeneralOfferResponseView.getProducts().size());
        bikeGeneralOfferResponseView = offerService.getClassicProductsGeneralOffer("Random", 0);
        Assertions.assertEquals(0, bikeGeneralOfferResponseView.getProducts().size());
    }

    @Test
    public void should_get_electric_product_general_offer(){
        BikeGeneralOfferResponseView bikeGeneralOfferResponseView = offerService.getElectricProductsGeneralOffer(0);
        Assertions.assertEquals(9, bikeGeneralOfferResponseView.getProducts().size());
        Assertions.assertEquals(2, bikeGeneralOfferResponseView.getMaxPages());
        Assertions.assertEquals(new BigDecimal("120.00"), bikeGeneralOfferResponseView.getMinimalPrice());
        bikeGeneralOfferResponseView = offerService.getElectricProductsGeneralOffer(3);
        Assertions.assertEquals(0, bikeGeneralOfferResponseView.getProducts().size());
    }

    @Test
    public void should_get_accessory_products_general_offer(){
        AccessoryGeneralOfferResponseView accessoryGeneralOfferResponseView = offerService.getAccessoryProductsGeneralOffer(0);
        Assertions.assertEquals(6, accessoryGeneralOfferResponseView.getProducts().size());
        Assertions.assertEquals(1, accessoryGeneralOfferResponseView.getMaxPages());
        accessoryGeneralOfferResponseView = offerService.getAccessoryProductsGeneralOffer(1);
        Assertions.assertEquals(0, accessoryGeneralOfferResponseView.getProducts().size());
    }

    @Test
    public void should_get_bike_information(){
        DetailBikeInfoView detailBikeInfoView = offerService.getBikeInformation(8L,true);
        Assertions.assertNotNull(detailBikeInfoView);
        Assertions.assertEquals(8L, detailBikeInfoView.getId());
        Assertions.assertEquals("Standard", detailBikeInfoView.getBikeType());
        Assertions.assertEquals("Bike", detailBikeInfoView.getOfferType());
        Assertions.assertEquals("Classic", detailBikeInfoView.getBikeOfferType());
        detailBikeInfoView = offerService.getBikeInformation(22L,true);
        Assertions.assertEquals("Bike", detailBikeInfoView.getOfferType());
        Assertions.assertEquals("Electric", detailBikeInfoView.getBikeOfferType());
        detailBikeInfoView = offerService.getBikeInformation(1L,true);
        Assertions.assertNull(detailBikeInfoView);
        detailBikeInfoView = offerService.getBikeInformation(50L,true);
        Assertions.assertNotNull(detailBikeInfoView);
        Assertions.assertThrows(OfferNotFoundException.class,()->offerService.getBikeInformation(50L,false));
    }

    @Test
    public void should_get_all_bikes(){
        List<BikeForSearchView> allBikes = offerService.getAllBikes(true, true);
        Assertions.assertEquals(45, allBikes.size());
        List<BikeForSearchView> allActiveBikes = offerService.getAllBikes(false, true);
        Assertions.assertEquals(40, allActiveBikes.size());
    }

    @Test
    public void should_get_all_accessories(){
        List<AccessoryInformationInOrderView> accessories = offerService.getAllAccessories(2);
        Assertions.assertEquals(6, accessories.size());
        AccessoryInformationInOrderView accessoryInformationInOrderView = accessories.get(0);
        Assertions.assertEquals(new BigDecimal("10.00"), accessoryInformationInOrderView.getPrice());
        accessoryInformationInOrderView = offerService.getAllAccessories(5).get(0);
        Assertions.assertEquals(new BigDecimal("15.00"), accessoryInformationInOrderView.getPrice());
        accessoryInformationInOrderView = offerService.getAllAccessories(6).get(0);
        Assertions.assertEquals(new BigDecimal("20.00"), accessoryInformationInOrderView.getPrice());
    }

    @Test
    @Transactional
    public void can_grade(){
        User user = User.builder().userId(1L).build();
        String token = issueTokenForUser(user);
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/detailUser");
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        Assertions.assertTrue(offerService.canGrade(17L, request, true));
        Product productBefore = productRepo.findByProductId(17L).get();
        GradeRequest gradeRequest = new GradeRequest(17L,new BigDecimal("5.00"));
        offerService.grade(gradeRequest, request, true);
        Product productAfter = productRepo.findByProductId(17L).get();
        double expectedAvg = productAfter.getUserGradeProducts().stream()
                .mapToDouble(usp -> usp.getRating().doubleValue())
                .average().getAsDouble();
        Assertions.assertEquals(expectedAvg, productAfter.getRating().doubleValue());
        UserGradeProduct userGradeProduct = userGradeProductRepo.findByProductAndUserGrade_UserId(productAfter, 1L).get();
        Assertions.assertFalse(offerService.canGrade(17L, request, true));
        MockHttpServletRequest request2 = new MockHttpServletRequest("GET", "/detailUser");
        Assertions.assertThrows(AuthorizationException.class, () ->offerService.canGrade(17L, request2, true));
        Assertions.assertThrows(OfferNotFoundException.class, () ->offerService.canGrade(1700000L, request, true));

        userGradeProductRepo.delete(userGradeProduct);
        userGradeRepo.delete(userGradeProduct.getUserGrade());
        productAfter = productRepo.findByProductId(17L).get();
        productAfter.setRating(productBefore.getRating());
        productRepo.save(productAfter);
    }

    private String issueTokenForUser(User user) {
        return jwtTokenProvider.generateToken(user);
    }

    @Test
    public void can_get_service_info() {
        ServiceGeneralInfoView serviceGeneralInfoView = offerService.getServiceInfo(new ServiceRequest("Wycieczka z przewodnikiem"));
        Assertions.assertEquals("Wycieczka z przewodnikiem", serviceGeneralInfoView.getName());
        serviceGeneralInfoView = offerService.getServiceInfo(serviceGeneralInfoView.getId());
        Assertions.assertEquals("Wycieczka z przewodnikiem", serviceGeneralInfoView.getName());
        Assertions.assertThrows(OfferNotFoundException.class, () -> offerService.getServiceInfo(1L));
        Assertions.assertThrows(OfferNotFoundException.class, () -> offerService.getServiceInfo(new ServiceRequest("error")));
    }

    @Test
    @Transactional
    public void can_add_bike() throws IOException {
        FrameRequest frameRequest = FrameRequest.builder()
                .frameSize("S")
                .quantity(new Integer("10"))
                .build();

        AddBikeRequest addBikeRequest = AddBikeRequest.builder()
                .frames(new ArrayList<>(Collections.singletonList(frameRequest)))
                .name("Test name bike")
                .selectedProductTypeOption("rower klasyczny")
                .image(new Byte[]{})
                .selectedBikeOrAccessoryTypeOption("Standard")
                .build();

        List<ClassicBike> productsBefore = classicBikeRepo.findAll();
        offerService.addOffer(addBikeRequest, true);
        List<ClassicBike> productsAfter = classicBikeRepo.findAll();

        Assertions.assertTrue(productsAfter.size()>productsBefore.size());
        Assertions.assertEquals("Test name bike",productsAfter.get(productsAfter.size()-1).getProduct().getName());
        ClassicBike classicBike = productsAfter.get(productsAfter.size() - 1);

        classicBikeRepo.delete(classicBike);
        System.out.println();
    }

    @Test
    public void can_change_offer_activity() {
        Product productBefore = productRepo.findByProductIdAndActive(8L, true).get();
        boolean expected = productBefore.getActive();
        offerService.changeOfferActivity(new Long(productBefore.getProductId()));
        Product productAfter = productRepo.findByProductIdAndActive(8L, false).get();
        Assertions.assertNotEquals(expected,productAfter.getActive());
        offerService.changeOfferActivity(productBefore.getProductId());
    }

    @Test
    public void get_classic_bike_prices() {
        List<ClassicBikePriceView> classicBikePriceViews = offerService.getClassicBikePrices();
        Assertions.assertEquals(3, classicBikePriceViews.size());
    }

    @Test
    public void update_classic_bike_prices() {
        List<ClassicBikePriceView> classicBikePriceViews = offerService.getClassicBikePrices();
        BigDecimal expected = new BigDecimal(classicBikePriceViews.get(0).getDayPrice().toString());
        List<ClassicBikePriceRequest> classicBikePriceRequest = classicBikePriceViews.stream()
                .map(bike -> ClassicBikePriceRequest.builder()
                        .bikeType(bike.getBikeType())
                        .dayPrice(bike.getDayPrice())
                        .currency(bike.getCurrency())
                        .dayAndNightPrice(bike.getDayAndNightPrice())
                        .id(bike.getId())
                        .everyBeginHourPrice(bike.getEveryBeginHourPrice())
                        .build())
                .collect(Collectors.toList());
        classicBikePriceRequest.get(0).setDayPrice(new BigDecimal("0"));
        offerService.updateClassicBikePrices(classicBikePriceRequest);
        List<ClassicBikePriceView> classicBikePriceViewsAfter = offerService.getClassicBikePrices();
        Assertions.assertNotEquals(expected, classicBikePriceViewsAfter.get(0).getDayPrice());
        classicBikePriceRequest.get(0).setDayPrice(expected);
        offerService.updateClassicBikePrices(classicBikePriceRequest);
    }

    @Test
    public void update_electric_bike_prices() {
        List<ElectricBikePrice> electricBikePriceViews = electricBikePriceRepo.findAll();
        BigDecimal expected = new BigDecimal(electricBikePriceViews.get(0).getPrice().toString());
        List<ElectricBikePriceRequest> electricBikePriceRequestList = electricBikePriceViews.stream()
                .map(bike -> ElectricBikePriceRequest.builder()
                        .price(bike.getPrice())
                        .currency(bike.getCurrency())
                        .id(bike.getId())
                        .time(bike.getTime())
                        .build())
                .collect(Collectors.toList());
        electricBikePriceRequestList.get(0).setPrice(new BigDecimal("0"));
        offerService.updateElectricBikePrices(electricBikePriceRequestList);
        List<ElectricBikePrice> electricBikePriceViewsAfter = electricBikePriceRepo.findAll();
        Assertions.assertNotEquals(expected, electricBikePriceViewsAfter.get(0).getPrice());
        electricBikePriceRequestList.get(0).setPrice(expected);
        offerService.updateElectricBikePrices(electricBikePriceRequestList);
    }

    @Test
    public void get_order_name_product_for_statistic() {
        OrderNameProductForStatisticRequest orderNameProductRequest = OrderNameProductForStatisticRequest.builder()
                .bikeId(10L)
                .build();
        OrderNameProductForStatisticResponse orderNamesForStatistic = offerService.getOrderNamesForStatistic(orderNameProductRequest);
        Assertions.assertEquals("Klasyczny", orderNamesForStatistic.getProductType());
        Assertions.assertEquals("Standard", orderNamesForStatistic.getBikeType());
        orderNameProductRequest.setBikeId(40L);
        orderNamesForStatistic = offerService.getOrderNamesForStatistic(orderNameProductRequest);
        Assertions.assertEquals("Klasyczny", orderNamesForStatistic.getProductType());
        Assertions.assertEquals("Premium", orderNamesForStatistic.getBikeType());
        orderNameProductRequest.setBikeId(22L);
        orderNamesForStatistic = offerService.getOrderNamesForStatistic(orderNameProductRequest);
        Assertions.assertEquals("Elektryczny", orderNamesForStatistic.getProductType());
        Assertions.assertNull(orderNamesForStatistic.getBikeType());
        orderNameProductRequest.setBikeId(1L);
        orderNamesForStatistic = offerService.getOrderNamesForStatistic(orderNameProductRequest);
        Assertions.assertNull(orderNamesForStatistic);
    }
}