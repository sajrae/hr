package com.company.hr.core.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignSalaryRequest {

    @NotNull
    @Positive
    private Double baseSalary;

    @NotNull
    private Long taxRule;

    @NotNull
    private Long payrollScheduleId;

    @NotNull
    private Long overTimeRule;


}
