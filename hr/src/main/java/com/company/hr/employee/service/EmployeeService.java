package com.company.hr.employee.service;

import com.company.hr.employee.entity.Department;
import com.company.hr.employee.entity.Employee;
import com.company.hr.employee.entity.EmployeeSalary;
import com.company.hr.employee.entity.Position;
import com.company.hr.employee.repository.DepartmentRepository;
import com.company.hr.employee.repository.EmployeeRepository;
import com.company.hr.employee.repository.EmployeeSalaryRepository;
import com.company.hr.employee.repository.PositionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeSalaryRepository employeeSalaryRepository;
    private final PositionRepository positionRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeSalaryRepository employeeSalaryRepository, PositionRepository positionRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.employeeSalaryRepository = employeeSalaryRepository;
        this.positionRepository = positionRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<Employee> getEmployee(){
        return employeeRepository.findAll();
    }

    public List<EmployeeSalary> getSalaray(){
        return employeeSalaryRepository.findAll();
    }

    public List<Position> getPosition(){
        return positionRepository.findAll();
    }

    public List<Department> getAllDepartment(){
        return departmentRepository.findAll();
    }
}
