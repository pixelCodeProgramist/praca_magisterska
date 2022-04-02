package com.karol.generalinformationservice.sectionMenager.api.mapper;


import com.karol.generalinformationservice.sectionMenager.api.response.BranchView;
import com.karol.generalinformationservice.sectionMenager.api.response.GeneralInfoView;
import com.karol.generalinformationservice.sectionMenager.data.entity.GeneralInformation;
import lombok.experimental.UtilityClass;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class GeneralInfoMapper {
    public GeneralInfoView mapDataToResponse(GeneralInformation generalInformation) {
        List<BranchView> branchViewList = generalInformation.getBranches().stream()
                .map(BranchMapper::mapDataToResponse)
                .sorted(Comparator.comparing(BranchView::getBranchId))
                .collect(Collectors.toList());

        return new GeneralInfoView().builder()
                .generalInformationId(generalInformation.getGeneralInformationId())
                .hourFrom(generalInformation.getHourFrom())
                .hourTo(generalInformation.getHourTo())
                .email(generalInformation.getEmail())
                .branches(branchViewList)
                .build();
    }
}
