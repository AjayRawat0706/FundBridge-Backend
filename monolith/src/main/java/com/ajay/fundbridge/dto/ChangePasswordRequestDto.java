package com.ajay.fundbridge.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangePasswordRequestDto {
  @NotBlank(message = "Old Password is Required")
   private String oldPassword;
  @NotBlank(message = "New Password is Required")
  private String newPassword;
}
