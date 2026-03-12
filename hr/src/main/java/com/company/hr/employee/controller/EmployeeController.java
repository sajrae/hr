package com.company.hr.employee.controller;

import com.company.hr.employee.entity.Department;
import com.company.hr.employee.entity.Employee;
import com.company.hr.employee.entity.EmployeeSalary;
import com.company.hr.employee.entity.Position;
import com.company.hr.employee.service.EmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employee")
    public List<Employee> getEmployee(){
        return employeeService.getEmployee();
    }

    @GetMapping("/salary")
    public List<EmployeeSalary> getAllSalary(){
        return employeeService.getSalaray();
    }

    @GetMapping("/department")
    public List<Department> getAllDepartment(){
        return employeeService.getAllDepartment();
    }

    @GetMapping("/position")
    public List<Position> getAllPosition(){
        return employeeService.getPosition();
    }
}
