package com.fundbridge.investor.mapper;

import com.fundbridge.investor.dto.StartupDocumentRequestDto;
import com.fundbridge.investor.dto.StartupDocumentResponseDto;
import com.fundbridge.investor.model.Startup;
import com.fundbridge.investor.model.StartupDocument;

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
