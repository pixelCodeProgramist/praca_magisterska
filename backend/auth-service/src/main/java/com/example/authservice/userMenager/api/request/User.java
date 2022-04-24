package com.example.authservice.userMenager.api.request;

import com.example.authservice.security.data.entity.ExpiredJwt;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails, Serializable {
    private Long userId;

    private String firstName;

    private String lastName;

    private String email;

    private BigInteger phone;

    private String password;

    private Role role;

    private Token token;

    private boolean active;

    Set<ExpiredJwt> expiredJwts = new HashSet<>();

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
}
