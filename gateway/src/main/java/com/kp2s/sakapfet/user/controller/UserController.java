package com.kp2s.sakapfet.user.controller;

import com.kp2s.sakapfet.common.UrlMapping;
import com.kp2s.sakapfet.common.dto.SuccessDTO;
import com.kp2s.sakapfet.user.app.UserApplication;
import com.kp2s.sakapfet.user.dto.UserRegisterDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(UrlMapping.API_BASE_PATH + UrlMapping.USER_BASE_PATH)
public class UserController {

    private final UserApplication userApplication;

    @Autowired
    public UserController(UserApplication userApplication){
        this.userApplication = userApplication;
    }

    @PostMapping(path = UrlMapping.REGISTER, produces =  MediaType.APPLICATION_JSON_VALUE)
    public Mono<SuccessDTO> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO){
        return this.userApplication.register(userRegisterDTO);
    }

}
