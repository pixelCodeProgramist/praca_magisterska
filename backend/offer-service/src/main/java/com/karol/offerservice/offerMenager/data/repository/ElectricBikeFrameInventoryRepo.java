package com.karol.offerservice.offerMenager.data.repository;

import com.karol.offerservice.offerMenager.data.entity.ElectricBikeFrameInventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ElectricBikeFrameInventoryRepo extends JpaRepository<ElectricBikeFrameInventory, Long> {
    Optional<ElectricBikeFrameInventory> findByElectricBike_IdAndFrame_Name(Long bikeId, String frame);
}
