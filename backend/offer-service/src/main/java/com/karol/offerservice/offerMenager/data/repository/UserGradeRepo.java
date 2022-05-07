package com.karol.offerservice.offerMenager.data.repository;

import com.karol.offerservice.offerMenager.data.entity.UserGrade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserGradeRepo extends JpaRepository<UserGrade, Long> {
    Optional<UserGrade> findByUserId(Long id);
}
