package com.example.leave_management_service.service;


import com.example.leave_management_service.dto.request.AdminActionDto;
import com.example.leave_management_service.dto.request.CreateLeaveRequestDto;
import com.example.leave_management_service.dto.response.LeaveRequestDto;

import java.util.List;

public interface LeaveRequestService {

    // --- Employee Methods ---
    LeaveRequestDto createLeaveRequest(CreateLeaveRequestDto request);
    List<LeaveRequestDto> getMyLeaveRequests();
    LeaveRequestDto cancelLeaveRequest(Long requestId);

    // --- Admin Methods ---
    List<LeaveRequestDto> getAllPendingRequests();
    LeaveRequestDto reviewLeaveRequest(Long requestId, AdminActionDto action);
}