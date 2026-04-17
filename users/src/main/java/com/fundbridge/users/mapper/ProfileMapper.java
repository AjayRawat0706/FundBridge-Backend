package com.fundbridge.users.mapper;

import com.fundbridge.users.dto.ProfileRequestDto;
import com.fundbridge.users.dto.ProfileResponseDto;
import com.fundbridge.users.model.Profile;

public class ProfileMapper {
    public static Profile toProfile(ProfileRequestDto profileRequest){
        Profile profile=new Profile();
        profile.setFirstName(profileRequest.getFirstName());
        profile.setLastName(profileRequest.getFirstName());
        profile.setMobileNumber(profileRequest.getMobileNumber());
        profile.setDateOfBirth(profileRequest.getDateOfBirth());
        return profile;
    }
    public static ProfileResponseDto toProfileResponse(Profile profile){
        ProfileResponseDto profileResponse =new ProfileResponseDto();
        profileResponse.setFirstName(profile.getFirstName());
        profileResponse.setLastName(profile.getLastName());
        profileResponse.setProfileImage(profile.getProfileImage());
        profileResponse.setDateOfBirth(profile.getDateOfBirth());
        profileResponse.setMobileNumber(profile.getMobileNumber());
        return profileResponse;
    }
}
