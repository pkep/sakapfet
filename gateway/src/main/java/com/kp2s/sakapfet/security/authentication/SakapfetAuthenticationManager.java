package com.kp2s.sakapfet.security.authentication;

import com.kp2s.sakapfet.security.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class SakapfetAuthenticationManager implements ReactiveAuthenticationManager {

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        return authenticationService.authenticate(username, password);
    }
}
