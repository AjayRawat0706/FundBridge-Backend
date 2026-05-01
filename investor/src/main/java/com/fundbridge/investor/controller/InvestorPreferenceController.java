package com.fundbridge.investor.controller;

import com.fundbridge.investor.dto.InvestorPreferenceRequestDto;
import com.fundbridge.investor.service.InvestorPreferenceService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/investors/preferences")
public class InvestorPreferenceController {
    private final InvestorPreferenceService investorPreferenceService;

    @PostMapping
    public ResponseEntity<String>savePreferences(@Valid @RequestBody InvestorPreferenceRequestDto request,
                                                 @RequestHeader("X-User-Id") UUID userId){
        investorPreferenceService.savePreferences(userId,request);
        return ResponseEntity.ok("Preferences saved successfully");
    }

    @GetMapping
    public ResponseEntity<InvestorPreferenceRequestDto>getPreferences(@RequestHeader("X-User-Id") UUID userId){
        return ResponseEntity.ok(investorPreferenceService.getPreferences(userId));
    }
}
