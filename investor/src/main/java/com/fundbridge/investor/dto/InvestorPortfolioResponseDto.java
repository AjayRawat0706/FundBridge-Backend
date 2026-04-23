package com.fundbridge.investor.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
public class InvestorPortfolioResponseDto {
    private UUID id;
    private UUID startupId;
    private BigDecimal investmentAmount;
    private BigDecimal equityPercentage;
    private LocalDate investmentDate;
}
