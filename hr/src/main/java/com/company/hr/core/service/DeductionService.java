package com.company.hr.core.service;

import com.company.hr.core.entity.DeductionBracket;
import com.company.hr.core.entity.DeductionType;
import com.company.hr.core.entity.Employee;
import com.company.hr.core.entity.EmployeeSalary;
import com.company.hr.core.repository.DeductionBracketRepository;
import com.company.hr.core.repository.DeductionTypeRepository;
import com.company.hr.core.repository.EmployeeSalaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class DeductionService {

    private final DeductionBracketRepository deductionBracketRepository;
    private final DeductionTypeRepository deductionTypeRepository;
    private final EmployeeSalaryRepository salaryRepository;

    public BigDecimal calculateContribution(final Employee employee, final String deductionName) {
        DeductionType type = deductionTypeRepository.findByName(deductionName)
                .orElseThrow(() -> new RuntimeException("Deduction not found"));

        EmployeeSalary salary = salaryRepository.findByEmployeeId(employee.getId())
                .orElseThrow(() -> new RuntimeException("Employee Salary Not found"));

        if (type.getRate() != null) {
            return BigDecimal.valueOf(type.getRate());
        }

        BigDecimal empSalary = salary.getBaseSalary();
        DeductionBracket deductionBracket = deductionBracketRepository.findByDeductionTypeAndMinSalaryLessThanEqualAndMaxSalaryGreaterThanEqual(type, empSalary, empSalary)
                .orElseThrow(() -> new RuntimeException("No Bracket Found for Salary"));

        return BigDecimal.valueOf(deductionBracket.getContribution());
    }
}
