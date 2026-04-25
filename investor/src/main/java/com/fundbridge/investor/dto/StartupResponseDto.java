package com.fundbridge.investor.dto;

import com.fundbridge.investor.model.StartupStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class StartupResponseDto {
    private UUID id;
    private String name;
    private String tagline;
    private String description;
    private String industry;
    private Integer industryId;
    private Integer foundedYear;
    private String websiteUrl;
    private StartupStatus status;
    private UUID ownerId;
}
