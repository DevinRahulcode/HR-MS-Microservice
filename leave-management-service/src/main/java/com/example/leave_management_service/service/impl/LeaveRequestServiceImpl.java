package com.example.leave_management_service.service.impl;

import com.example.leave_management_service.dto.request.AdminActionDto;
import com.example.leave_management_service.dto.request.CreateLeaveRequestDto;
import com.example.leave_management_service.dto.response.LeaveRequestDto;
import com.example.leave_management_service.entity.LeaveRequest;
import com.example.leave_management_service.enums.LeaveStatus;
import com.example.leave_management_service.exception.InvalidLeaveRequestException;
import com.example.leave_management_service.exception.LeaveRequestNotFoundException;
import com.example.leave_management_service.mapper.LeaveRequestMapper;
import com.example.leave_management_service.repository.LeaveRequestRepository;
import com.example.leave_management_service.service.LeaveRequestService;
import com.example.leave_management_service.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveRequestServiceImpl implements LeaveRequestService {

    private final LeaveRequestRepository repository;
    private final LeaveRequestMapper mapper;

    @Override
    public LeaveRequestDto createLeaveRequest(CreateLeaveRequestDto request) {
        if (request.getStartDate().isAfter(request.getEndDate())) {
            throw new InvalidLeaveRequestException("Start date must be before or equal to end date.");
        }

        LeaveRequest newRequest = LeaveRequest.builder()
                .userId(SecurityUtils.getCurrentUserId()) // Get ID from our SecurityUtils
                .leaveType(request.getLeaveType())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .reason(request.getReason())
                .status(LeaveStatus.PENDING)
                .requestedDate(LocalDate.now())
                .build();

        LeaveRequest savedRequest = repository.save(newRequest);
        return mapper.toDto(savedRequest);
    }

    @Override
    public List<LeaveRequestDto> getMyLeaveRequests() {
        Long userId = SecurityUtils.getCurrentUserId();
        List<LeaveRequest> requests = repository.findByUserIdOrderByRequestedDateDesc(userId);
        return mapper.toDtoList(requests);
    }

    @Override
    public LeaveRequestDto cancelLeaveRequest(Long requestId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        LeaveRequest request = findRequestById(requestId);

        // Production-grade check: Ensure the user owns this request
        if (!request.getUserId().equals(currentUserId)) {
            throw new AccessDeniedException("You can only cancel your own leave requests.");
        }

        if (request.getStatus() != LeaveStatus.PENDING) {
            throw new InvalidLeaveRequestException("Only PENDING requests can be cancelled.");
        }

        request.setStatus(LeaveStatus.CANCELLED);
        LeaveRequest updatedRequest = repository.save(request);
        return mapper.toDto(updatedRequest);
    }

    @Override
    public List<LeaveRequestDto> getAllPendingRequests() {
        // This method is only called by an admin (enforced by controller)
        List<LeaveRequest> requests = repository.findByStatusOrderByRequestedDateAsc(LeaveStatus.PENDING);
        return mapper.toDtoList(requests);
    }

    @Override
    public LeaveRequestDto reviewLeaveRequest(Long requestId, AdminActionDto action) {
        LeaveRequest request = findRequestById(requestId);

        if (request.getStatus() != LeaveStatus.PENDING) {
            throw new InvalidLeaveRequestException("This request has already been reviewed.");
        }

        if (action.getStatus() != LeaveStatus.APPROVED && action.getStatus() != LeaveStatus.DENIED) {
            throw new InvalidLeaveRequestException("Admin action must be APPROVE or DENY.");
        }

        request.setStatus(action.getStatus());
        request.setManagerNotes(action.getManagerNotes());

        LeaveRequest updatedRequest = repository.save(request);
        return mapper.toDto(updatedRequest);
    }

    // --- Helper Method ---
    private LeaveRequest findRequestById(Long requestId) {
        return repository.findById(requestId)
                .orElseThrow(() -> new LeaveRequestNotFoundException("Leave request not found with ID: " + requestId));
    }
}