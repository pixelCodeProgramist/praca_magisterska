package com.karol.offerservice.offerMenager.data.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserGradeProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userGradeProductId;

    @Column(precision=10, scale=2)
    private BigDecimal rating;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_grade_id", nullable = false)
    private UserGrade userGrade;
}
