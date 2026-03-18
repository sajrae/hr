package com.company.hr.core.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AssignSalaryRequest {

    @NotNull
    @Positive
    private BigDecimal baseSalary;

    @NotNull
    private Long taxRule;

    @NotNull
    private Long payrollScheduleId;

    @NotNull
    private Long overTimeRule;


}
