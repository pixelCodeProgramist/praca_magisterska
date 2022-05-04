package com.example.userservice.userMenager.api.mapper;

import com.example.userservice.userMenager.api.response.EmployeeUserView;
import com.example.userservice.userMenager.data.entity.User;

public class EmployeeMapper {
    public static EmployeeUserView mapUserToData(User user) {
        return new EmployeeUserView().builder()
                .userId(user.getUserId())
                .addressView(AddressMapper.mapAddressToData(user.getAddress()))
                .birthDay(user.getBirthday())
                .branchView(BranchMapper.mapBranchToData(user.getBranch()))
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .employeeDate(user.getEmployee().getEmployeeDate())
                .roleView(RoleMapper.mapRoleToData(user.getRole()))
                .build();
    }
}
