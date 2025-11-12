package com.example.employee_profile_service.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee_profiles")
public class EmployeeProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // This is the foreign key to the user-auth-service `User` table
    @Column(unique = true, nullable = false)
    private Long userId;

    // This is also from the User, duplicated for easy access
    @Column(unique = true, nullable = false)
    private String email;

    private String firstName;
    private String lastName;

    // Profile-specific fields
    private String phone;
    private String address;
    private String jobTitle;
    private String department;
    private LocalDate hireDate;
}
