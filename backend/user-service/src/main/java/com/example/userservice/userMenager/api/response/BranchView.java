package com.example.userservice.userMenager.api.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BranchView {
    private Long branchId;
    private String street;
    private String zipCode;
    private String city;
    private double latitude;
    private double longitude;
    private String phone;
    private String country;
}
