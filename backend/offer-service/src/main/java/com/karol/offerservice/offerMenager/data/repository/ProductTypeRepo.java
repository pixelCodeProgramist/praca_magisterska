package com.karol.offerservice.offerMenager.data.repository;

import com.karol.offerservice.offerMenager.data.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductTypeRepo extends JpaRepository<ProductType, Long> {
    Optional<ProductType> findByTypeIgnoreCase(String type);
}
