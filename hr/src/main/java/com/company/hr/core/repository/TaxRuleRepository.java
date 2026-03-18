package com.company.hr.core.repository;

import com.company.hr.core.entity.TaxRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxRuleRepository extends JpaRepository<TaxRule,Long> {
}
