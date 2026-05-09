package com.fundbridge.users.service;

import com.fundbridge.users.dto.*;
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
    private final JwtService jwtService;
    public UserResponseDTO createUser(UserRequestDTO userRequest) {
        if(userRepository.findByEmail(userRequest.getEmail()).isPresent()){
          throw new ResourceAlreadyExistException("Profile already present with this email");
        }
        User user=UserMapper.toUser(userRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User newUser=userRepository.save(user);
        return UserMapper.toUserResponse(newUser);
    }
    public UserResponseDTO login (LoginRequestDto dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }
        return UserMapper.toUserResponse(user);
    }


    public UserResponseDTO getUser(UUID id){
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

    public AuthResponse refreshAccessToken(String refreshToken) {

        if (refreshToken == null || !jwtService.validateToken(refreshToken)) {
            throw new InvalidCredentialsException("Invalid refresh token");
        }

        UUID userId = jwtService.extractUserId(refreshToken);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        String accessToken =
                jwtService.generateToken(user.getId(),
                        user.getRole().toString());

        String newRefreshToken =
                jwtService.generateRefreshToken(user.getId());

        return new AuthResponse(accessToken, newRefreshToken);
    }

}
