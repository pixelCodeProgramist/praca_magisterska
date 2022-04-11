package com.karol.generalinformationservice.generalInformationMenager.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageForSection implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long imageForSectionId;

    @NotEmpty
    @Size(min = 3, max = 150)
    @Column(length = 150)
    private String url;

    @NotEmpty
    @Size(min = 3, max = 50)
    @Column(length = 50)
    private String fileName;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "sectionId", nullable = false)
    @JsonBackReference
    private Section section;

}
