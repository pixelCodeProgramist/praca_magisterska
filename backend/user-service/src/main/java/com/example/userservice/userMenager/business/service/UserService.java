package com.example.userservice.userMenager.business.service;

import com.example.userservice.security.data.repository.ExpiredJwtRepo;
import com.example.userservice.userMenager.api.mapper.UserMapper;
import com.example.userservice.userMenager.api.response.UserView;
import com.example.userservice.userMenager.data.entity.User;
import com.example.userservice.userMenager.data.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepo userRepo;
    private ExpiredJwtRepo expiredJwtRepo;

    public void verifyUser(User user) {
        user.setActive(true);
        userRepo.save(user);
    }

    public UserView getUserByMail(String mail) {
        Optional<User> user = userRepo.findByEmail(mail);
        if(user.isPresent()) return UserMapper.mapDataToResponse(user.get());
        return null;
    }

    public UserView getUserById(Long id) {
        Optional<User> user = userRepo.findById(id);
        if(user.isPresent()) return UserMapper.mapDataToResponse(user.get());
        return null;
    }
}
