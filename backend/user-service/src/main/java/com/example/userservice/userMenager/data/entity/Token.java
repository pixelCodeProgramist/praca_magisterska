package com.example.userservice.userMenager.data.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long tokenId;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private Boolean active;

    @OneToOne(mappedBy = "token")
    private User user;
}
