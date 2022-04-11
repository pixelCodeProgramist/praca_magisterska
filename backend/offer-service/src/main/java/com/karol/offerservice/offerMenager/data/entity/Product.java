package com.karol.offerservice.offerMenager.data.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "product_type_id", nullable = false)
    private ProductType productType;

    @NotBlank
    private String name;

    @OneToOne(mappedBy = "product")
    private Accessory accessory;

    @OneToOne(mappedBy = "product")
    private ClassicBike classicBike;

    @OneToOne(mappedBy = "product")
    private ElectricBike electricBike;

    @OneToOne(mappedBy = "product")
    private Service Service;
}


