package com.ajay.fundbridge.controller;

import com.ajay.fundbridge.dto.ProfileRequestDto;
import com.ajay.fundbridge.dto.ProfileResponseDto;
import com.ajay.fundbridge.service.ProfileService;
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
    ResponseEntity<ProfileResponseDto>addProfile(@Valid @RequestBody ProfileRequestDto profileRequest , @RequestHeader ("USER-ID") UUID userId){
      ProfileResponseDto response=profileService.newProfile(profileRequest,userId);
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
   }
   @GetMapping("/{userId}")
    ResponseEntity<ProfileResponseDto>getProfile(@PathVariable UUID userId){
       ProfileResponseDto profile=profileService.getProfile(userId);
       return ResponseEntity.status(HttpStatus.OK).body(profile);
   }

    @PostMapping("/upload-photo")
    public ResponseEntity<String> uploadProfilePhoto(
            @RequestHeader ("USER-ID") UUID userId,
            @RequestParam("file") MultipartFile file
    ) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is required");
        }

        profileService.uploadProfileImage(userId, file);
        return ResponseEntity.ok("Profile image uploaded successfully");
    }

    @PutMapping()
    ResponseEntity<ProfileResponseDto>updateProfile(@RequestHeader("USER-ID")UUID userId,
                                                    @RequestBody ProfileRequestDto profileRequestDto){
       ProfileResponseDto profile=profileService.updateProfile(userId,profileRequestDto);
       return ResponseEntity.status(HttpStatus.CREATED).body(profile);
    }
    @DeleteMapping("/profile-photo")
    public ResponseEntity<String>removeProfilePhoto(@RequestHeader("USER-ID")UUID userId){
       profileService.removeProfilePhoto(userId);
       return ResponseEntity.ok("Profile image removed successfully");
    }
}
