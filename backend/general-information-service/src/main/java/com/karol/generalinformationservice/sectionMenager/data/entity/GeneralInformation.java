package com.karol.generalinformationservice.sectionMenager.data.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeneralInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long generalInformationId;

    @Email
    @NotBlank
    private String email;

    @OneToMany(mappedBy = "generalInformation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Branch> branches = new HashSet<>();

    @NotBlank
    private String hourFrom;

    @NotBlank
    private String hourTo;

}
