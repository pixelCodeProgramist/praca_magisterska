package com.karol.offerservice.offerMenager.data.repository;

import com.karol.offerservice.offerMenager.data.entity.ClassicBikeFrameInventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClassicBikeFrameInventoryRepo extends JpaRepository<ClassicBikeFrameInventory, Long> {
    Optional<ClassicBikeFrameInventory> findByClassicBike_IdAndFrame_Name(Long bikeId, String frame);
}
