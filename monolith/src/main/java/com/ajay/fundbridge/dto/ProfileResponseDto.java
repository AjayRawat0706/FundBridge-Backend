package com.ajay.fundbridge.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ProfileResponseDto {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String mobileNumber;
    private String profileImage;
}
