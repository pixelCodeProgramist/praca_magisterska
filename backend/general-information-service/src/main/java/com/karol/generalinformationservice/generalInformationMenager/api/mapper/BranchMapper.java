package com.karol.generalinformationservice.generalInformationMenager.api.mapper;

import com.karol.generalinformationservice.generalInformationMenager.api.response.BranchView;
import com.karol.generalinformationservice.generalInformationMenager.data.entity.Branch;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BranchMapper {
    public BranchView mapDataToResponse(Branch branch) {
        return new BranchView().builder()
                .branchId(branch.getBranchId())
                .city(branch.getCity())
                .latitude(branch.getLatitude())
                .longitude(branch.getLongitude())
                .phone(branch.getPhone())
                .street(branch.getStreet())
                .zipCode(branch.getZipCode())
                .build();
    }
}
