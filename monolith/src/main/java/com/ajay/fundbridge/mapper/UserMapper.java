package com.ajay.fundbridge.mapper;

import com.ajay.fundbridge.model.Role;
import com.ajay.fundbridge.model.User;
import com.ajay.fundbridge.dto.UserRequestDTO;
import com.ajay.fundbridge.dto.UserResponseDTO;

public class UserMapper {
    public static User toUser(UserRequestDTO userRequest){
        User user=new User();
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setRole(userRequest.getRole().equals("startup")?Role.STARTUP:Role.INVESTOR);
        return user;
    }
    public static UserResponseDTO toUserResponse(User user){
        UserResponseDTO response= new UserResponseDTO();
        response.setId(String.valueOf(user.getId()));
        response.setEmail(user.getEmail());
        response.setRole(String.valueOf(user.getRole()));
        response.setActive(user.isActive());
        response.setVerified(user.isVerified());
        return response;
    }
}
