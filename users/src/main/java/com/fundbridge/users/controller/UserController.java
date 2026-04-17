package com.fundbridge.users.controller;

import com.fundbridge.users.dto.ChangePasswordRequestDto;
import com.fundbridge.users.dto.LoginRequestDto;
import com.fundbridge.users.dto.UserRequestDTO;
import com.fundbridge.users.dto.UserResponseDTO;
import com.fundbridge.users.service.UserService;
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
