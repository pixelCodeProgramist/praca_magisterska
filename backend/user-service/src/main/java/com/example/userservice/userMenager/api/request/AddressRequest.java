package com.example.userservice.userMenager.api.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest {
    @NotBlank(message = "Ulica nie może być pusta")
    @Size(min = 3, max = 100, message = "Ulica musi mieć przynajmniej trzy znaki i maksymalnie sto znaków")
    private String street;

    private String houseNr;

    @NotBlank(message = "Kod pocztowy nie może być pusty")
    @Pattern(regexp = "^\\d{2}[- ]{0,1}\\d{3}$",
            message = "Kod pocztowy musi mieć strukturę xx-xxx lub xxxxx i składać się z cyfr")
    private String zipCode;

    @NotBlank(message = "Ulica nie może być pusta")
    @Size(min = 5, max = 100, message = "Ulica musi mieć przynajmniej pięć znaków i maksymalnie sto znaków")
    private String city;

}
