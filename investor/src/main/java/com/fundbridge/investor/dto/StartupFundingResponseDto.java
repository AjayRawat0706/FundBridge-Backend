package com.fundbridge.investor.dto;

import com.fundbridge.investor.model.FundingStage;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
public class StartupFundingResponseDto {
    private UUID startupId;
    private FundingStage fundingStage;
    private BigDecimal amountRequired;
    private BigDecimal equityOffered;
    private String useOfFunds;
}
