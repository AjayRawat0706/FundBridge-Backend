package com.fundbridge.users.service;

import com.fundbridge.users.dto.ChangePasswordRequestDto;
import com.fundbridge.users.dto.LoginRequestDto;
import com.fundbridge.users.dto.UserRequestDTO;
import com.fundbridge.users.dto.UserResponseDTO;
import com.fundbridge.users.exception.InvalidCredentialsException;
import com.fundbridge.users.exception.ResourceAlreadyExistException;
import com.fundbridge.users.exception.ResourceNotFoundException;
import com.fundbridge.users.mapper.UserMapper;
import com.fundbridge.users.model.User;
import com.fundbridge.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserResponseDTO createUser(UserRequestDTO userRequest) {
        if(userRepository.findByEmail(userRequest.getEmail()).isPresent()){
          throw new ResourceAlreadyExistException("Profile already present with this email");
        }
        User user=UserMapper.toUser(userRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User newUser=userRepository.save(user);
        return UserMapper.toUserResponse(newUser);
    }

    public UserResponseDTO logInUser(LoginRequestDto loginRequestDto){
        User user=userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));
        if(!passwordEncoder.matches(loginRequestDto.getPassword(),user.getPassword())){
            throw new InvalidCredentialsException("Invalid email or password");
        }
        return UserMapper.toUserResponse(user);
    }
    public UserResponseDTO getUser(UUID id){
        System.out.println("service hitted");
        User user =userRepository.findById(id)
                   .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return UserMapper.toUserResponse(user);
    }
    public void updatePassword(ChangePasswordRequestDto changePasswordRequestDto, UUID id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if(!passwordEncoder.matches(changePasswordRequestDto.getOldPassword(), user.getPassword())){
             throw new InvalidCredentialsException("Old password not correct");
         }
         user.setPassword(passwordEncoder.encode(changePasswordRequestDto.getNewPassword()));
         userRepository.save(user);
    }

}
