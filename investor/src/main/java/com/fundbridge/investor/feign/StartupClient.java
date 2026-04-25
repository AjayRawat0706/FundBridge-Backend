package com.fundbridge.investor.feign;

import com.fundbridge.investor.dto.StartupResponseDto;
import com.fundbridge.investor.dto.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "startup-service", url = "http://localhost:8081")
public interface StartupClient {
    @GetMapping("/api/startups/{id}")
    StartupResponseDto getStartupById(@PathVariable("id") UUID userId);
}
