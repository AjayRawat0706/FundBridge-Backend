package com.fundbridge.startup.mapper;

import com.fundbridge.startup.dto.StartupDocumentRequestDto;
import com.fundbridge.startup.dto.StartupDocumentResponseDto;
import com.fundbridge.startup.model.Startup;
import com.fundbridge.startup.model.StartupDocument;

public class StartupDocumentMapper {
    public static StartupDocument toDocument(StartupDocumentRequestDto request, Startup startup,String fileUrl,String publicId){
        StartupDocument document=new StartupDocument();
        document.setDocumentType(request.getDocumentType());
        document.setFileUrl(fileUrl);
        document.setStartup(startup);
        document.setPublicId(publicId);
        return document;
    }

    public static StartupDocumentResponseDto toResponse(StartupDocument document){
        StartupDocumentResponseDto response=new StartupDocumentResponseDto();
        response.setId(document.getId());
        response.setDocumentType(document.getDocumentType());
        response.setFileUrl(document.getFileUrl());
        return response;
    }
}
