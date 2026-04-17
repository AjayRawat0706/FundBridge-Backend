package com.fundbridge.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequestDto {
   @NotBlank(message = "Email is required")
   @Email(message = "Email should  be valid")
   private String email;
   @NotBlank(message = "Password is required")
    private String password;
}
