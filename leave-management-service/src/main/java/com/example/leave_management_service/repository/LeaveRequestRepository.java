package com.example.leave_management_service.repository;

import com.example.leave_management_service.entity.LeaveRequest;
import com.example.leave_management_service.enums.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    // For employees to get their own requests
    List<LeaveRequest> findByUserIdOrderByRequestedDateDesc(Long userId);

    // For admins to find pending requests
    List<LeaveRequest> findByStatusOrderByRequestedDateAsc(LeaveStatus status);
}
