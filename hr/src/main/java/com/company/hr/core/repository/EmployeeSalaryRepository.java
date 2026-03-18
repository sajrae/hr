package com.company.hr.core.repository;

import com.company.hr.core.entity.EmployeeSalary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeSalaryRepository extends JpaRepository<EmployeeSalary,Long> {
    Optional<EmployeeSalary> findByEmployeeId (Long employeeId);
}
