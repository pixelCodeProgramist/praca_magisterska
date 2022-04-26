package com.example.orderservice.security.business.filter;

import com.example.orderservice.security.business.filter.error.FilterError;
import com.example.orderservice.security.business.request.RequestJwt;
import com.example.orderservice.security.business.response.ExpiredJwtResponse;
import com.example.orderservice.security.business.service.JwtTokenProvider;
import com.example.orderservice.userMenager.api.request.User;
import com.example.orderservice.userMenager.feignClient.ExpiredJwtServiceFeignClient;
import com.example.orderservice.userMenager.feignClient.UserServiceFeignClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private UserServiceFeignClient userServiceFeignClient;
    private JwtTokenProvider tokenProvider;
    private ExpiredJwtServiceFeignClient expiredJwtServiceFeignClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        Long userId = null;
        String jwt = null;

        if (header != null && header.startsWith("Bearer ")) {
            jwt = header.substring(7);
            try {
                userId = tokenProvider.extractUserId(jwt);
            } catch (ExpiredJwtException ex) {
                handleJwtExpiredException(request, response);
                return;
            }
        }

        ExpiredJwtResponse token = expiredJwtServiceFeignClient.getExpiredJwtByJwt(new RequestJwt(jwt));

        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null && token == null) {
            User user = userServiceFeignClient.getUserById(userId);
            if (user != null) {
                if (tokenProvider.validateToken(jwt, user)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(user.getEmail(), null,
                                    Collections.singleton(new SimpleGrantedAuthority("ROLE_"+user.getRole().getRole())));
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource()
                            .buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }
        filterChain.doFilter(request, response);


    }

    private void handleJwtExpiredException(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        FilterError filterError = new FilterError(HttpStatus.UNAUTHORIZED);
        filterError.setMessage("JWT expired");
        filterError.setPath(request.getRequestURI());
        byte[] body = new ObjectMapper().writeValueAsBytes(filterError);
        response.getOutputStream().write(body);
    }
}
