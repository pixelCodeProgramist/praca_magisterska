package com.karol.offerservice.offerMenager.data.repository;

import com.karol.offerservice.offerMenager.data.entity.BikeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface BikeTypeRepo extends JpaRepository<BikeType, Long> {
    @Query(value = "SELECT cbp.every_begin_hour_price from bike_type bt, classic_bike_price cbp " +
            "where bt.id = cbp.bike_type_id order by cbp.every_begin_hour_price ASC LIMIT 1", nativeQuery = true)
    BigDecimal findLowestPriceBySection(@Param("section") String section);

    Optional<BikeType> findByType(String type);
}
