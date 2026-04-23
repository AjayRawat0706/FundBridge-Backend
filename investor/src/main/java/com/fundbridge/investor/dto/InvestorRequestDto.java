package com.fundbridge.investor.dto;


import com.fundbridge.investor.model.InvestorType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InvestorRequestDto {
    @NotNull(message = "Investor type is required")
    private InvestorType investorType;
    private String organizationName;
    private String companyWebsite;
    private String location;
    @Size(max = 1000)
    private String bio;

}
