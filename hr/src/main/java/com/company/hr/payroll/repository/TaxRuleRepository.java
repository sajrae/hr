package com.company.hr.payroll.repository;

import com.company.hr.payroll.entity.TaxRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxRuleRepository extends JpaRepository<TaxRule,Long> {
}
