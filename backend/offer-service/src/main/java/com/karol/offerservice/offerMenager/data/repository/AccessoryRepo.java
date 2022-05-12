package com.karol.offerservice.offerMenager.data.repository;

import com.karol.offerservice.offerMenager.data.entity.Accessory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccessoryRepo extends JpaRepository<Accessory, Long> {
    Page<Accessory> findAllByProduct_Active(Pageable pageable, Boolean active);
    List<Accessory> findAllByProduct_Active(Boolean active);

    Optional<Accessory> findByIdAndProduct_Active(Long id, Boolean active);


}
