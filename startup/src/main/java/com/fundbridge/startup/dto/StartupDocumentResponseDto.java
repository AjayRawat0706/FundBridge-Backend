package com.fundbridge.startup.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class StartupDocumentResponseDto {
    private UUID id;
    private String documentType;
    private String fileUrl;
}
