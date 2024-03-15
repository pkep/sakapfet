package com.kp2s.sakapfet.security.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.common.value.qual.MinLen;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthLoginDTO {
    @NotBlank
    private String username;

    @NotBlank
    @MinLen(8)
    private String password;
}
