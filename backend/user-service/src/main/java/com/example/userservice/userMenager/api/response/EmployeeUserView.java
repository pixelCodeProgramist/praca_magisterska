package com.example.userservice.userMenager.api.response;

import lombok.*;

import java.math.BigInteger;
import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeUserView {
    private Long userId;

    private String firstName;

    private String lastName;

    private String email;

    private BigInteger phone;

    private Date birthDay;

    private AddressView addressView;

    private BranchView branchView;

    private Date employeeDate;

    private RoleView roleView;
}
