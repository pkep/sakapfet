package com.kp2s.sakapfet.user.repository;

import com.kp2s.sakapfet.user.entity.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends R2dbcRepository<User, String> {

    Mono<User> findByUsername(String username);

    Mono<Boolean> existsUsersByUsername(String username);
}
