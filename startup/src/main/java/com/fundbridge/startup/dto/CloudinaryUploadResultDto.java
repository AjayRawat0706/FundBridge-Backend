package com.fundbridge.startup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CloudinaryUploadResultDto {
    private String url;
    private String publicId;
}
