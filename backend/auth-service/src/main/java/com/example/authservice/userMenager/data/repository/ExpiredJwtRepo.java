package com.example.authservice.userMenager.data.repository;

import com.example.authservice.userMenager.data.entity.ExpiredJwt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExpiredJwtRepo extends JpaRepository<ExpiredJwt, Long> {
    Optional<ExpiredJwt> findByJwt(String jwt);
}
