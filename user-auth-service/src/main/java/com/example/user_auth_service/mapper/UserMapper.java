package com.example.user_auth_service.mapper;

import com.example.user_auth_service.dto.request.RegisterRequestDto;

import com.example.user_auth_service.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User registerDtoToUser(RegisterRequestDto dto) {
        return User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                // Password and Role are set in the service
                .build();
    }
}
