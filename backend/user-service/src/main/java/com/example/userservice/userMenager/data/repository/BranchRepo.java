package com.example.userservice.userMenager.data.repository;

import com.example.userservice.userMenager.data.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepo extends JpaRepository<Branch, Long> {
}
