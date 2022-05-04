package com.example.userservice.userMenager.data.repository;

import com.example.userservice.userMenager.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUserIdAndRole_Role(Long id, String role);

    List<User> findAllByRole_RoleAndActive(String role, Boolean active);

    List<User> findAllByRole_Role(String role);

}
