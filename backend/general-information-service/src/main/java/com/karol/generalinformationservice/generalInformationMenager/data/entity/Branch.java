package com.karol.generalinformationservice.generalInformationMenager.data.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long branchId;

    @NotEmpty
    private String street;

    @NotEmpty
    private String zipCode;

    @NotEmpty
    private String city;

    @NotEmpty
    private String country;

    @NotEmpty
    private double latitude;

    @NotEmpty
    private double longitude;

    @Size(min = 9, max = 12)
    @NotEmpty
    @Digits(integer = 9, fraction = 0)
    private String phone;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "generalInformationId", nullable = false)
    private GeneralInformation generalInformation;

}