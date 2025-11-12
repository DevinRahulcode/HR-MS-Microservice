package com.example.employee_profile_service.service;

import com.example.employee_profile_service.dto.AdminCreateProfileDto;
import com.example.employee_profile_service.dto.EmployeeProfileDto;
import com.example.employee_profile_service.dto.UpdateProfileRequestDto;

public interface EmployeeProfileService {

    // For an employee to get their own profile
    EmployeeProfileDto getMyProfile();

    // For an employee to update their own profile
    EmployeeProfileDto updateMyProfile(UpdateProfileRequestDto request);

    // For an admin to get any profile
    EmployeeProfileDto getProfileByUserId(Long userId);

    // For an admin to create a profile
    EmployeeProfileDto createProfile(AdminCreateProfileDto request);
}
