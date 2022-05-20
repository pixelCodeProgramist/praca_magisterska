package com.example.userservice.userMenager.business.service;

import com.example.userservice.userMenager.api.request.RegisterRequest;
import com.example.userservice.userMenager.business.exception.user.UserMailExistsException;
import com.example.userservice.userMenager.data.entity.Role;
import com.example.userservice.userMenager.data.entity.User;
import com.example.userservice.userMenager.data.repository.AddressRepo;
import com.example.userservice.userMenager.data.repository.RoleRepo;
import com.example.userservice.userMenager.data.repository.TokenRepo;
import com.example.userservice.userMenager.data.repository.UserRepo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
@RunWith(SpringRunner.class)
public class RegisterServiceTest {
    @Autowired
    private RegisterService registerService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private TokenRepo tokenRepo;

    @Before
    public void start() {
        Role role = Role.builder()
                .role("CLIENT")
                .build();
        roleRepo.save(role);
    }


    @Test
    public void should_register_user() {
        List<User> userList = userRepo.findAll();

        RegisterRequest registerRequest = RegisterRequest.builder()
                .city("CityTest")
                .birthday(new Date(1990,2,3))
                .houseNr("1")
                .email("testMail@o2.pl")
                .firstName("First")
                .lastName("Last")
                .password("1qazXSW2")
                .street("TestStreet")
                .zipCode("11222")
                .phone(BigInteger.valueOf(129333222L))
                .build();

        registerService.register(registerRequest, true);
        List<User> userListAfter = userRepo.findAll();
        Assertions.assertTrue(userListAfter.size()>userList.size());
        User dbUser = userListAfter.get(userListAfter.size()-1);
        Assertions.assertEquals(registerRequest.getEmail(), dbUser.getEmail());
        Assertions.assertThrows(UserMailExistsException.class,()->  registerService.register(registerRequest, true));
    }

    @After
    public void finish() {
        addressRepo.deleteAll();
        tokenRepo.deleteAll();
        userRepo.deleteAll();
        roleRepo.deleteAll();
    }
}