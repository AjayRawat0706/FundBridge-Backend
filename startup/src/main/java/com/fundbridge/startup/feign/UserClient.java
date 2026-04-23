package com.fundbridge.startup.feign;

import com.fundbridge.startup.dto.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "user-service", url = "http://localhost:8081")
public interface UserClient {
    @GetMapping("/api/users/{id}")
    UserResponseDTO getUserById(@PathVariable("id") UUID userId);
}
