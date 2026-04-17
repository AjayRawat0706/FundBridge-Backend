package com.fundbridge.investor.mapper;

import com.fundbridge.investor.dto.StartupFundingRequestDto;
import com.fundbridge.investor.dto.StartupFundingResponseDto;
import com.fundbridge.investor.model.FundingStage;
import com.fundbridge.investor.model.Startup;
import com.fundbridge.investor.model.StartupFunding;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StartupFundingMapper {
    public static StartupFunding toStartupFunding(StartupFundingRequestDto request,Startup startup, FundingStage stage){
        StartupFunding funding=new StartupFunding();
        funding.setFundingStage(stage);
        funding.setStartup(startup);
        funding.setUseOfFunds(request.getUseOfFunds());
        funding.setAmountRequired(request.getAmountRequired());
        funding.setEquityOffered(request.getEquityOffered());
        return funding;
    }

    public static StartupFundingResponseDto toFundingResponse(StartupFunding funding){
        StartupFundingResponseDto response =new StartupFundingResponseDto();
        response.setStartupId(funding.getStartup().getId());
        response.setUseOfFunds(funding.getUseOfFunds());
        response.setFundingStage(funding.getFundingStage());
        response.setEquityOffered(funding.getEquityOffered());
        response.setAmountRequired(funding.getAmountRequired());
        return response;
    }
}
