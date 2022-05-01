package com.example.userservice.userMenager.business.service;

import com.example.userservice.business.service.JwtTokenNonUserProvider;
import com.example.userservice.business.service.JwtTokenProvider;
import com.example.userservice.userMenager.api.mapper.UserMapper;
import com.example.userservice.userMenager.api.request.DetailUserRequest;
import com.example.userservice.userMenager.api.request.ForgetAndChangerPasswordRequest;
import com.example.userservice.userMenager.api.request.UserByIdRequest;
import com.example.userservice.userMenager.api.request.UserByMailRequest;
import com.example.userservice.userMenager.api.response.DetailUserView;
import com.example.userservice.userMenager.api.response.UserView;
import com.example.userservice.userMenager.business.exception.authorize.AuthorizationException;
import com.example.userservice.userMenager.business.exception.token.TokenAlreadyUsedException;
import com.example.userservice.userMenager.business.exception.token.TokenExpiredException;
import com.example.userservice.userMenager.business.exception.user.UserNotFoundException;
import com.example.userservice.userMenager.data.entity.Address;
import com.example.userservice.userMenager.data.entity.ExpiredJwt;
import com.example.userservice.userMenager.data.entity.User;
import com.example.userservice.userMenager.data.repository.AddressRepo;
import com.example.userservice.userMenager.data.repository.ExpiredJwtRepo;
import com.example.userservice.userMenager.data.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepo userRepo;

    private AddressRepo addressRepo;

    private ExpiredJwtRepo expiredJwtRepo;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider tokenProvider;
    private JwtTokenNonUserProvider jwtTokenNonUserProvider;


    public void verifyUser(User user) {
        user.setActive(true);
        userRepo.save(user);
    }

    public UserView getUserByMail(UserByMailRequest userByMailRequest) {
        if (jwtTokenNonUserProvider.validateToken(userByMailRequest.getToken())) {
            if (!jwtTokenNonUserProvider.isTokenExpire(userByMailRequest.getToken()) &&
                    "COMPUTER".equalsIgnoreCase(jwtTokenNonUserProvider.extractUserIdName(userByMailRequest.getToken()))) {
                Optional<User> user = userRepo.findByEmail(userByMailRequest.getMail());
                if (user.isPresent()) return UserMapper.mapDataToResponse(user.get());
            }else throw new AuthorizationException();
        }else throw new AuthorizationException();
        return null;
    }

    public UserView getUserById(UserByIdRequest userByIdRequest) {
        if (jwtTokenNonUserProvider.validateToken(userByIdRequest.getToken())) {
            if (!jwtTokenNonUserProvider.isTokenExpire(userByIdRequest.getToken()) &&
                    "COMPUTER".equalsIgnoreCase(jwtTokenNonUserProvider.extractUserIdName(userByIdRequest.getToken()))) {
                Optional<User> user = userRepo.findById(userByIdRequest.getId());
                if (user.isPresent()) return UserMapper.mapDataToResponse(user.get());
            }else throw new AuthorizationException();
        }else throw new AuthorizationException();
        return null;
    }

    public void changePassword(ForgetAndChangerPasswordRequest forgetAndChangerPasswordRequest) {

        Optional<ExpiredJwt> expiredJwtOptional = expiredJwtRepo.findByJwt(forgetAndChangerPasswordRequest.getToken());
        if (expiredJwtOptional.isPresent())
            throw new TokenAlreadyUsedException(forgetAndChangerPasswordRequest.getToken());

        if (!tokenProvider.isTokenExpire(forgetAndChangerPasswordRequest.getToken())) {
            Long userId = tokenProvider.extractUserId(forgetAndChangerPasswordRequest.getToken());
            User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException(" with id: " + userId));
            user.setPassword(passwordEncoder.encode(forgetAndChangerPasswordRequest.getPassword()));
            ExpiredJwt expiredJwt = ExpiredJwt.builder()
                    .jwt(forgetAndChangerPasswordRequest.getToken())
                    .user(user)
                    .build();
            expiredJwtRepo.save(expiredJwt);
            userRepo.save(user);
        } else {
            throw new TokenExpiredException(forgetAndChangerPasswordRequest.getToken());
        }

    }

    public DetailUserView getUser(Long id, HttpServletRequest httpServletRequest) {
        User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("with id: " + id));
        String token = httpServletRequest.getHeader("Authorization");
        if (token != null) {
            token = token.substring(7);
            Long userId = tokenProvider.extractUserId(token);
            User userFromToken = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("with id: " + userId));
            if (user.equals(userFromToken)) {
                return UserMapper.mapDataToDetailedResponse(user);
            }
        }

        throw new UserNotFoundException("");
    }

    public boolean updateUser(DetailUserRequest detailUserView, HttpServletRequest httpServletRequest) {
        User user = userRepo.findByEmail(detailUserView.getEmail()).orElseThrow(() ->
                new UserNotFoundException("with email: " + detailUserView.getEmail()));
        String token = httpServletRequest.getHeader("Authorization");
        if (token != null) {
            token = token.substring(7);
            Long userId = tokenProvider.extractUserId(token);
            User userFromToken = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("with id: " + userId));
            if (user.equals(userFromToken)) {
                user.setFirstName(detailUserView.getFirstName());
                user.setLastName(detailUserView.getLastName());
                user.setBirthday(detailUserView.getBirthDay());
                user.setPhone(detailUserView.getPhone());
                user.setEmail(detailUserView.getEmail());
                if (isValidPassword(detailUserView.getPassword()))
                    user.setPassword(passwordEncoder.encode(detailUserView.getPassword()));
                Address address = user.getAddress();
                address.setCity(detailUserView.getAddressView().getCity());
                address.setHouseNr(detailUserView.getAddressView().getHouseNr());
                address.setStreet(detailUserView.getAddressView().getStreet());
                address.setZipCode(detailUserView.getAddressView().getZipCode());
                userRepo.save(user);
                return true;
            }
        }

        throw new UserNotFoundException("");


    }

    public boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$");
        return pattern.matcher(password).matches();
    }
}
