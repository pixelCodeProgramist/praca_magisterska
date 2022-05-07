package com.karol.offerservice.offerMenager.data.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userGradeId;

    @NotBlank
    private Long userId;

    @OneToMany(mappedBy = "userGrade", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<UserGradeProduct> userGradeProducts = new HashSet<>();
}


