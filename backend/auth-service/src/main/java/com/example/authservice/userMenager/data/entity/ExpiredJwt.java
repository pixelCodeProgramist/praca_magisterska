package com.example.authservice.userMenager.data.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpiredJwt implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "jwtId")
    private Long jwtId;

    @NotEmpty
    private Long userId;

    @Column(nullable = false)
    private String jwt;
}

