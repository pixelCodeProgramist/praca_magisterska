package com.karol.offerservice.offerMenager.data.repository;

import com.karol.offerservice.offerMenager.data.entity.ClassicBikePrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface ClassicBikePriceRepo extends JpaRepository<ClassicBikePrice, Long> {
}
