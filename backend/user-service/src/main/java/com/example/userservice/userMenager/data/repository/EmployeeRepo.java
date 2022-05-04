package com.example.userservice.userMenager.data.repository;

import com.example.userservice.userMenager.data.entity.Employee;
import com.example.userservice.userMenager.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    Optional<Employee> findByUser(User user);
}
