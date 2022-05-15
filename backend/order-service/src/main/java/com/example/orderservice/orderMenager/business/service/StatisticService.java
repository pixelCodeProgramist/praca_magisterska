package com.example.orderservice.orderMenager.business.service;

import com.example.orderservice.orderMenager.api.request.AprioriRequest;
import com.example.orderservice.orderMenager.api.request.OrderNameProductForStatisticRequest;
import com.example.orderservice.orderMenager.api.response.OrderNameProductForStatisticResponse;
import com.example.orderservice.orderMenager.business.service.aprioriPackage.AprioriAlgorithm;
import com.example.orderservice.orderMenager.business.service.aprioriPackage.AssociationRule;
import com.example.orderservice.orderMenager.data.entity.UserOrder;
import com.example.orderservice.orderMenager.data.repository.UserOrderRepo;
import com.example.orderservice.orderMenager.feignClient.OfferServiceFeignClient;
import com.example.orderservice.security.business.service.JwtTokenNonUserOrderProvider;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class StatisticService {
    private UserOrderRepo userOrderRepo;
    private JwtTokenNonUserOrderProvider jwtTokenNonUserOrderProvider;
    private OfferServiceFeignClient offerServiceFeignClient;

    public List<AssociationRule> getStatistic(AprioriRequest aprioriRequest) {
        List<UserOrder> allTransactions = userOrderRepo.findByBeginOrderBetween(aprioriRequest.getFrom(), aprioriRequest.getTo());
        List<List<String>> input = new ArrayList<>(allTransactions.stream().map(transaction -> {
            String bikeIdStr = jwtTokenNonUserOrderProvider.extractValueFromClaims(transaction.getTransactionToken(), "bikeId");
            String accessoryIdStr = jwtTokenNonUserOrderProvider.extractValueFromClaims(transaction.getTransactionToken(), "accessoryId");
            String withBikeTripStr = jwtTokenNonUserOrderProvider.extractValueFromClaims(transaction.getTransactionToken(), "withBikeTrip");

            if ("null".equalsIgnoreCase(bikeIdStr)) return new ArrayList<String>();

            Long accessoryIdLong = null;
            if (!StringUtils.isBlank(accessoryIdStr)) accessoryIdLong = Long.parseLong(accessoryIdStr);
            OrderNameProductForStatisticResponse orderNameProductForStatisticResponse = offerServiceFeignClient.getOrderNamesForStatistic(
                    new OrderNameProductForStatisticRequest(Long.parseLong(bikeIdStr))
            );
            if (orderNameProductForStatisticResponse != null) {
                withBikeTripStr = withBikeTripStr.equals("true") ? "Wycieczka z przewodnikiem" : "";
                if (!accessoryIdStr.isBlank()) accessoryIdStr = "Akcesoria";
                String productName = "klasyczny".equalsIgnoreCase(orderNameProductForStatisticResponse.getProductType()) ?
                        orderNameProductForStatisticResponse.getProductType() + " " + orderNameProductForStatisticResponse.getBikeType() :
                        "elektryczny".equalsIgnoreCase(orderNameProductForStatisticResponse.getProductType()) ?
                                orderNameProductForStatisticResponse.getProductType() : "";
                List<String> result = new ArrayList<>();
                if (!StringUtils.isBlank(productName)) result.add(productName);
                if (!StringUtils.isBlank(accessoryIdStr)) result.add(accessoryIdStr);
                if (!StringUtils.isBlank(withBikeTripStr)) result.add(withBikeTripStr);
                return result;
            }

            return new ArrayList<String>();
        }).toList());
        input.removeIf(inputOne->inputOne.size()==0);
        AprioriAlgorithm aprioriAlgorithm = new AprioriAlgorithm(input, aprioriRequest.getSupport(),aprioriRequest.getConfidence());
        return aprioriAlgorithm.solve();

    }
}
