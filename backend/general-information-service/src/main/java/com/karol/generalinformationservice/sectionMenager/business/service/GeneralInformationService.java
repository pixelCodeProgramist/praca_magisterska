package com.karol.generalinformationservice.sectionMenager.business.service;

import com.karol.generalinformationservice.sectionMenager.api.mapper.GeneralInfoMapper;
import com.karol.generalinformationservice.sectionMenager.api.response.GeneralInfoView;
import com.karol.generalinformationservice.sectionMenager.business.exception.GeneralInfoError;
import com.karol.generalinformationservice.sectionMenager.business.exception.GeneralInfoException;
import com.karol.generalinformationservice.sectionMenager.data.entity.GeneralInformation;
import com.karol.generalinformationservice.sectionMenager.data.repository.GeneralInformationRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class GeneralInformationService {
    private GeneralInformationRepo generalInformationRepo;

    public GeneralInfoView getAllGeneralInfo() {
        GeneralInformation generalInformation = generalInformationRepo.findFirstRecord()
                .orElseThrow(() -> new GeneralInfoException(GeneralInfoError.INFO_NOT_FOUND_EXCEPTION));
        return GeneralInfoMapper.mapDataToResponse(generalInformation);
    }
}
