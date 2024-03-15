package com.kp2s.sakapfet.user.dto;

import com.kp2s.sakapfet.user.entity.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class SAFAuthUser {

    private String id;
    private String username;
    private String password;
    private String email;
    private List<GrantedAuthority> roles;

    public SAFAuthUser(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.roles = user.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

}
