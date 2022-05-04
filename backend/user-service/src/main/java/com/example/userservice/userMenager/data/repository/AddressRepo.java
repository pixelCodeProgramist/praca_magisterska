package com.example.userservice.userMenager.data.repository;

import com.example.userservice.userMenager.data.entity.Address;
import com.example.userservice.userMenager.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepo extends JpaRepository<Address, Long> {
    Optional<Address> findByUser(User user);
}
