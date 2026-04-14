package com.ajay.fundbridge.service;

import com.ajay.fundbridge.dto.CloudinaryUploadResultDto;
import com.ajay.fundbridge.dto.ProfileRequestDto;
import com.ajay.fundbridge.dto.ProfileResponseDto;
import com.ajay.fundbridge.exception.ResourceAlreadyExistException;
import com.ajay.fundbridge.exception.ResourceNotFoundException;
import com.ajay.fundbridge.mapper.ProfileMapper;
import com.ajay.fundbridge.model.Profile;
import com.ajay.fundbridge.repository.ProfileRepository;
import com.ajay.fundbridge.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProfileService {
   private final ProfileRepository profileRepository;
    private final CloudinaryService cloudinaryService;
    private final UserRepository userRepository;
   public ProfileResponseDto newProfile(ProfileRequestDto profileRequestDto, UUID userId){
       if(userRepository.findById(userId).isEmpty()){
           throw new ResourceNotFoundException("User does not exist");
       }
       if(profileRepository.findByUserId(userId).isPresent()){
           throw new ResourceAlreadyExistException("Profile Already present");
       }
       Profile profile= ProfileMapper.toProfile(profileRequestDto);
       profile.setUserId(userId);
      Profile newProfile=  profileRepository.save(profile);
       return ProfileMapper.toProfileResponse(newProfile);
   }

   public ProfileResponseDto getProfile(UUID userId){
       Profile profile = profileRepository.findByUserId(userId)
               .orElseThrow(() ->
                       new ResourceNotFoundException("Profile not found ")
               );
       return ProfileMapper.toProfileResponse(profile);
   }

    public void uploadProfileImage(UUID userId, MultipartFile file) {

        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found"));

        CloudinaryUploadResultDto imageResult = cloudinaryService.uploadProfileImage(file);

        profile.setProfileImage(imageResult.getUrl());
        profile.setImagePublicId(imageResult.getPublicId());
        profileRepository.save(profile);
    }
    @Transactional
    public ProfileResponseDto updateProfile(UUID userId, ProfileRequestDto request) {

        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found"));

        Optional.ofNullable(request.getFirstName()).ifPresent(profile::setFirstName);
        Optional.ofNullable(request.getLastName()).ifPresent(profile::setLastName);
        Optional.ofNullable(request.getDateOfBirth()).ifPresent(profile::setDateOfBirth);

        if (request.getMobileNumber() != null &&
                !request.getMobileNumber().equals(profile.getMobileNumber())) {

            if (profileRepository.existsByMobileNumber(request.getMobileNumber())) {
                throw new ResourceAlreadyExistException("Mobile number already exists");
            }
            profile.setMobileNumber(request.getMobileNumber());
        }

        Profile updatedProfile = profileRepository.save(profile);
        return ProfileMapper.toProfileResponse(updatedProfile);
    }


    @Transactional
    public void removeProfilePhoto(UUID userId) {

        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found"));

        if (profile.getProfileImage() == null) {
            return;
        }
        cloudinaryService.deleteFile(profile.getImagePublicId());
        profile.setProfileImage(null);
        profile.setImagePublicId(null);
        profileRepository.save(profile);
    }
}
