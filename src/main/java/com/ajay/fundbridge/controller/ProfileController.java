package com.ajay.fundbridge.controller;

import com.ajay.fundbridge.dto.ProfileRequestDto;
import com.ajay.fundbridge.dto.ProfileResponseDto;
import com.ajay.fundbridge.service.ProfileService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
