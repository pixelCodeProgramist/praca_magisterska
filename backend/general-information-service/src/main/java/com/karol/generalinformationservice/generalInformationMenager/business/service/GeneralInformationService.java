package com.karol.generalinformationservice.generalInformationMenager.business.service;

import com.karol.generalinformationservice.generalInformationMenager.api.mapper.BranchMapper;
import com.karol.generalinformationservice.generalInformationMenager.api.mapper.GeneralInfoMapper;
import com.karol.generalinformationservice.generalInformationMenager.api.response.BranchView;
import com.karol.generalinformationservice.generalInformationMenager.api.response.GeneralInfoView;
import com.karol.generalinformationservice.generalInformationMenager.business.exception.generalInfo.GeneralInformationNotFoundException;
import com.karol.generalinformationservice.generalInformationMenager.data.entity.Branch;
import com.karol.generalinformationservice.generalInformationMenager.data.entity.GeneralInformation;
import com.karol.generalinformationservice.generalInformationMenager.data.repository.BranchRepo;
import com.karol.generalinformationservice.generalInformationMenager.data.repository.GeneralInformationRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class GeneralInformationService {
    private GeneralInformationRepo generalInformationRepo;

    private BranchRepo branchRepo;

    public GeneralInfoView getAllGeneralInfo() {
        GeneralInformation generalInformation = generalInformationRepo.findFirstRecord()
                .orElseThrow(GeneralInformationNotFoundException::new);
        return GeneralInfoMapper.mapDataToResponse(generalInformation);
    }

    public List<BranchView> getBranches() {
        List<Branch> branches = branchRepo.findAll();
        return branches.stream().map(BranchMapper::mapDataToResponse).collect(Collectors.toList());
    }

    public BranchView getBranchAfterId(Long id) {
        Branch branch = branchRepo.findById(id).orElse(null);
        if(branch!=null) return BranchMapper.mapDataToResponse(branch);
        return null;
    }
}
