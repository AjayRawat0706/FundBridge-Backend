package com.ajay.fundbridge.controller;

import com.ajay.fundbridge.dto.InvestorPortfolioRequestDto;
import com.ajay.fundbridge.dto.InvestorPortfolioResponseDto;
import com.ajay.fundbridge.service.InvestmentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/investment")
public class InvestmentController {
private final InvestmentService investmentService;
@PostMapping
public ResponseEntity<InvestorPortfolioResponseDto>createInvestment(@Valid @RequestBody InvestorPortfolioRequestDto request,
                                                                    @RequestHeader("USER-ID") UUID userId){
    InvestorPortfolioResponseDto response=investmentService.addInvestment(userId,request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
}

@GetMapping("/portfolio")
public ResponseEntity<List<InvestorPortfolioResponseDto>>getInvestments( @RequestHeader("USER-ID") UUID userId){
    List<InvestorPortfolioResponseDto>investments=investmentService.getPortfolio(userId);
    return ResponseEntity.ok(investments);
}

@GetMapping("/investors/{startupId}")
public ResponseEntity<List<InvestorPortfolioResponseDto>>getInvestors(@PathVariable UUID startupId){
    List<InvestorPortfolioResponseDto>investors=investmentService.getInvestors(startupId);
    return ResponseEntity.ok(investors);
}
}
