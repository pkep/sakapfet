package com.kp2s.sakapfet.user.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.checkerframework.common.value.qual.MinLen;

@Data
public class UserRegisterDTO {

    @NotBlank
    @Size(min = 4, message = "The username have to get 4 characters minimum")
    private String username;

    @Email
    private String email;

    @NotBlank
    @Size(min = 8, max = 64, message = "The password will have 8 characters minimum")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$",
    message = "The password required at least 1 digit,\n at least 1 lowercase letter,\n at least 1 uppercase letter,\n at least 1 special character and between 8 and 20 characters.")
    private String password;

    @NotBlank
    @Size(min = 8, max = 64, message = "The password have to get 8 characters minimum")
    private String repeatedPassword;


}
