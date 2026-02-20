package com.ajay.fundbridge.service;

import com.ajay.fundbridge.dto.ChangePasswordRequestDto;
import com.ajay.fundbridge.dto.LoginRequestDto;
import com.ajay.fundbridge.dto.UserRequestDTO;
import com.ajay.fundbridge.dto.UserResponseDTO;
import com.ajay.fundbridge.exception.EmailAlreadyExistException;
import com.ajay.fundbridge.exception.InvalidCredentialsException;
import com.ajay.fundbridge.exception.ResourceNotFoundException;
import com.ajay.fundbridge.mapper.UserMapper;
import com.ajay.fundbridge.model.User;
import com.ajay.fundbridge.repository.UserRepository;
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
          throw new EmailAlreadyExistException("Profile already present with this email");
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
