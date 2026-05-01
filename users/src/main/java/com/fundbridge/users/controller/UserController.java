package com.fundbridge.users.controller;

import com.fundbridge.users.dto.ChangePasswordRequestDto;
import com.fundbridge.users.dto.LoginRequestDto;
import com.fundbridge.users.dto.UserRequestDTO;
import com.fundbridge.users.dto.UserResponseDTO;
import com.fundbridge.users.service.JwtService;
import com.fundbridge.users.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(
            @Valid @RequestBody UserRequestDTO userRequest,
            HttpServletResponse response) {
        UserResponseDTO user = userService.createUser(userRequest);
        String token = jwtService.generateToken(UUID.fromString(user.getId()));
        Cookie cookie = new Cookie("access_token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60);
        response.addCookie(cookie);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @RequestBody LoginRequestDto request,
            HttpServletResponse response
    ) {
        String token = userService.loginAndGenerateToken(request);

        Cookie cookie = new Cookie("access_token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60);

        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }
    @GetMapping("/{id}")
    ResponseEntity<UserResponseDTO>getUser(@PathVariable UUID id){
        System.out.println("this is controller id"+id);
        UserResponseDTO user= userService.getUser(id);
        return ResponseEntity.ok().body(user);
    }
    @PutMapping("/change-password/{id}")
    ResponseEntity<Void>updatePassword(@Valid @RequestBody ChangePasswordRequestDto changePasswordRequestDto,
                                  @PathVariable UUID id){
        userService.updatePassword(changePasswordRequestDto,id);
        return ResponseEntity.noContent().build();
    }
}
