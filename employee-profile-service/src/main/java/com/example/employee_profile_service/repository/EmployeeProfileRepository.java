package com.example.employee_profile_service.repository;
import com.example.employee_profile_service.entity.EmployeeProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeProfileRepository extends JpaRepository<EmployeeProfile, Long> {

    // We need to be able to find a profile by the user's email (from the token)
    Optional<EmployeeProfile> findByEmail(String email);

    // We also need to find by userId for admin tasks
    Optional<EmployeeProfile> findByUserId(Long userId);
}
