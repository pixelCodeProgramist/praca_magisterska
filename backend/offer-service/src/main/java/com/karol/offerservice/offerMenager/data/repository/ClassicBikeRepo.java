package com.karol.offerservice.offerMenager.data.repository;

import com.karol.offerservice.offerMenager.data.entity.ClassicBike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassicBikeRepo extends JpaRepository<ClassicBike, Long> {
    Page<ClassicBike> findAllByBikeType_Type(Pageable pageable, String type);
}
