package com.example.employee_profile_service.controller;

import com.example.employee_profile_service.dto.AdminCreateProfileDto;
import com.example.employee_profile_service.dto.EmployeeProfileDto;
import com.example.employee_profile_service.dto.UpdateProfileRequestDto;
import com.example.employee_profile_service.service.EmployeeProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class EmployeeProfileController {

    private final EmployeeProfileService profileService;

    // --- Employee Endpoints ---

    @GetMapping("/get-all")
    public ResponseEntity<EmployeeProfileDto> getMyProfile() {
        return ResponseEntity.ok(profileService.getMyProfile());
    }

    @PutMapping("/update-profile")
    public ResponseEntity<EmployeeProfileDto> updateMyProfile(@Valid @RequestBody UpdateProfileRequestDto request) {
        return ResponseEntity.ok(profileService.updateMyProfile(request));
    }

    // --- Admin Endpoints ---

    @PostMapping("/create-employee")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')") // Only Admins can create
    public ResponseEntity<EmployeeProfileDto> createProfile(@Valid @RequestBody AdminCreateProfileDto request) {
        return new ResponseEntity<>(profileService.createProfile(request), HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')") // Only Admins can get by ID
    public ResponseEntity<EmployeeProfileDto> getProfileByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(profileService.getProfileByUserId(userId));
    }
}
