package com.example.userservice.userMenager.data.repository;

import com.example.userservice.userMenager.data.entity.ExpiredJwt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExpiredJwtRepo extends JpaRepository<ExpiredJwt, Long> {
    Optional<ExpiredJwt> findByJwt(String jwt);
}
