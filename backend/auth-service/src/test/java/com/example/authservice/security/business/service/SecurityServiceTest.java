package com.example.authservice.security.business.service;

import com.example.authservice.security.api.request.AuthenticationRequest;
import com.example.authservice.security.api.request.ForgetPasswordRequest;
import com.example.authservice.security.api.request.RequestJwt;
import com.example.authservice.security.api.response.TokenForUserNonLoginResponse;
import com.example.authservice.security.business.exception.AuthorizationException;
import com.example.authservice.userMenager.api.request.User;
import com.example.authservice.userMenager.data.entity.ExpiredJwt;
import com.example.authservice.userMenager.data.repository.ExpiredJwtRepo;
import com.google.common.net.HttpHeaders;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.List;

@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
@RunWith(SpringRunner.class)
public class SecurityServiceTest {

    @Autowired
    private SecurityService securityService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    JwtTokenNonUserProvider jwtTokenNonUserProvider;

    @Autowired
    ExpiredJwtRepo expiredJwtRepo;


    @Test
    public void should_logout() {
        User user = new User();
        user.setUserId(1L);
        String token = issueTokenForUser(user);
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/detailUser");
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        List<ExpiredJwt> expiredJwtsBefore = expiredJwtRepo.findAll();
        securityService.logOut(request, true);
        List<ExpiredJwt> expiredJwtsAfter = expiredJwtRepo.findAll();
        Assertions.assertTrue(expiredJwtsAfter.size()>expiredJwtsBefore.size());
        ExpiredJwt expiredJwt = expiredJwtsAfter.get(expiredJwtsAfter.size()-1);
        ExpiredJwt expiredJwtResponse = securityService.getExpiredJwt(new RequestJwt(expiredJwt.getJwt()));
        Assertions.assertNotNull(expiredJwtResponse);
    }

    @Test
    public void should_get_token_from_forget_password() {
        ForgetPasswordRequest forgetPasswordRequest = new ForgetPasswordRequest("mail1@o2.pl", jwtTokenNonUserProvider.generateToken());
        TokenForUserNonLoginResponse tokenForUserNonLoginResponse = securityService.generateTokenNonLoginForUser(forgetPasswordRequest, true);
        Assertions.assertNotNull(tokenForUserNonLoginResponse);
        Assertions.assertNotNull(tokenForUserNonLoginResponse.getToken());
        Assertions.assertTrue(tokenForUserNonLoginResponse.getToken().length()>0);

        ForgetPasswordRequest forgetPasswordRequest2 = new ForgetPasswordRequest("mail1@o2.pl", issueTokenForUser(
                User.builder().userId(1L).build()
        ));

        Assertions.assertThrows(AuthorizationException.class, () -> securityService.generateTokenNonLoginForUser(forgetPasswordRequest2, true));
    }

    @After
    public void finish() {
        expiredJwtRepo.deleteAll();
    }

    private String issueTokenForUser(User user) {
        return jwtTokenProvider.generateToken(user);
    }
}