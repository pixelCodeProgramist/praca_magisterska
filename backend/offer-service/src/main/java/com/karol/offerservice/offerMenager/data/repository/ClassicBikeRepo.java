package com.karol.offerservice.offerMenager.data.repository;

import com.karol.offerservice.offerMenager.data.entity.ClassicBike;
import com.karol.offerservice.offerMenager.data.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClassicBikeRepo extends JpaRepository<ClassicBike, Long> {
    Page<ClassicBike> findAllByBikeType_TypeAndProduct_Active(Pageable pageable, String type, Boolean active);
    Optional<ClassicBike> findByProductAndProduct_Active(Product product, Boolean active);

    List<ClassicBike> findByProduct_Active(Boolean active);
    Optional<ClassicBike> findByIdAndProduct_Active(Long id, Boolean active);

    Optional<ClassicBike> findByProduct(Product product);

}
