package com.fundbridge.users.controller;

import com.fundbridge.users.dto.*;
import com.fundbridge.users.exception.UnauthorizedException;
import com.fundbridge.users.service.JwtService;
import com.fundbridge.users.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;

    @Value("${app.cookie.secure}")
    private boolean secureCookie;

    @Value("${app.cookie.same-site}")
    private String sameSite;

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
        UserResponseDTO user= userService.getUser(id);
        return ResponseEntity.ok().body(user);
    }
    @PutMapping("/change-password/{id}")
    ResponseEntity<Void>updatePassword(@Valid @RequestBody ChangePasswordRequestDto changePasswordRequestDto,
                                  @PathVariable UUID id,
                                  Authentication authentication){
        UUID authenticatedUserId = getAuthenticatedUserId(authentication);
        userService.updatePassword(changePasswordRequestDto, id, authenticatedUserId);
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
        ResponseCookie cookie = ResponseCookie.from(name, value)
                .httpOnly(true)
                .secure(secureCookie)
                .sameSite(sameSite)
                .path("/")
                .maxAge(Duration.ofSeconds(maxAge))
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }


    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {

        addCookie(response, "access_token", "", 0);
        addCookie(response, "refresh_token", "", 0);

        return ResponseEntity.ok().build();
    }

    private UUID getAuthenticatedUserId(Authentication authentication) {
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new UnauthorizedException("Authentication required");
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UUID userId) {
            return userId;
        }

        return UUID.fromString(principal.toString());
    }
}
