package com.fundbridge.users.controller;

import com.fundbridge.users.dto.ProfileRequestDto;
import com.fundbridge.users.dto.ProfileResponseDto;
import com.fundbridge.users.service.ProfileService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    private final ProfileService profileService;

    @PostMapping()
    public ResponseEntity<ProfileResponseDto> addProfile(
            @Valid @RequestBody ProfileRequestDto profileRequest,
            @RequestHeader("X-User-Id") UUID userId
    ) {
        ProfileResponseDto response = profileService.newProfile(profileRequest, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
   @GetMapping("/{userId}")
    ResponseEntity<ProfileResponseDto>getProfile(@PathVariable UUID userId){
       ProfileResponseDto profile=profileService.getProfile(userId);
       return ResponseEntity.status(HttpStatus.OK).body(profile);
   }

    @PostMapping("/upload-photo")
    public ResponseEntity<String> uploadProfilePhoto(@RequestParam("file") MultipartFile file,
                                                     @RequestHeader("X-User-Id") UUID userId) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is required");
        }

        profileService.uploadProfileImage(userId, file);
        return ResponseEntity.ok("Profile image uploaded successfully");
    }

    @PutMapping()
    ResponseEntity<ProfileResponseDto>updateProfile(@RequestBody ProfileRequestDto profileRequestDto,
                                                    @RequestHeader("X-User-Id") UUID userId){
        ProfileResponseDto profile=profileService.updateProfile(userId,profileRequestDto);
       return ResponseEntity.ok(profile);
    }
    @DeleteMapping("/profile-photo")
    public ResponseEntity<String>removeProfilePhoto(@RequestHeader("X-User-Id") UUID userId){
        profileService.removeProfilePhoto(userId);
       return ResponseEntity.ok("Profile image removed successfully");
    }
}
