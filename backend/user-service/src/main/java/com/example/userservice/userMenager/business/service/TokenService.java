package com.example.userservice.userMenager.business.service;

import com.example.userservice.userMenager.api.mapper.TokenMapper;
import com.example.userservice.userMenager.business.exception.token.TokenAlreadyUsedException;
import com.example.userservice.userMenager.business.exception.token.TokenNotFoundException;
import com.example.userservice.userMenager.data.entity.Token;
import com.example.userservice.userMenager.data.entity.User;
import com.example.userservice.userMenager.data.repository.TokenRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TokenService {
    private TokenRepo tokenRepo;

    @Transactional
    public Token generateToken() {
        String token = UUID.randomUUID().toString();
        return tokenRepo.save(TokenMapper.mapStringToData(token));
    }

    @Transactional
    public User verifyToken(String token) {
        Token tokenObject = tokenRepo.findByToken(token).orElseThrow(()->new TokenNotFoundException(token));

        if (!tokenObject.getActive()) throw new TokenAlreadyUsedException(token);
        tokenObject.setActive(false);
        tokenRepo.save(tokenObject);
        return tokenObject.getUser();
    }

    void deleteToken(Token token) {
        tokenRepo.delete(token);
    }
}
