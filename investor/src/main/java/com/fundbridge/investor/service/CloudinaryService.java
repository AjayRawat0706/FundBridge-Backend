package com.fundbridge.investor.service;

import com.fundbridge.investor.dto.CloudinaryUploadResultDto;
import com.fundbridge.investor.exception.FileDeleteException;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
@AllArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryUploadResultDto uploadProfileImage(MultipartFile file) {
        try {
            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", "fundbridge/profile-images",
                            "resource_type", "image"
                    )
            );
            String url = uploadResult.get("secure_url").toString();
            String publicId = uploadResult.get("public_id").toString();

            return new CloudinaryUploadResultDto(url, publicId);

        } catch (Exception e) {
            throw new RuntimeException("Failed to upload image", e);
        }
    }

    public CloudinaryUploadResultDto uploadDocument(MultipartFile file) {
        try {
            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", "fundbridge/startup-documents",
                            "resource_type", "auto"
                    )
            );
            String url = uploadResult.get("secure_url").toString();
            String publicId = uploadResult.get("public_id").toString();
            return new CloudinaryUploadResultDto(url, publicId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload document", e);
        }
    }

    public void deleteFile(String publicId) {
        try {
            cloudinary.uploader().destroy(
                    publicId,
                    ObjectUtils.asMap("resource_type", "auto")
            );
        } catch (Exception e) {
            throw new FileDeleteException("Failed to delete file");
        }
    }


}