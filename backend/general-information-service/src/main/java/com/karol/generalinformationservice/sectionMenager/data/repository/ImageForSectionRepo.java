package com.karol.generalinformationservice.sectionMenager.data.repository;

import com.karol.generalinformationservice.sectionMenager.data.entity.ImageForSection;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface ImageForSectionRepo extends JpaRepository<ImageForSection, Long> {
    List<ImageForSection> findAllBySectionName(String sectionName);



}
