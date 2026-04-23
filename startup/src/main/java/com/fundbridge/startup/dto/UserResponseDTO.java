package com.fundbridge.startup.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponseDTO {
    private  String id;
    private String email;
    private String role;
    private boolean active;
    private boolean verified;
}
