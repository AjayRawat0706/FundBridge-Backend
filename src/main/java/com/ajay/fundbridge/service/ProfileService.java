package com.ajay.fundbridge.service;

import com.ajay.fundbridge.dto.ProfileRequestDto;
import com.ajay.fundbridge.dto.ProfileResponseDto;
import com.ajay.fundbridge.exception.ResourceAlreadyExistException;
import com.ajay.fundbridge.exception.ResourceNotFoundException;
import com.ajay.fundbridge.mapper.ProfileMapper;
import com.ajay.fundbridge.model.Profile;
import com.ajay.fundbridge.repository.ProfileRepository;
import com.ajay.fundbridge.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ProfileService {
   private final ProfileRepository profileRepository;
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
}
