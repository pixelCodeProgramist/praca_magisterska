package com.karol.generalinformationservice.generalInformationMenager.data.repository;


import com.karol.generalinformationservice.generalInformationMenager.data.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BranchRepo extends JpaRepository<Branch, Long> {
}
