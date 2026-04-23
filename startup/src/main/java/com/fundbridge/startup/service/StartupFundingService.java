package com.fundbridge.startup.service;

import com.fundbridge.startup.dto.StartupFundingRequestDto;
import com.fundbridge.startup.dto.StartupFundingResponseDto;
import com.fundbridge.startup.exception.ResourceNotFoundException;
import com.fundbridge.startup.exception.UnauthorizedException;
import com.fundbridge.startup.mapper.StartupFundingMapper;
import com.fundbridge.startup.model.FundingStage;
import com.fundbridge.startup.model.Startup;
import com.fundbridge.startup.model.StartupFunding;
import com.fundbridge.startup.repository.FundingStageRepository;
import com.fundbridge.startup.repository.StartupFundingRepository;
import com.fundbridge.startup.repository.StartupRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class StartupFundingService {
    private final StartupRepository startupRepository;
    private final FundingStageRepository fundingStageRepository;
    private final StartupFundingRepository startupFundingRepository;

    public StartupFundingResponseDto createFunding(StartupFundingRequestDto request, UUID userId){
        Startup startup=startupRepository.findById(request.getStartupId())
                .orElseThrow(()->new ResourceNotFoundException("Startup not found"));
        if (startupFundingRepository.existsByStartupId(startup.getId())) {
            throw new IllegalStateException("Funding already exists for this startup");
        }
        if(!startup.getOwnerId().equals(userId)){
            throw new UnauthorizedException("User not authorized");
        }
        FundingStage fundingStage=fundingStageRepository.findById(request.getFundingStageId())
                .orElseThrow(()->new ResourceNotFoundException("Funding stage not found"));

        StartupFunding funding= StartupFundingMapper.toStartupFunding(request,startup,fundingStage);
        StartupFunding savedFunding= startupFundingRepository.save(funding);
        return StartupFundingMapper.toFundingResponse(savedFunding);
    }

    public StartupFundingResponseDto getFunding(UUID startupId){

        StartupFunding funding = startupFundingRepository.findByStartupId(startupId)
                .orElseThrow(() -> new ResourceNotFoundException("Funding not found"));

        return StartupFundingMapper.toFundingResponse(funding);
    }

    public StartupFundingResponseDto updateFunding(UUID startupId,
                                                   StartupFundingRequestDto request,
                                                   UUID userId){
        StartupFunding funding = startupFundingRepository.findByStartupId(startupId)
                .orElseThrow(() -> new ResourceNotFoundException("Funding not found"));
        Startup startup = funding.getStartup();
        if(!startup.getOwnerId().equals(userId)){
            throw new UnauthorizedException("User not authorized");
        }
        FundingStage fundingStage = fundingStageRepository.findById(request.getFundingStageId())
                .orElseThrow(() -> new ResourceNotFoundException("Funding stage not found"));
        funding.setFundingStage(fundingStage);
        funding.setAmountRequired(request.getAmountRequired());
        funding.setEquityOffered(request.getEquityOffered());
        funding.setUseOfFunds(request.getUseOfFunds());
        funding.setFundingStage(fundingStage);
        StartupFunding updatedFunding = startupFundingRepository.save(funding);

        return StartupFundingMapper.toFundingResponse(updatedFunding);
    }
}
