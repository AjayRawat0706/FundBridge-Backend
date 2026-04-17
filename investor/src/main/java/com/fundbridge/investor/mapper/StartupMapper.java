package com.fundbridge.investor.mapper;

import com.fundbridge.investor.dto.StartupRequestDto;
import com.fundbridge.investor.dto.StartupResponseDto;
import com.fundbridge.investor.model.Startup;

public class StartupMapper {
    public static Startup toStartup(StartupRequestDto request){
        Startup startup=new Startup();
        startup.setName(request.getName());
        startup.setDescription(request.getDescription());
        startup.setIndustry(request.getIndustry());
        startup.setStatus(request.getStatus());
        startup.setFoundedYear(request.getFoundedYear());
        startup.setTagline(request.getTagline());
        startup.setWebsiteUrl(request.getWebsiteUrl());
        return startup;
    }

    public static StartupResponseDto toStartupResponse(Startup startup){
        StartupResponseDto response=new StartupResponseDto();
        response.setOwnerId(startup.getOwnerId());
        response.setId(startup.getId());
        response.setName(startup.getName());
        response.setIndustry(startup.getIndustry());
        response.setWebsiteUrl(startup.getWebsiteUrl());
        response.setStatus(startup.getStatus());
        response.setTagline(startup.getTagline());
        response.setFoundedYear(startup.getFoundedYear());
        response.setDescription(startup.getDescription());
        return response;
    }
}
