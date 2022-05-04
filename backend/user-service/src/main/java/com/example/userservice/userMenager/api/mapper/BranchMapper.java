package com.example.userservice.userMenager.api.mapper;

import com.example.userservice.userMenager.api.response.BranchView;
import com.example.userservice.userMenager.api.response.EmployeeUserView;
import com.example.userservice.userMenager.data.entity.Branch;
import com.example.userservice.userMenager.data.entity.User;

public class BranchMapper {
    public static BranchView mapBranchToData(Branch branch) {
        return  BranchView.builder()
                .branchId(branch.getBranchId())
                .city(branch.getCity())
                .country(branch.getCountry())
                .street(branch.getStreet())
                .zipCode(branch.getZipCode())
                .latitude(branch.getLatitude())
                .longitude(branch.getLongitude())
                .phone(branch.getPhone())
                .build();
    }
}
