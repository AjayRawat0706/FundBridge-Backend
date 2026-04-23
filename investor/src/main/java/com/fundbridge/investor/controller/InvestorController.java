package com.fundbridge.investor.controller;

import com.fundbridge.investor.dto.InvestorRequestDto;
import com.fundbridge.investor.dto.InvestorResponseDto;
import com.fundbridge.investor.service.InvestorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/investor")
public class InvestorController {

 private final InvestorService investorService;
 @PostMapping
 public ResponseEntity<InvestorResponseDto> addInvestor(@Valid @RequestBody InvestorRequestDto request,
                                                        @RequestHeader("USER-ID") UUID userId){
     InvestorResponseDto response=investorService.addInvestor(request,userId);
     return ResponseEntity.status(HttpStatus.CREATED).body(response);
 }
 @GetMapping
 public ResponseEntity<List<InvestorResponseDto>>getAllInvestors(){
     List<InvestorResponseDto>investors=investorService.getAllInvestors();
     return ResponseEntity.ok(investors);
 }
 @GetMapping("/{id}")
 public ResponseEntity<InvestorResponseDto>getInvestor(@PathVariable UUID id){
     InvestorResponseDto response=investorService.getInvestor(id);
     return ResponseEntity.ok(response);
 }

@PutMapping("/{id}")
public ResponseEntity<InvestorResponseDto>updateInvestor(@PathVariable UUID id,
                                                         @Valid @RequestBody InvestorRequestDto request,
                                                         @RequestHeader("USER-ID") UUID userId){
    InvestorResponseDto response=investorService.updateInvestor(id,userId,request);
    return ResponseEntity.ok(response);
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteInvestor(@PathVariable UUID id, @RequestHeader("USER-ID") UUID userId) {
     investorService.deleteInvestor(id,userId);
     return ResponseEntity.noContent().build();
}
}
