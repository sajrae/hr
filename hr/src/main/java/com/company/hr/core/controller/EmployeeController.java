package com.company.hr.core.controller;

import com.company.hr.common.response.ApiResponse;
import com.company.hr.core.dto.AssignSalaryRequest;
import com.company.hr.core.dto.EmployeeCompensationResponse;
import com.company.hr.core.dto.EmployeeRegistrationRequest;
import com.company.hr.core.entity.Employee;
import com.company.hr.core.mapper.EmployeeMapper;
import com.company.hr.core.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

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

    @PostMapping(value = "/register")
    public ResponseEntity<ApiResponse<String>> addEmployee(@Valid @RequestBody EmployeeRegistrationRequest request) {
        try {
            Employee employee = employeeMapper.toEntity(request);
            if (!employeeService.duplicateEmployee(employee)) {
                employeeService.saveEmployee(employee);
            }
            return ResponseEntity.ok(ApiResponse.success("Employee registration", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
