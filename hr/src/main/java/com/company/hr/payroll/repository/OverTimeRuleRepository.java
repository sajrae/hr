package com.company.hr.payroll.repository;

import com.company.hr.payroll.entity.OverTimeRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OverTimeRuleRepository extends JpaRepository<OverTimeRule,Long> {
}
