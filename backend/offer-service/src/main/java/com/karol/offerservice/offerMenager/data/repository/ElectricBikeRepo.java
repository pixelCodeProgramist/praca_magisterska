package com.karol.offerservice.offerMenager.data.repository;

import com.karol.offerservice.offerMenager.data.entity.ElectricBike;
import com.karol.offerservice.offerMenager.data.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ElectricBikeRepo extends JpaRepository<ElectricBike, Long> {
    Page<ElectricBike> findAll(Pageable pageable);

    Optional<ElectricBike> findByProduct(Product product);
}
