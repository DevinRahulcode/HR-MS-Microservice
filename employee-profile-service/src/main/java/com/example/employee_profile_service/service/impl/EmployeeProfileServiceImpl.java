package com.example.employee_profile_service.service.impl;

import com.example.employee_profile_service.dto.AdminCreateProfileDto;
import com.example.employee_profile_service.dto.EmployeeProfileDto;
import com.example.employee_profile_service.dto.UpdateProfileRequestDto;
import com.example.employee_profile_service.entity.EmployeeProfile;
import com.example.employee_profile_service.exception.ProfileNotFoundException;
import com.example.employee_profile_service.mapper.EmployeeProfileMapper;
import com.example.employee_profile_service.repository.EmployeeProfileRepository;
import com.example.employee_profile_service.service.EmployeeProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeProfileServiceImpl implements EmployeeProfileService {

    private final EmployeeProfileRepository repository;
    private final EmployeeProfileMapper mapper;

    @Override
    public EmployeeProfileDto getMyProfile() {
        String currentUserEmail = getCurrentAuthenticatedUserEmail();
        EmployeeProfile profile = findProfileByEmail(currentUserEmail);
        return mapper.toDto(profile);
    }

    @Override
    public EmployeeProfileDto updateMyProfile(UpdateProfileRequestDto request) {
        String currentUserEmail = getCurrentAuthenticatedUserEmail();
        EmployeeProfile profile = findProfileByEmail(currentUserEmail);

        // Update only the allowed fields
        profile.setPhone(request.getPhone());
        profile.setAddress(request.getAddress());

        EmployeeProfile updatedProfile = repository.save(profile);
        return mapper.toDto(updatedProfile);
    }

    @Override
    public EmployeeProfileDto getProfileByUserId(Long userId) {
        EmployeeProfile profile = findProfileByUserId(userId);
        return mapper.toDto(profile);
    }

    @Override
    public EmployeeProfileDto createProfile(AdminCreateProfileDto request) {
        // TODO: Add check if profile with userId or email already exists
        EmployeeProfile newProfile = mapper.createDtoToEntity(request);
        EmployeeProfile savedProfile = repository.save(newProfile);
        return mapper.toDto(savedProfile);
    }

    // --- Helper Methods ---

    private String getCurrentAuthenticatedUserEmail() {
        // This gets the "principal" we set in the JwtAuthFilter
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private EmployeeProfile findProfileByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new ProfileNotFoundException("No profile found for email: " + email));
    }

    private EmployeeProfile findProfileByUserId(Long userId) {
        return repository.findByUserId(userId)
                .orElseThrow(() -> new ProfileNotFoundException("No profile found for user ID: " + userId));
    }
}
