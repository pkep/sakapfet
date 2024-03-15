package com.kp2s.sakapfet.security.controller;

import com.kp2s.sakapfet.common.UrlMapping;
import com.kp2s.sakapfet.security.app.SecurityApplication;
import com.kp2s.sakapfet.security.dto.AuthLoginDTO;
import com.kp2s.sakapfet.security.dto.SAFAuthResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping(UrlMapping.API_BASE_PATH)
@Slf4j
public class LoginController {

    private static final String COOKIE_XSRF_TOKEN = "XSRF-TOKEN";
    private static final String XSRF_TOKEN = "token";

    private final SecurityApplication securityApplication;

    @Autowired
    public LoginController(SecurityApplication securityApplication){
        this.securityApplication = securityApplication;
    }

    @PostMapping(value = UrlMapping.LOGIN, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<SAFAuthResponse> login(ServerWebExchange serverWebExchange, WebSession session,
                                       @RequestBody @Valid AuthLoginDTO authLoginDTO){
        String xsrfToken = UUID.randomUUID().toString();
        serverWebExchange.getResponse().addCookie(ResponseCookie.from(COOKIE_XSRF_TOKEN, xsrfToken).httpOnly(false).path("/").build());
        session.getAttributes().put(XSRF_TOKEN, xsrfToken);
        return securityApplication.login(authLoginDTO.getUsername(), authLoginDTO.getPassword());
    }

}
