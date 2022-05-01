package com.example.authservice.security.business.configuration;

import com.example.authservice.security.api.request.UserByMailRequest;
import com.example.authservice.security.business.service.JwtTokenNonUserProvider;
import com.example.authservice.userMenager.api.request.User;
import com.example.authservice.userMenager.feignClient.UserServiceFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserServiceFeignClient userServiceFeignClient;
    private JwtTokenNonUserProvider tokenNonUserProvider;


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException
    {
        User userByMail = userServiceFeignClient.getUserByMail(new UserByMailRequest(login, tokenNonUserProvider.generateToken()));

        if(userByMail == null) throw new UsernameNotFoundException("User with given login does not exist");
        return userByMail;
    }
}
