package com.ajay.fundbridge.mapper;

import com.ajay.fundbridge.dto.StartupDocumentRequestDto;
import com.ajay.fundbridge.dto.StartupDocumentResponseDto;
import com.ajay.fundbridge.model.Startup;
import com.ajay.fundbridge.model.StartupDocument;

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
