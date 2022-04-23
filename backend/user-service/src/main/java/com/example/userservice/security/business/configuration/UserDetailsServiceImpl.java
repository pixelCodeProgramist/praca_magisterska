package com.example.userservice.security.business.configuration;

import com.example.userservice.userMenager.data.repository.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepo userRepo;

    public UserDetailsServiceImpl(UserRepo userRepo)
    {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException
    {
        return userRepo.findByEmail(login)
                .orElseThrow( () -> new UsernameNotFoundException("User with given login does not exist"));
    }
}
