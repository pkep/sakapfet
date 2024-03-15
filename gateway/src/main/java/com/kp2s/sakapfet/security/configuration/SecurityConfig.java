package com.kp2s.sakapfet.security.configuration;

import com.kp2s.sakapfet.security.authentication.SakapfetAuthenticationManager;
import com.kp2s.sakapfet.security.service.AuthenticationService;
import com.kp2s.sakapfet.web.JWTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.HttpStatusReturningServerLogoutSuccessHandler;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.security.web.server.csrf.WebSessionServerCsrfTokenRepository;
import reactor.core.publisher.Mono;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public ServerSecurityContextRepository webSessionServerSecurityContextRepository(){
        return new WebSessionServerSecurityContextRepository();
    };

    @Bean
    public ReactiveAuthenticationManager authenticationManager(){
        return new SakapfetAuthenticationManager();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
            http
                    .authorizeExchange((exchanges) -> exchanges
                        .pathMatchers(getAuthPathList()).permitAll()
                        .anyExchange().authenticated()
                    )
                    .authenticationManager(authenticationManager())
                    .formLogin(formLoginSpec -> formLoginSpec.disable())
                    .httpBasic(httpBasicSpec -> httpBasicSpec.securityContextRepository(new WebSessionServerSecurityContextRepository()))
                    .exceptionHandling(withDefaults())
                    .httpBasic(httpBasicSpec -> httpBasicSpec.authenticationEntryPoint((swe, e) -> Mono.fromRunnable(() -> {
                        swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    }))
                    .authenticationFailureHandler((swe, e) -> Mono.fromRunnable(() -> {
                        swe.getExchange().getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                    })))
                    .csrf((csrf) -> csrf.disable())
                    .logout(logoutSpec -> logoutSpec.logoutSuccessHandler(new HttpStatusReturningServerLogoutSuccessHandler()));

            return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user =  User.withDefaultPasswordEncoder()
                .username("kp2s")
                .password("kp2s")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    private static String[] getAuthPathList(){
        return new String[]{
                "/api-gateway/login",
                "/api-gateway/user/register",
                "/*", "/index.html",
                "/assets/**" ,
                "/swagger-resources/**",
                "/actuator/**",
                "/swagger-ui/index.html",
                "/webjars/**",
                "/v3/api-docs",
                "/v3/api-docs/*"
        };
    }

}
