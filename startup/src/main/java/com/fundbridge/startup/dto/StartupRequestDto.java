package com.fundbridge.startup.dto;

import com.fundbridge.startup.model.StartupStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StartupRequestDto {

    @NotBlank(message = "Startup name required")
    private String name;
    private String tagline;
    private String description;
    private Integer industryId;
    private Integer foundedYear;
    private String websiteUrl;
    private StartupStatus status;

}