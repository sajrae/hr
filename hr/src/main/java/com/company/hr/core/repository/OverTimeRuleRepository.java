package com.company.hr.core.repository;

import com.company.hr.core.entity.OverTimeRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OverTimeRuleRepository extends JpaRepository<OverTimeRule,Long> {
}
