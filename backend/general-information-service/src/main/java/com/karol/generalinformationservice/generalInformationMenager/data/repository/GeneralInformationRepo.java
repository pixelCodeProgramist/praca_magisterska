package com.karol.generalinformationservice.generalInformationMenager.data.repository;


import com.karol.generalinformationservice.generalInformationMenager.data.entity.GeneralInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface GeneralInformationRepo extends JpaRepository<GeneralInformation, Long> {
    @Query(value = "SELECT * FROM general_information ORDER BY id DESC LIMIT 1",nativeQuery = true)
    Optional<GeneralInformation> findFirstRecord();
}
