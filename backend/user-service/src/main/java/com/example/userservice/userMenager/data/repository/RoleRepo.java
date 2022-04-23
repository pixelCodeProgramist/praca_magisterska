package com.example.userservice.userMenager.data.repository;

import com.example.userservice.userMenager.data.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleIgnoreCase(String role);
}
