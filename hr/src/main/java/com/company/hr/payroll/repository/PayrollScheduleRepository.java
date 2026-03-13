package com.company.hr.payroll.repository;

import com.company.hr.payroll.entity.PayrollSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayrollScheduleRepository extends JpaRepository<PayrollSchedule,Long> {
}
