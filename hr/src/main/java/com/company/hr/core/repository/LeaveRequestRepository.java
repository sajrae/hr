package com.company.hr.core.repository;

import com.company.hr.core.entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest,Long>{

    Optional<LeaveRequest> findByEmployeeId(Long employeeId);

    Optional<LeaveRequest> findByLeaveTypeId(Long leaveTypeId);
}
