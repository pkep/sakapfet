package com.kp2s.sakapfet.security.dto;

import com.kp2s.sakapfet.user.type.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SAFAuthResponse {

    private String id;
    private String username;
    private String email;
    private String token;
    private List<Role> roles;

}
