package com.company.hr.core.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class EmployeeCompensationResponse {

    private BigDecimal basicSalary;
    private Double overTimeRate;
    private String schedule;
    private List<AllowanceResponse> allowanceResponses;
}
