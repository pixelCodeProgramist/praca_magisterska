package com.karol.offerservice.offerMenager.data.repository;

import com.karol.offerservice.offerMenager.data.entity.ClassicBike;
import com.karol.offerservice.offerMenager.data.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClassicBikeRepo extends JpaRepository<ClassicBike, Long> {
    Page<ClassicBike> findAllByBikeType_Type(Pageable pageable, String type);
    Optional<ClassicBike> findByProduct(Product product);
}
