package com.example.userservice.userMenager.api.controller;

import com.example.userservice.business.service.JwtTokenNonUserProvider;
import com.example.userservice.business.service.JwtTokenProvider;
import com.example.userservice.userMenager.api.dto.RoleEnum;
import com.example.userservice.userMenager.api.request.*;
import com.example.userservice.userMenager.api.response.DetailUserView;
import com.example.userservice.userMenager.api.response.UserView;
import com.example.userservice.userMenager.business.exception.token.TokenAlreadyUsedException;
import com.example.userservice.userMenager.business.exception.user.UserNotFoundException;
import com.example.userservice.userMenager.business.service.UserService;
import com.example.userservice.userMenager.data.entity.Address;
import com.example.userservice.userMenager.data.entity.Role;
import com.example.userservice.userMenager.data.entity.User;
import com.example.userservice.userMenager.data.repository.AddressRepo;
import com.example.userservice.userMenager.data.repository.ExpiredJwtRepo;
import com.example.userservice.userMenager.data.repository.RoleRepo;
import com.example.userservice.userMenager.data.repository.UserRepo;
import com.google.common.net.HttpHeaders;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.atomic.AtomicReference;

@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserControllerTest {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private JwtTokenNonUserProvider jwtTokenNonUserProvider;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ExpiredJwtRepo expiredJwtRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private AddressRepo addressRepo;


    private User user;

    @Before
    public void init() {
        Role role = Role.builder()
                .role(RoleEnum.CLIENT.name())
                .build();

        Address address = Address.builder()
                .zipCode("11111")
                .street("testStreet")
                .city("testCity")
                .houseNr("1")
                .build();

        this.user = User.builder()
                .active(false)
                .email("firstUserTestMail@o2.pl")
                .role(role)
                .address(address)
                .password("")
                .build();

        this.roleRepo.save(role);
        address.setUser(this.user);
        this.addressRepo.save(address);
        this.user = userRepo.save(user);
    }

    @After
    public void finish() {

        addressRepo.deleteAll();
        userRepo.deleteAll();
        roleRepo.deleteAll();
        expiredJwtRepo.deleteAll();
    }

    @Test
    public void should_get_user_details() {
        String token = issueTokenForUser(user);
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/detailUser");
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        AtomicReference<DetailUserView> detailUserView = new AtomicReference<>();
        Assertions.assertDoesNotThrow(() -> detailUserView.set(userService.getUser(user.getUserId(), request)));
        Assertions.assertNotNull(detailUserView.get());
        Assertions.assertEquals(user.getUserId().longValue(), detailUserView.get().getUserId().longValue());
        MockHttpServletRequest requestAuthNull = new MockHttpServletRequest("GET", "/detailUser");
        Assertions.assertThrows(UserNotFoundException.class,
                () -> detailUserView.set(userService.getUser(user.getUserId(), requestAuthNull)));
        Assertions.assertThrows(UserNotFoundException.class,
                () -> detailUserView.set(userService.getUser(-999L, request)));
    }

    @Test
    public void should_activate_user() {
        Assertions.assertFalse(this.user.isActive());
        userService.verifyUser(this.user);
        Assertions.assertTrue(this.user.isActive());
    }

    @Test
    public void is_correct_password() {
        Assertions.assertFalse(userService.isValidPassword("asdfghjk"));
        Assertions.assertFalse(userService.isValidPassword("A12s"));
        Assertions.assertTrue(userService.isValidPassword("1qwerty2Wsx"));
    }

    @Test
    public void can_get_user_by_mail() {
        UserByMailRequest userByMailRequest = new UserByMailRequest("firstUserTestMail@o2.pl", jwtTokenNonUserProvider.generateToken());
        AtomicReference<UserView> userViewFromAtomic = new AtomicReference<>();
        Assertions.assertDoesNotThrow(() -> userViewFromAtomic.set(userService.getUserByMail(userByMailRequest)));
        Assertions.assertNotNull(userViewFromAtomic.get());
    }

    @Test
    public void can_get_user_by_id() {
        UserByIdRequest userByIdRequest = new UserByIdRequest(this.user.getUserId(), jwtTokenNonUserProvider.generateToken());
        AtomicReference<UserView> userViewFromAtomic = new AtomicReference<>();
        Assertions.assertDoesNotThrow(() -> userViewFromAtomic.set(userService.getUserById(userByIdRequest)));
        Assertions.assertNotNull(userViewFromAtomic.get());
    }

    @Test
    public void can_change_password() {
        ForgetAndChangerPasswordRequest forgetAndChangerPasswordRequest = new ForgetAndChangerPasswordRequest("1qazXSW2",
                issueTokenForUser(this.user));
        String password = String.valueOf(this.user.getPassword());
        userService.changePassword(forgetAndChangerPasswordRequest);
        user = userRepo.findById(user.getUserId()).get();
        Assertions.assertNotEquals(password, user.getPassword());
        Assertions.assertThrows(TokenAlreadyUsedException.class, () -> userService.changePassword(forgetAndChangerPasswordRequest));
    }

    @Test
    public void can_update_user() {
        String token = issueTokenForUser(user);
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/detailUser");
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        DetailUserRequest detailUserRequest = DetailUserRequest.builder()
                .firstName("changedFirstName")
                .email("firstUserTestMail@o2.pl")
                .password(user.getPassword())
                .addressView(new AddressRequest(user.getAddress().getStreet(),user.getAddress().getHouseNr(),
                        user.getAddress().getZipCode(), user.getAddress().getCity()))
                .build();
        String firstName = String.valueOf(this.user.getFirstName());
        Assertions.assertTrue(userService.updateUser(detailUserRequest, request));
        user = userRepo.findById(user.getUserId()).get();
        Assertions.assertNotEquals(firstName, user.getFirstName());
        MockHttpServletRequest request2 = new MockHttpServletRequest("GET", "/detailUser");
        Assertions.assertThrows(UserNotFoundException.class, ()->userService.updateUser(detailUserRequest, request2));
    }


    private String issueTokenForUser(User user) {
        return jwtTokenProvider.generateToken(user);
    }

}