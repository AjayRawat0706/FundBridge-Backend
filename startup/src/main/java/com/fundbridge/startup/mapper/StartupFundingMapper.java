package com.fundbridge.startup.mapper;

import com.fundbridge.startup.dto.StartupFundingRequestDto;
import com.fundbridge.startup.dto.StartupFundingResponseDto;
import com.fundbridge.startup.model.FundingStage;
import com.fundbridge.startup.model.Startup;
import com.fundbridge.startup.model.StartupFunding;
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
