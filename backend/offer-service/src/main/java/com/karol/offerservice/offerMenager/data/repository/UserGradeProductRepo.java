package com.karol.offerservice.offerMenager.data.repository;

import com.karol.offerservice.offerMenager.data.entity.Product;
import com.karol.offerservice.offerMenager.data.entity.UserGradeProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserGradeProductRepo extends JpaRepository<UserGradeProduct, Long> {
    Optional<UserGradeProduct> findByProductAndUserGrade_UserId(Product product, Long userId);
}
