package com.karol.offerservice.offerMenager.data.repository;

import com.karol.offerservice.offerMenager.data.entity.ClassicBike;
import com.karol.offerservice.offerMenager.data.entity.ElectricBike;
import com.karol.offerservice.offerMenager.data.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ElectricBikeRepo extends JpaRepository<ElectricBike, Long> {
    Page<ElectricBike> findAllByProduct_Active(Pageable pageable,Boolean active);

    Optional<ElectricBike> findByProductAndProduct_Active(Product product, Boolean active);

    List<ElectricBike> findByProduct_Active(Boolean active);

    Optional<ElectricBike> findByIdAndProduct_Active(Long id, Boolean active);

    Optional<ElectricBike> findByProduct(Product product);
}
