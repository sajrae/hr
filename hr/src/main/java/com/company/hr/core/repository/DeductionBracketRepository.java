package com.company.hr.core.repository;

import com.company.hr.core.entity.DeductionBracket;
import com.company.hr.core.entity.DeductionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface DeductionBracketRepository extends JpaRepository<DeductionBracket, Long> {

    Optional<DeductionBracket> findByDeductionTypeAndMinSalaryLessThanEqualAndMaxSalaryGreaterThanEqual(DeductionType deductionType, BigDecimal minSalaryIsLessThan, BigDecimal maxSalaryIsGreaterThan);
}
