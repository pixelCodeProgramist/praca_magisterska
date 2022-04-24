package com.example.userservice.userMenager.data.entity;

import com.example.userservice.security.data.entity.ExpiredJwt;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long userId;

    @NotBlank
    @Size(min = 5, max = 50)
    private String firstName;

    @NotBlank
    @Size(min = 5, max = 50)
    private String lastName;

    @NotBlank
    @Email
    @Size(min = 5, max = 50)
    private String email;

    @NotBlank
    @Digits(integer = 9, fraction = 0)
    private BigInteger phone;

    @NotBlank
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$",
            message = "Hasło musi zawierać 8 znaków w tym: dużą liter, małą literę oraz cyfre")
    private String password;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "roleId", nullable = false)
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "token_id", referencedColumnName = "id")
    private Token token;

    @Column(nullable = false)
    private boolean active;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    Set<ExpiredJwt> expiredJwts = new HashSet<>();


}
