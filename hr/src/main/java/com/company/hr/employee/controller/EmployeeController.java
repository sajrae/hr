package com.company.hr.employee.controller;

import com.company.hr.common.response.ApiResponse;
import com.company.hr.employee.dto.AssignSalaryRequest;
import com.company.hr.employee.dto.EmployeeCompensationResponse;
import com.company.hr.employee.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/{code}/salary")
    public ResponseEntity<ApiResponse<String>> assignSalary(
            @PathVariable String code,
            @Valid @RequestBody AssignSalaryRequest salaryRequest) {
        try {
            employeeService.assignSalary(code, salaryRequest);
            return ResponseEntity.ok(ApiResponse.success("Salary Assigned", null));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ApiResponse.error(ex.getMessage()));
        }


    }

    @GetMapping("/{code}/compensation")
    public ResponseEntity<ApiResponse<EmployeeCompensationResponse>> getCompensation(
            @PathVariable String code) {
        try {
            return ResponseEntity.ok(ApiResponse.success("Employee Fetched", employeeService.getCompensation(code)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
