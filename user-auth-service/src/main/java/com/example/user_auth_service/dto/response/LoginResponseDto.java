package com.example.user_auth_service.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public class LoginResponseDto {
    private String token;
    private long expiresIn;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
