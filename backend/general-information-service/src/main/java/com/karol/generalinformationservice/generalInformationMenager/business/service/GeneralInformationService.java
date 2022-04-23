package com.karol.generalinformationservice.generalInformationMenager.business.service;

import com.karol.generalinformationservice.generalInformationMenager.api.mapper.GeneralInfoMapper;
import com.karol.generalinformationservice.generalInformationMenager.api.response.GeneralInfoView;
import com.karol.generalinformationservice.generalInformationMenager.business.exception.generalInfo.GeneralInformationNotFoundException;
import com.karol.generalinformationservice.generalInformationMenager.data.entity.GeneralInformation;
import com.karol.generalinformationservice.generalInformationMenager.data.repository.GeneralInformationRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class GeneralInformationService {
    private GeneralInformationRepo generalInformationRepo;

    public GeneralInfoView getAllGeneralInfo() {
        GeneralInformation generalInformation = generalInformationRepo.findFirstRecord()
                .orElseThrow(GeneralInformationNotFoundException::new);
        return GeneralInfoMapper.mapDataToResponse(generalInformation);
    }
}
