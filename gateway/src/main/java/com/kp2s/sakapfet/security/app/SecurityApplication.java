package com.kp2s.sakapfet.security.app;

import com.kp2s.sakapfet.security.dto.SAFAuthResponse;
import com.kp2s.sakapfet.security.service.SAFAuthService;
import com.kp2s.sakapfet.security.util.JWTUtil;
import com.kp2s.sakapfet.user.dto.SAFAuthUser;
import com.kp2s.sakapfet.user.type.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Service
public class SecurityApplication {

    private final SAFAuthService safAuthService;

    private final JWTUtil jwtUtil;

    @Autowired
    public SecurityApplication(SAFAuthService safAuthService, JWTUtil jwtUtil){
        this.safAuthService = safAuthService;
        this.jwtUtil = jwtUtil;
    }

    public Mono<SAFAuthResponse> login(String username, String password){
        return safAuthService.authenticate(username, password)
                .map(Authentication::getPrincipal)
                .cast(SAFAuthUser.class)
                .map(this::getSAFAuthResponseByUser);
    }

    private SAFAuthResponse getSAFAuthResponseByUser(SAFAuthUser safAuthUser){
        return new SAFAuthResponse(safAuthUser.getId(), safAuthUser.getUsername(),
                safAuthUser.getEmail(), jwtUtil.generateToken(safAuthUser),
                safAuthUser.getRoles().stream().map(role -> Role.valueOf(role.getAuthority())).collect(Collectors.toList()));
    }



}
