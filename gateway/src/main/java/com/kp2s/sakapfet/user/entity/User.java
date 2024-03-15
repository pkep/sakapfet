package com.kp2s.sakapfet.user.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Data
@Table(name = "user", schema = "sakapfet")
public class User {

    @Id
    @UuidGenerator
    private String id;

    private String username;

    private String email;

    private String password;

    private List<String> roles;
}
