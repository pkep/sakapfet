package com.kp2s.sakapfet.security.service;

import com.kp2s.sakapfet.security.util.JWTUtil;
import com.kp2s.sakapfet.user.dto.SAFAuthUser;
import com.kp2s.sakapfet.user.entity.User;
import com.kp2s.sakapfet.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Service
@Slf4j
public class SAFAuthService implements AuthenticationService{

    private final UserService userService;

    private final PasswordEncoder encoder;

    @Autowired
    public SAFAuthService(UserService userService, PasswordEncoder encoder){
        this.userService = userService;
        this.encoder = encoder;
    }

    @Override
    public Mono<Authentication> authenticate(String username, String password) {
        return userService.findByUsername(username).flatMap(user -> {
            if (user == null) {
                return Mono.error(new UsernameNotFoundException(username));
            }
            if(StringUtils.hasText(password) && encoder.matches(password, user.getPassword())){
                SAFAuthUser safAuthUser = new SAFAuthUser(user);
                return Mono.just(new UsernamePasswordAuthenticationToken(safAuthUser, password, safAuthUser.getRoles()));
            } else {
                log.error("Bad credential");
                return Mono.error(new IllegalArgumentException("Bad credential"));
            }
        });
    }

    @Override
    public Mono<Void> logout() {
        return null;
    }
    
}
