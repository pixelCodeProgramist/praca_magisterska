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
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;

    @NotBlank
    private String url;


    @OneToMany(mappedBy = "electricBike", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ElectricBikeFrameInventory> electricBikeFrameInventories = new HashSet<>();


}