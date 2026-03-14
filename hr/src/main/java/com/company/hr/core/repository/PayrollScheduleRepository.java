package com.company.hr.core.repository;

import com.company.hr.core.entity.PayrollSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayrollScheduleRepository extends JpaRepository<PayrollSchedule,Long> {
}
