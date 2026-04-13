package com.ajay.fundbridge.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
public class InvestorPortfolioRequestDto {
    @NotNull(message = "Startup Id is required")
    private UUID startupId;
    @NotNull(message = "Investment Amount is required")
    private BigDecimal investmentAmount;
    @NotNull(message = "Equity Percentage is required")
    private BigDecimal equityPercentage;
    @NotNull(message = "Investment Date is required")
    private LocalDate investmentDate;
}
