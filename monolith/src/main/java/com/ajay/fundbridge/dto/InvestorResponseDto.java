package com.ajay.fundbridge.dto;

import com.ajay.fundbridge.model.InvestorType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class InvestorResponseDto {
    private UUID id;
    private InvestorType investorType;
    private String organizationName;
    private String companyWebsite;
    private String location;
    private String bio;
    private Integer portfolioSize;
}
