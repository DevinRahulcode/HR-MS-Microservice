package com.example.user_auth_service.service;
import com.example.user_auth_service.dto.request.LoginRequestDto;
import com.example.user_auth_service.dto.request.RegisterRequestDto;
import com.example.user_auth_service.dto.response.LoginResponseDto;

public interface AuthService {
    LoginResponseDto register(RegisterRequestDto request);
    LoginResponseDto login(LoginRequestDto request);
}
