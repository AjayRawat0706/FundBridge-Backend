package com.fundbridge.investor.controller;

import com.fundbridge.investor.dto.InvestorPortfolioRequestDto;
import com.fundbridge.investor.dto.InvestorPortfolioResponseDto;
import com.fundbridge.investor.service.InvestmentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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
