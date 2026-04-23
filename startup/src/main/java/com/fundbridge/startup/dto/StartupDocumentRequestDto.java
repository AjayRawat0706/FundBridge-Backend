package com.fundbridge.startup.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class StartupDocumentRequestDto {
    @NotNull(message = "StartupId is required")
    private UUID startupId;
    @NotBlank(message = "Document type is required")
    private String documentType;
}
