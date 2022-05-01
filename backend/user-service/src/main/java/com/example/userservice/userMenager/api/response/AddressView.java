package com.example.userservice.userMenager.api.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressView {
    private String street;

    private String houseNr;

    private String zipCode;

    private String city;

}
