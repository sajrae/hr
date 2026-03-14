package com.company.hr.core.repository;

import com.company.hr.core.entity.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveTypeRepository extends JpaRepository<LeaveType,Long> {
}
