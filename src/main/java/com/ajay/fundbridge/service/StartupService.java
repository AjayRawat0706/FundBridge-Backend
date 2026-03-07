package com.ajay.fundbridge.service;

import com.ajay.fundbridge.dto.StartupRequestDto;
import com.ajay.fundbridge.dto.StartupResponseDto;
import com.ajay.fundbridge.exception.ResourceNotFoundException;
import com.ajay.fundbridge.exception.UnauthorizedException;
import com.ajay.fundbridge.mapper.StartupMapper;
import com.ajay.fundbridge.model.Startup;
import com.ajay.fundbridge.repository.StartupRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class StartupService {
    private final StartupRepository startupRepository;
    public StartupResponseDto addStartup(StartupRequestDto request , UUID userId){
        //when the service will be divided need to check for userId through service communication
        Startup startup= StartupMapper.toStartup(request);
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
        startup.setName(request.getName());
        startup.setTagline(request.getTagline());
        startup.setDescription(request.getDescription());
        startup.setIndustry(request.getIndustry());
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
}
