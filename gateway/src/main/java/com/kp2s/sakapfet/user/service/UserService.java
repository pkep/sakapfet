package com.kp2s.sakapfet.user.service;

import com.kp2s.sakapfet.user.dto.UserRegisterDTO;
import com.kp2s.sakapfet.user.entity.User;
import com.kp2s.sakapfet.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder encoder){
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    /**
     *  Get user by username
     * @param username
     * @return {@link User}
     */
    public Mono<User> findByUsername(String username){
        return this.userRepository.findByUsername(username);
    }


    /**
     *  Register user
     * @param userRegisterDTO
     * @return {@link User}
     */
    @Transactional
    public Mono<User> save(UserRegisterDTO userRegisterDTO){
        return this.userRepository.existsUsersByUsername(userRegisterDTO.getUsername()).flatMap(userExist -> {
           if(userExist)
               return Mono.error(new UsernameNotFoundException(String.format("Username : %s already exist", userRegisterDTO.getUsername())));

           User user = new User();
           user.setUsername(userRegisterDTO.getUsername());
           user.setEmail(userRegisterDTO.getEmail());
           user.setPassword(encoder.encode(userRegisterDTO.getPassword()));
           user.setRoles(List.of("USER"));

           return this.userRepository.save(user);
        });
    }


}
