package com.fundbridge.users.controller;

import com.fundbridge.users.dto.*;
import com.fundbridge.users.service.JwtService;
import com.fundbridge.users.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
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
        UUID userId = UUID.fromString(user.getId());
        String accessToken = jwtService.generateToken(userId, user.getRole());
        String refreshToken = jwtService.generateRefreshToken(userId);
        addCookie(response, "access_token", accessToken, 60 * 60);
        addCookie(response, "refresh_token", refreshToken, 60 * 60 * 24 * 7);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(
            @RequestBody LoginRequestDto request,
            HttpServletResponse response
    ) {
        UserResponseDTO user = userService.login(request);
        UUID userId = UUID.fromString(user.getId());
        String accessToken = jwtService.generateToken(userId, user.getRole());
        String refreshToken = jwtService.generateRefreshToken(userId);
        addCookie(response, "access_token", accessToken, 60 * 60);
        addCookie(response, "refresh_token", refreshToken, 60 * 60 * 24 * 7);

        return ResponseEntity.ok(user);
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

    @PostMapping("/refresh")
    public ResponseEntity<Void> refreshToken(
            @CookieValue(name = "refresh_token", required = false) String refreshToken,
            HttpServletResponse response
    ) {

        AuthResponse tokens = userService.refreshAccessToken(refreshToken);

        addCookie(response,
                "access_token",
                tokens.getAccessToken(),
                60 * 60);

        addCookie(response,
                "refresh_token",
                tokens.getRefreshToken(),
                60 * 60 * 24 * 7);

        return ResponseEntity.ok().build();
    }
    private void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // true in production
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }


    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {

        addCookie(response, "access_token", "", 0);
        addCookie(response, "refresh_token", "", 0);

        return ResponseEntity.ok().build();
    }
}
