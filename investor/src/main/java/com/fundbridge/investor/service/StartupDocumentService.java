package com.fundbridge.investor.service;

import com.fundbridge.investor.dto.CloudinaryUploadResultDto;
import com.fundbridge.investor.dto.StartupDocumentRequestDto;
import com.fundbridge.investor.dto.StartupDocumentResponseDto;
import com.fundbridge.investor.exception.ResourceNotFoundException;
import com.fundbridge.investor.exception.UnauthorizedException;
import com.fundbridge.investor.mapper.StartupDocumentMapper;
import com.fundbridge.investor.model.Startup;
import com.fundbridge.investor.model.StartupDocument;
import com.fundbridge.investor.repository.StartupDocumentRepository;
import com.fundbridge.investor.repository.StartupRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class StartupDocumentService {
    private final StartupDocumentRepository startupDocumentRepository;
    private final StartupRepository startupRepository;
    private final CloudinaryService cloudinaryService;
    public StartupDocumentResponseDto addDocument(StartupDocumentRequestDto request, MultipartFile file, UUID userId){
        Startup startup=startupRepository.findById(request.getStartupId())
                .orElseThrow(()->new ResourceNotFoundException("Startup not found"));
        if(!startup.getOwnerId().equals(userId)){
            throw new UnauthorizedException("User not authorized");
        }
        if(file == null || file.isEmpty()){
            throw new RuntimeException("File cannot be empty");
        }
        CloudinaryUploadResultDto cloudinaryResult=cloudinaryService.uploadDocument(file);
        String fileUrl=cloudinaryResult.getUrl();
        String publicId= cloudinaryResult.getPublicId();
        StartupDocument document= StartupDocumentMapper.toDocument(request,startup,fileUrl,publicId);
        StartupDocument savedDocument=startupDocumentRepository.save(document);
        return StartupDocumentMapper.toResponse(savedDocument);
    }


    public List<StartupDocumentResponseDto> getAllDocuments(UUID startupId){
        startupRepository.findById(startupId)
                .orElseThrow(() -> new ResourceNotFoundException("Startup not found"));

        List<StartupDocument>documents= startupDocumentRepository.findByStartupId(startupId);

        return documents.stream().map(StartupDocumentMapper::toResponse)
                .toList();

    }

    public void deleteDocument(UUID documentId, UUID userId){
        StartupDocument document = startupDocumentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found"));
        Startup startup = document.getStartup();
        if(!startup.getOwnerId().equals(userId)){
            throw new UnauthorizedException("User not authorized");
        }
        cloudinaryService.deleteFile(document.getPublicId());
        startupDocumentRepository.delete(document);
    }

    public StartupDocumentResponseDto updateDocument(StartupDocumentRequestDto request, MultipartFile file,UUID documentId, UUID userid ){
        StartupDocument document=startupDocumentRepository.findById(documentId)
                .orElseThrow(()->new ResourceNotFoundException("Document not found"));
        if(!document.getStartup().getOwnerId().equals(userid)){
            throw new UnauthorizedException("User not authorized");
        }
        String fileUrl=document.getFileUrl();
        String publicId=document.getPublicId();
        if(file!=null && !file.isEmpty()){
           CloudinaryUploadResultDto  cloudinaryResult=cloudinaryService.uploadDocument(file);
           fileUrl=cloudinaryResult.getUrl();
           publicId= cloudinaryResult.getPublicId();
           cloudinaryService.deleteFile(document.getPublicId());
        }
        document.setFileUrl(fileUrl);
        document.setPublicId(publicId);
        document.setDocumentType(request.getDocumentType());
        StartupDocument updatedDocument= startupDocumentRepository.save(document);
        return StartupDocumentMapper.toResponse(updatedDocument);
    }

}
