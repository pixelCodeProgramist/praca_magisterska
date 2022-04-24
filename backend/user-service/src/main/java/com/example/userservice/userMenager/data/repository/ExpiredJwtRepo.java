package com.example.userservice.security.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.userservice.security.data.entity.ExpiredJwt;

import java.util.Optional;

public interface ExpiredJwtRepo extends JpaRepository<ExpiredJwt, Long>
{
    Optional<ExpiredJwt> findByJwt(String jwt);
}