package com.example.userservice.userMenager.api.response;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.math.BigInteger;
import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetailUserView {

    private String firstName;

    private String lastName;

    private String email;

    private BigInteger phone;

    private Date birthDay;

    private AddressView addressView;

}
