package com.karol.offerservice.offerMenager.data.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ClassicBikePrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bike_type_id", referencedColumnName = "id")
    private BikeType bikeType;

    @NotEmpty
    @Column(precision=10, scale=2)
    private BigDecimal everyBeginHourPrice;

    @NotEmpty
    @Column(precision=10, scale=2)
    private BigDecimal dayPrice;

    @NotEmpty
    @Column(precision=10, scale=2)
    private BigDecimal dayAndNightPrice;

    @Size(min = 3, max = 3)
    private String currency;
}
