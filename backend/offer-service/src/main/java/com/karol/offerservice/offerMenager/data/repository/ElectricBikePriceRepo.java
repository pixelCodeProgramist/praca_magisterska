package com.karol.offerservice.offerMenager.data.repository;

import com.karol.offerservice.offerMenager.data.entity.ElectricBikePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface ElectricBikePriceRepo extends JpaRepository<ElectricBikePrice, Long> {
    @Query(value = "SELECT ebp.price from electric_bike_price ebp order by ebp.price ASC LIMIT 1", nativeQuery = true)
    BigDecimal findMinimalPrice();
}
