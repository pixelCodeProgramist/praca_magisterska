package com.karol.offerservice.offerMenager.data.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long productId;

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

    @Size(max = 5)
    @Column(precision=10, scale=2)
    private BigDecimal rating;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<UserGradeProduct> userGradeProducts = new HashSet<>();

    @NotNull
    private Boolean active;
}


