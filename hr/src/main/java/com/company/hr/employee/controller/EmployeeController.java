package com.company.hr.employee.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    @GetMapping("/getEmployee")
    public String getEmployee(){
        return "Admin";
    }
}
