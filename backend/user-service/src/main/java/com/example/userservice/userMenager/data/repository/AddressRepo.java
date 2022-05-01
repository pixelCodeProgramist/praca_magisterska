package com.example.userservice.userMenager.data.repository;

import com.example.userservice.userMenager.data.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepo extends JpaRepository<Address, Long> {
}
