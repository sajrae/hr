package com.company.hr.core.repository;

import com.company.hr.core.entity.EmployeeLeave;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeLeaveRepository extends JpaRepository<EmployeeLeave,Long> {

    Optional<EmployeeLeave> findByEmployeeId(Long employeeId);
}
