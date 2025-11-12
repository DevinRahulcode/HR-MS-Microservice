package com.example.employee_profile_service.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class EmployeeProfileDto {
    private Long userId;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String jobTitle;
    private String department;
    private LocalDate hireDate;
}
