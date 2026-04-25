package com.fundbridge.startup.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class InvestorPreferenceRequestDto {
    private BigDecimal investmentMin;
    private BigDecimal investmentMax;
    private List<Integer> industryIds;
    private List<Integer> stageIds;
}
