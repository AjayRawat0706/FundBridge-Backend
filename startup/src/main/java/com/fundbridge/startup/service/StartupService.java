package com.fundbridge.startup.service;

import com.fundbridge.startup.dto.InvestorPreferenceRequestDto;
import com.fundbridge.startup.dto.StartupRequestDto;
import com.fundbridge.startup.dto.StartupResponseDto;
import com.fundbridge.startup.dto.UserResponseDTO;
import com.fundbridge.startup.exception.ResourceNotFoundException;
import com.fundbridge.startup.exception.UnauthorizedException;
import com.fundbridge.startup.feign.UserClient;
import com.fundbridge.startup.mapper.StartupMapper;
import com.fundbridge.startup.model.Industry;
import com.fundbridge.startup.model.Startup;
import com.fundbridge.startup.repository.IndustryRepository;
import com.fundbridge.startup.repository.StartupRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class StartupService {
    private final StartupRepository startupRepository;
    private final IndustryRepository industryRepository;
    private final UserClient userClient;
    public StartupResponseDto addStartup(StartupRequestDto request , UUID userId){
        UserResponseDTO user;
        try {
            user = userClient.getUserById(userId);
        } catch (Exception e) {
            throw new ResourceNotFoundException("User not registered");
        }
        Industry industry=industryRepository.findById(request.getIndustryId())
                .orElseThrow(()->new ResourceNotFoundException("Industry not found"));
        Startup startup= StartupMapper.toStartup(request);
        startup.setIndustry(industry.getName());
        startup.setOwnerId(userId);
        Startup newStartup =startupRepository.save(startup);
        return StartupMapper.toStartupResponse(newStartup);

    }
    public List<StartupResponseDto>getAllStartups(){
        return startupRepository.findAll().stream()
                .map(StartupMapper::toStartupResponse)
                .toList();
    }
    public StartupResponseDto getStartup(UUID id){
        Startup startup=startupRepository.findById(id).
                orElseThrow(()->new ResourceNotFoundException("Startup not found"));
        return StartupMapper.toStartupResponse(startup);
    }
    public StartupResponseDto updateStartup(UUID id, StartupRequestDto request, UUID userId) {
        Startup startup = startupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Startup not found"));
        if (!startup.getOwnerId().equals(userId)) {
            throw new UnauthorizedException("You are not the owner of this startup");
        }
        Industry industry=industryRepository.findById(request.getIndustryId())
                        .orElseThrow(()->new ResourceNotFoundException("Industry not found"));
        startup.setName(request.getName());
        startup.setTagline(request.getTagline());
        startup.setDescription(request.getDescription());
        startup.setIndustryId(request.getIndustryId());
        startup.setIndustry(industry.getName());
        startup.setFoundedYear(request.getFoundedYear());
        startup.setWebsiteUrl(request.getWebsiteUrl());
        startup.setStatus(request.getStatus());
        Startup updatedStartup = startupRepository.save(startup);
        return StartupMapper.toStartupResponse(updatedStartup);
    }

    public void deleteStartup(UUID id, UUID userId) {
        Startup startup = startupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Startup not found"));
        if (!startup.getOwnerId().equals(userId)) {
            throw new UnauthorizedException("You are not the owner");
        }
        startupRepository.delete(startup);
    }
    public List<StartupResponseDto> getStartupsByOwner(UUID userId) {

        return startupRepository.findByOwnerId(userId)
                .stream()
                .map(StartupMapper::toStartupResponse)
                .toList();
    }


    public List<StartupResponseDto> filterStartupsByPreference(InvestorPreferenceRequestDto request) {

        List<Integer> industries = (request.getIndustryIds() == null || request.getIndustryIds().isEmpty())
                ? null
                : request.getIndustryIds();

        List<Integer> stages = (request.getStageIds() == null || request.getStageIds().isEmpty())
                ? null
                : request.getStageIds();

        List<Startup> startups = startupRepository.filterStartups(
                industries,
                stages,
                request.getInvestmentMin(),
                request.getInvestmentMax()
        );
        return startups.stream()
                .map(StartupMapper::toStartupResponse)
                .toList();
    }
}
