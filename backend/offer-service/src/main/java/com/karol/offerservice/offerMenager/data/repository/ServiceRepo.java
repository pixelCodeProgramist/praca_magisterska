package com.karol.offerservice.offerMenager.data.repository;

import com.karol.offerservice.offerMenager.data.entity.Product;
import com.karol.offerservice.offerMenager.data.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceRepo extends JpaRepository<Service, Long> {
    Optional<Service> findByProduct_Name(String name);
}
