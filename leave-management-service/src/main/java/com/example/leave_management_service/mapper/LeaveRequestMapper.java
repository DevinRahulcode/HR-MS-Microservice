package com.example.leave_management_service.mapper;
import com.example.leave_management_service.dto.response.LeaveRequestDto;
import com.example.leave_management_service.entity.LeaveRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LeaveRequestMapper {

    public LeaveRequestDto toDto(LeaveRequest entity) {
        LeaveRequestDto dto = new LeaveRequestDto();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setLeaveType(entity.getLeaveType().name());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setReason(entity.getReason());
        dto.setStatus(entity.getStatus().name());
        dto.setRequestedDate(entity.getRequestedDate());
        dto.setManagerNotes(entity.getManagerNotes());
        return dto;
    }

    public List<LeaveRequestDto> toDtoList(List<LeaveRequest> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }
}
