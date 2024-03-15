package com.kp2s.sakapfet.user.app;

import com.kp2s.sakapfet.common.dto.SuccessDTO;
import com.kp2s.sakapfet.user.dto.UserRegisterDTO;
import com.kp2s.sakapfet.user.service.UserService;
import org.apache.kafka.common.errors.DuplicateResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserApplication {

    private final UserService userService;

    @Autowired
    public UserApplication(UserService userService){
        this.userService = userService;
    }

    public Mono<SuccessDTO> register(UserRegisterDTO userRegisterDTO){
        if(!userRegisterDTO.getPassword().equals(userRegisterDTO.getRepeatedPassword()))
            return Mono.error(new IllegalArgumentException("The password and repeated password have to be the same."));

        return this.userService.save(userRegisterDTO).thenReturn(new SuccessDTO(true));
    }

}
