package com.example.userservice.userMenager.data.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable, UserDetails {
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

    @NotBlank(message = "Data nie może być pusta")
    @Past(message = "Data urodzenia nie może być z przyszłości")
    private Date birthday;

    @NotBlank
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$",
            message = "Hasło musi zawierać 8 znaków w tym: dużą liter, małą literę oraz cyfre")
    private String password;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "roleId", nullable = false)
    private Role role;

    @OneToOne(mappedBy = "user")
    private Token token;

    @OneToOne(mappedBy = "user")
    private Branch branch;

    @OneToOne(mappedBy = "user")
    private Employee employee;

    @Column(nullable = false)
    private boolean active;

    @OneToOne(mappedBy = "user")
    private Address address;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ExpiredJwt> expiredJwts = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return Collections.singleton(new SimpleGrantedAuthority(role.getRole()));
    }

    @Override
    public String getPassword()
    {
        return password;
    }

    @Override
    public String getUsername()
    {
        return email;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        boolean equalsRole = role != null && user.role != null && Objects.equals(role.getRoleId(), user.role.getRoleId());
        boolean equalsAddress = address != null && user.address != null && Objects.equals(address.getAddressId(), user.address.getAddressId());
        return active == user.active && Objects.equals(userId, user.userId) && Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) && Objects.equals(email, user.email) && Objects.equals(phone, user.phone) && 
                Objects.equals(birthday, user.birthday) && Objects.equals(password, user.password) && equalsRole &&
                Objects.equals(token, user.token) && Objects.equals(branch, user.branch) && Objects.equals(employee, user.employee) &&
                equalsAddress;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, firstName, lastName, email, phone, birthday, password, role, token, branch, employee, active, address, expiredJwts);
    }
}
