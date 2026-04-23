package com.fundbridge.startup.controller;

import com.fundbridge.startup.dto.StartupDocumentRequestDto;
import com.fundbridge.startup.dto.StartupDocumentResponseDto;
import com.fundbridge.startup.service.StartupDocumentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/documents")
public class StartupDocumentController {
    private final StartupDocumentService startupDocumentService;
    @PostMapping
    ResponseEntity<StartupDocumentResponseDto>addDocument(@Valid @ModelAttribute StartupDocumentRequestDto request,
                                                          @RequestHeader ("USER-ID") UUID userId,
                                                          @RequestParam("file") MultipartFile file){
        StartupDocumentResponseDto document=startupDocumentService.addDocument(request,file,userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(document);
    }

    @GetMapping("/{startupId}")
    ResponseEntity<List<StartupDocumentResponseDto>>getAllDocuments(@PathVariable UUID startupId){
        List<StartupDocumentResponseDto> documents = startupDocumentService.getAllDocuments(startupId);
        return ResponseEntity.ok(documents);

    }

    @DeleteMapping("/{documentId}")
    ResponseEntity<Void>deleteDocument(@PathVariable UUID documentId,
                                       @RequestHeader ("USER-ID") UUID userId){
        startupDocumentService.deleteDocument(documentId,userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{documentId}")
    ResponseEntity<StartupDocumentResponseDto>updateDocument(@PathVariable UUID documentId,
                                                             @ModelAttribute StartupDocumentRequestDto request,
                                                             @RequestParam("file") MultipartFile file,
                                                             @RequestHeader ("USER-ID") UUID userId){
        StartupDocumentResponseDto document=startupDocumentService.updateDocument(request,file,documentId,userId);
        return ResponseEntity.ok(document);
    }
}
