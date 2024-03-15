package com.kp2s.sakapfet.security.service;

import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

public interface AuthenticationService {

    public Mono<Authentication> authenticate(String username, String password);

    public Mono<Void> logout();

}
