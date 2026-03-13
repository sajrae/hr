package com.company.hr.employee.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class EmployeeCompensationResponse {

    private Double basicSalary;
    private Double overTimeRate;
    private String schedule;
    private List<AllowanceResponse> allowanceResponses;
}
