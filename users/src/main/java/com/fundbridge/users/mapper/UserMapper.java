package com.fundbridge.users.mapper;

import com.fundbridge.users.dto.UserRequestDTO;
import com.fundbridge.users.dto.UserResponseDTO;
import com.fundbridge.users.model.Role;
import com.fundbridge.users.model.User;

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
