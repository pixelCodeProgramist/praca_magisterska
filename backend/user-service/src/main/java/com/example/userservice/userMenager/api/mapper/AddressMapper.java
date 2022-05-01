package com.example.userservice.userMenager.api.mapper;

import com.example.userservice.userMenager.api.response.AddressView;
import com.example.userservice.userMenager.data.entity.Address;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AddressMapper {

    public static AddressView mapTokenToData(Address address) {
        return new AddressView().builder()
                .city(address.getCity())
                .houseNr(address.getHouseNr())
                .street(address.getStreet())
                .zipCode(address.getZipCode())
                .build();
    }
}
