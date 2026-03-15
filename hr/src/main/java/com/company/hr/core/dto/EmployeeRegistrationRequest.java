package com.company.hr.core.dto;

import lombok.Data;

@Data
public class EmployeeRegistrationRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String status;
    private String phone;
    private String hireDate;
    private Long payrollScheduleId;
    private Long departmentId;
    private Long positionId;

}
