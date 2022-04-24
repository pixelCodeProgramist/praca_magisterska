package com.example.userservice.userMenager.api.mapper;

import com.example.userservice.userMenager.api.response.RoleView;
import com.example.userservice.userMenager.api.response.TokenView;
import com.example.userservice.userMenager.data.entity.Role;
import com.example.userservice.userMenager.data.entity.Token;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RoleMapper {


    public static RoleView mapRoleToData(Role role) {
        return new RoleView().builder()
                .roleId(role.getRoleId())
                .role(role.getRole())
                .build();
    }
}
