package com.kp2s.sakapfet.web;

import com.kp2s.sakapfet.common.UrlMapping;
import com.kp2s.sakapfet.security.util.JWTUtil;
import com.kp2s.sakapfet.user.dto.SAFAuthUser;
import com.kp2s.sakapfet.user.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
@Order(Integer.MIN_VALUE)
@Slf4j
public class JWTokenFilter implements WebFilter {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserService userService;

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getPath().toString();
        boolean isAuthorizedPath = !path.startsWith(UrlMapping.API_BASE_PATH + UrlMapping.LOGIN)
                || !path.startsWith(UrlMapping.API_BASE_PATH + UrlMapping.LOGOUT)
                || !path.startsWith(UrlMapping.ACTUATOR);
        if (!isAuthorizedPath) {
            String authorization = exchange.getRequest().getHeaders().getFirst(AUTHORIZATION);
            if (Objects.nonNull(authorization) && authorization.startsWith(BEARER)) {
                String jwtToken = null;
                String username = null;
                jwtToken = authorization.substring(7);

                try {
                    username = jwtUtil.getUsernameFromToken(jwtToken);
                } catch (IllegalArgumentException e) {
                    log.error("Error while getting username from token!", e);
                } catch (ExpiredJwtException e) {
                    log.error("Token expired!", e);
                }

                if (Objects.nonNull(username) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
                    String finalJwtToken = jwtToken;
                    return this.userService.findByUsername(username).flatMap(user -> {
                        SAFAuthUser safAuthUser = new SAFAuthUser(user);
                        if (jwtUtil.validateToken(finalJwtToken, safAuthUser)) {
                            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                                    new UsernamePasswordAuthenticationToken(safAuthUser, user.getPassword(), safAuthUser.getRoles());

                            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                        }
                        return chain.filter(exchange);
                    });

                } else {
                    log.error("No user is provided");
                    return Mono.error(new IllegalArgumentException("No user is provided"));
                }
            } else {
                log.error("Token is required for this path");
                return Mono.error(new IllegalArgumentException("Token is required for this path"));
            }
        }
        return chain.filter(exchange);
    }
}
