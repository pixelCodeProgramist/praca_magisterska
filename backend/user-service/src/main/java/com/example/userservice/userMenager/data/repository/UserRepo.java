package com.example.userservice.userMenager.data.repository;

import com.example.userservice.userMenager.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    List<User> findAllByRole_RoleAndActive(String role, Boolean active);
}
