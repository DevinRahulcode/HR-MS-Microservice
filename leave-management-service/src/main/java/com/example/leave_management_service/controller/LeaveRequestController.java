package com.example.leave_management_service.controller;

import com.example.leave_management_service.dto.request.AdminActionDto;
import com.example.leave_management_service.dto.request.CreateLeaveRequestDto;
import com.example.leave_management_service.dto.response.LeaveRequestDto;
import com.example.leave_management_service.service.LeaveRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leave")
@RequiredArgsConstructor
public class LeaveRequestController {

    private final LeaveRequestService leaveService;

    // --- EMPLOYEE ENDPOINTS ---

    @PostMapping
    public ResponseEntity<LeaveRequestDto> createLeaveRequest(@Valid @RequestBody CreateLeaveRequestDto request) {
        return new ResponseEntity<>(leaveService.createLeaveRequest(request), HttpStatus.CREATED);
    }

    @GetMapping("/my-requests")
    public ResponseEntity<List<LeaveRequestDto>> getMyLeaveRequests() {
        return ResponseEntity.ok(leaveService.getMyLeaveRequests());
    }

    @PutMapping("/my-requests/{requestId}/cancel")
    public ResponseEntity<LeaveRequestDto> cancelLeaveRequest(@PathVariable Long requestId) {
        return ResponseEntity.ok(leaveService.cancelLeaveRequest(requestId));
    }

    // --- ADMIN ENDPOINTS ---

    @GetMapping("/admin/pending")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<LeaveRequestDto>> getAllPendingRequests() {
        return ResponseEntity.ok(leaveService.getAllPendingRequests());
    }

    @PutMapping("/admin/{requestId}/review")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<LeaveRequestDto> reviewLeaveRequest(
            @PathVariable Long requestId,
            @Valid @RequestBody AdminActionDto action
    ) {
        return ResponseEntity.ok(leaveService.reviewLeaveRequest(requestId, action));
    }
}
