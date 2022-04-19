package com.karol.offerservice.offerMenager.data.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ElectricBike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @NotBlank
    private String url;

    @Size(max = 5)
    @Column(precision=10, scale=2)
    private BigDecimal rating;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "ElectricBikeFrame",
            joinColumns = {@JoinColumn(name = "electricBikeId")},
            inverseJoinColumns = {@JoinColumn(name = "frameId")})
    private Set<Frame> frames = new HashSet<>();

}