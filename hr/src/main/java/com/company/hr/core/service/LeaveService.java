package com.company.hr.core.service;

import com.company.hr.common.enums.LeaveTypes;
import com.company.hr.core.dto.LeaveRequestDto;
import com.company.hr.core.dto.LeaveResponseDto;
import com.company.hr.core.entity.Employee;
import com.company.hr.core.entity.EmployeeLeave;
import com.company.hr.core.entity.LeaveRequest;
import com.company.hr.core.entity.LeaveType;
import com.company.hr.core.repository.EmployeeLeaveRepository;
import com.company.hr.core.repository.EmployeeRepository;
import com.company.hr.core.repository.LeaveRequestRepository;
import com.company.hr.core.repository.LeaveTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class LeaveService {

    private final LeaveTypeRepository leaveTypeRepository;
    private final EmployeeLeaveRepository employeeLeaveRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeRepository employeeRepository;


    public LeaveResponseDto requestLeave(final LeaveRequestDto dto) {

        LeaveType leaveType = leaveTypeRepository
                .findById(dto.getLeaveTypeId())
                .orElseThrow();

        Employee employee = getEmployeeById(dto.getEmployeeId());

        int days = (int) ChronoUnit.DAYS.between(dto.getStartDate(), dto.getEndDate()) + 1;

        LeaveRequest request = new LeaveRequest();
        request.setEmployee(employee);
        request.setLeaveType(leaveType);
        request.setStartDate(dto.getStartDate());
        request.setEndDate(dto.getEndDate());
        request.setDays(days);
        request.setStatus(LeaveTypes.PENDING);

        leaveRequestRepository.save(request);

        return mapToResponse(request);
    }

    private LeaveResponseDto mapToResponse(final LeaveRequest request) {

        LeaveResponseDto response = new LeaveResponseDto();

        Employee employee = request.getEmployee();

        response.setEmployeeName(employee.getLastName() + "," + employee.getFirstName());
        response.setLeaveType(request.getLeaveType().getName());
        response.setStartDate(request.getStartDate());
        response.setEndDate(request.getEndDate());
        response.setStatus(request.getStatus());

        return response;
    }

    public LeaveResponseDto approveLeave(final Long id) {

        LeaveRequest request = leaveRequestRepository
                .findById(id)
                .orElseThrow();

        if (request.getStatus() != LeaveTypes.APPROVED) {

            request.setStatus(LeaveTypes.APPROVED);
            leaveRequestRepository.save(request);
            createEmployeeLeaveRecords(request);

            return mapToResponse(request);
        }

        throw new IllegalStateException("Request Already Approved");
    }

    public LeaveResponseDto rejectLeave(final Long id) {

        LeaveRequest request = leaveRequestRepository
                .findById(id)
                .orElseThrow();

        request.setStatus(LeaveTypes.REJECTED);

        leaveRequestRepository.save(request);

        return mapToResponse(request);
    }

    private void createEmployeeLeaveRecords(final LeaveRequest request) {

        LocalDate date = request.getStartDate();

        while (!date.isAfter(request.getEndDate())) {

            EmployeeLeave leave = new EmployeeLeave();

            leave.setEmployee(request.getEmployee());
            leave.setLeaveType(request.getLeaveType());
            leave.setLeaveDate(date);
            leave.setPaid(request.getLeaveType().getIsPaid());

            employeeLeaveRepository.save(leave);

            date = date.plusDays(1);
        }
    }

    private Employee getEmployeeById(final String code) {
        return employeeRepository.findByEmployeeCode(code).orElseThrow(() -> new RuntimeException("No Employee Found"));
    }

    public double calculateLeaveDeduction(final double monthlySalary, final int unpaidLeaveDays, final int workingDays){
        double dailyRate = monthlySalary / workingDays;
        return dailyRate * unpaidLeaveDays;
    }
}
