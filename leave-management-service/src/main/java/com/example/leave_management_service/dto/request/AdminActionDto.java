package com.example.leave_management_service.dto.request;

import com.example.leave_management_service.enums.LeaveStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AdminActionDto {

    // Admin can only approve or deny
    @NotNull
    private LeaveStatus status;

    @NotBlank
    private String managerNotes;
}
