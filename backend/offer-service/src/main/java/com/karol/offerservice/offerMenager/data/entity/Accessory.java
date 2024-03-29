package com.karol.offerservice.offerMenager.data.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Accessory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;

    @NotBlank
    private String url;

    @Column(precision=10, scale=2)
    private BigDecimal everyBeginHourPrice;

    @Column(precision=10, scale=2)
    private BigDecimal dayPrice;

    @Column(precision=10, scale=2)
    private BigDecimal dayAndNightPrice;

    @Size(min = 3, max = 3)
    private String currency;

}