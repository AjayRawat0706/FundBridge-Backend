package com.fundbridge.investor.controller;

import com.fundbridge.investor.dto.StartupFundingRequestDto;
import com.fundbridge.investor.dto.StartupFundingResponseDto;
import com.fundbridge.investor.dto.StartupRequestDto;
import com.fundbridge.investor.dto.StartupResponseDto;
import com.fundbridge.investor.service.StartupFundingService;
import com.fundbridge.investor.service.StartupService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@RestController
@RequestMapping("/api/startups")
public class StartupController {
    private final StartupService startupService;
    private final StartupFundingService startupFundingService;
    @PostMapping
    public ResponseEntity<StartupResponseDto> addStartup(
            @Valid @RequestBody StartupRequestDto request,
            @RequestHeader("USER-ID") UUID userId) {
        StartupResponseDto response = startupService.addStartup(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping
    public ResponseEntity<List<StartupResponseDto>>getAllStartups(){
        List<StartupResponseDto>startups=startupService.getAllStartups();
        return ResponseEntity.ok(startups);
    }
    @GetMapping("/{id}")
    public ResponseEntity<StartupResponseDto>getStartup(@PathVariable UUID id){
        StartupResponseDto response=startupService.getStartup(id);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/{id}")
    public ResponseEntity<StartupResponseDto> updateStartup(
            @PathVariable UUID id,
            @Valid @RequestBody StartupRequestDto request,
            @RequestHeader("USER-ID") UUID userId) {

        StartupResponseDto response = startupService.updateStartup(id, request, userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStartup(
            @PathVariable UUID id,
            @RequestHeader("USER-ID") UUID userId) {
        startupService.deleteStartup(id, userId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/my")
    public ResponseEntity<List<StartupResponseDto>> getMyStartups(
            @RequestHeader("USER-ID") UUID userId) {
        return ResponseEntity.ok(startupService.getStartupsByOwner(userId));
    }
    @PostMapping("/funding")
    public ResponseEntity<StartupFundingResponseDto>createFunding(
            @Valid @RequestBody StartupFundingRequestDto request,
            @RequestHeader("USER-ID")UUID userId){
        StartupFundingResponseDto response=startupFundingService.createFunding(request,userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/funding/{startupId}")
    public ResponseEntity<StartupFundingResponseDto>getFunding( @PathVariable UUID startupId){
        StartupFundingResponseDto response=startupFundingService.getFunding(startupId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/funding/{startupId}")
    public ResponseEntity<StartupFundingResponseDto>updateFunding(
            @Valid @RequestBody StartupFundingRequestDto request,
            @PathVariable UUID startupId,
            @RequestHeader("USER-ID")UUID userId){
        StartupFundingResponseDto response=startupFundingService.updateFunding(startupId,request,userId);
        return ResponseEntity.ok(response);
    }
}