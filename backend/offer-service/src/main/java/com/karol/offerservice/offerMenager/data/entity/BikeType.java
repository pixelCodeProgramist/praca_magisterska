package com.karol.offerservice.offerMenager.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BikeType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int bikeTypeId;

    @OneToOne(mappedBy = "bikeType", cascade = CascadeType.ALL)
    private ClassicBikePrice classicBikePrice;

    @NotEmpty
    private String type;

    @OneToMany(mappedBy = "bikeType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ClassicBike> classicBikes = new HashSet<>();

}
