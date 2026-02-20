package com.ajay.fundbridge.controller;

import com.ajay.fundbridge.dto.ChangePasswordRequestDto;
import com.ajay.fundbridge.dto.LoginRequestDto;
import com.ajay.fundbridge.dto.UserRequestDTO;
import com.ajay.fundbridge.dto.UserResponseDTO;
import com.ajay.fundbridge.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    @PostMapping("/register")
    ResponseEntity<UserResponseDTO>addUser(@Valid @RequestBody UserRequestDTO userRequest){
       UserResponseDTO user=userService.createUser(userRequest);
       return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
    @PostMapping("/login")
    ResponseEntity<UserResponseDTO>logIn(@Valid @RequestBody LoginRequestDto loginRequest){
        UserResponseDTO user =userService.logInUser(loginRequest);
        return ResponseEntity.ok().body(user);
    }
    @PutMapping("/change-password/{id}")
    ResponseEntity<Void>updatePassword(@Valid @RequestBody ChangePasswordRequestDto changePasswordRequestDto,
                                  @PathVariable UUID id){
        userService.updatePassword(changePasswordRequestDto,id);
        return ResponseEntity.noContent().build();
    }
}
