package com.company.hr.core.repository;

import com.company.hr.core.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance,Long> {

    Optional<Attendance> findByEmployeeIdAndDate(Long employeeId, LocalDate date);
}
