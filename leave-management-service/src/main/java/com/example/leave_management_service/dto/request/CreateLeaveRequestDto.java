package com.example.leave_management_service.dto.request;

import com.example.leave_management_service.enums.LeaveType;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateLeaveRequestDto {
    @NotNull(message = "Leave type cannot be null")
    private LeaveType leaveType;

    @NotNull
    @FutureOrPresent(message = "Start date must be in the present or future")
    private java.time.LocalDate startDate;

    @NotNull
    @FutureOrPresent(message = "End date must be in the present or future")
    private java.time.LocalDate endDate;

    @NotBlank(message = "Reason cannot be blank")
    private String reason;
}
