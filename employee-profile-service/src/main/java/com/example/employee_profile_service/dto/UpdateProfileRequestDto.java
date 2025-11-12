package com.example.employee_profile_service.dto;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateProfileRequestDto {

    @Size(min = 10, max = 15)
    private String phone;

    @Size(min = 5, max = 255)
    private String address;
}
