package com.example.employee_profile_service.mapper;

import com.example.employee_profile_service.dto.AdminCreateProfileDto;
import com.example.employee_profile_service.dto.EmployeeProfileDto;
import com.example.employee_profile_service.entity.EmployeeProfile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class EmployeeProfileMapper {

    public EmployeeProfileDto toDto(EmployeeProfile entity) {
        EmployeeProfileDto dto = new EmployeeProfileDto();
        dto.setUserId(entity.getUserId());
        dto.setEmail(entity.getEmail());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setPhone(entity.getPhone());
        dto.setAddress(entity.getAddress());
        dto.setJobTitle(entity.getJobTitle());
        dto.setDepartment(entity.getDepartment());
        dto.setHireDate(entity.getHireDate());
        return dto;
    }

    public EmployeeProfile createDtoToEntity(AdminCreateProfileDto dto) {
        return EmployeeProfile.builder()
                .userId(dto.getUserId())
                .email(dto.getEmail())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .jobTitle(dto.getJobTitle())
                .department(dto.getDepartment())
                .hireDate(LocalDate.now()) // Set hire date on creation
                .build();
    }
}
