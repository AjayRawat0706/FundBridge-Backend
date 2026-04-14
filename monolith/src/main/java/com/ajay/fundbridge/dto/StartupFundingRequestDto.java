package com.ajay.fundbridge.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
public class StartupFundingRequestDto {
    @NotNull(message = "Startup id Required")
    private UUID startupId;
    @NotNull(message = "Funding Stage Required")
    private Integer fundingStageId;
    @NotNull(message = "Amount is Required")
    private BigDecimal amountRequired;
    @NotNull(message = "Equity percent Required")
    private BigDecimal equityOffered;
    private String useOfFunds;
}
