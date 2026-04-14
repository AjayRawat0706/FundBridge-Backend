package com.ajay.fundbridge.mapper;

import com.ajay.fundbridge.dto.StartupFundingRequestDto;
import com.ajay.fundbridge.dto.StartupFundingResponseDto;
import com.ajay.fundbridge.model.FundingStage;
import com.ajay.fundbridge.model.Startup;
import com.ajay.fundbridge.model.StartupFunding;
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
