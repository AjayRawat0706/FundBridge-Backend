package com.fundbridge.users.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ProfileRequestDto {
    @NotBlank(message = "First name required")
    private String firstName;
    private String lastName;
    @NotNull(message = "Date of birth required")
    private LocalDate dateOfBirth;
    @NotBlank (message = "Mobile number required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid mobile number")
    private String mobileNumber;
}
