package com.karol.offerservice.offerMenager.data.repository;

import com.karol.offerservice.offerMenager.data.entity.ElectricBike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectricBikeRepo extends JpaRepository<ElectricBike, Long> {
    Page<ElectricBike> findAll(Pageable pageable);
}
