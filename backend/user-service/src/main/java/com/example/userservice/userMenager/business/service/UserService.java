package com.example.userservice.userMenager.business.service;

import com.example.userservice.userMenager.data.entity.User;
import com.example.userservice.userMenager.data.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepo userRepo;

    public void verifyUser(User user) {
        user.setActive(true);
        userRepo.save(user);
    }
}
