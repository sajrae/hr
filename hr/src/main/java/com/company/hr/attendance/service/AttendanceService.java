package com.company.hr.attendance.service;

import com.company.hr.attendance.dto.AttendanceResponse;
import com.company.hr.attendance.dto.DeviceLogRequest;
import com.company.hr.attendance.entity.Attendance;
import com.company.hr.attendance.entity.AttendanceLog;
import com.company.hr.attendance.repository.AttendanceLogRepository;
import com.company.hr.attendance.repository.AttendanceRepository;
import com.company.hr.common.SystemSettingService;
import com.company.hr.common.constants.GlobalConstants;
import com.company.hr.employee.entity.Employee;
import com.company.hr.employee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class AttendanceService {

    private final AttendanceLogRepository attendanceLogRepository;
    private final AttendanceRepository repository;
    private final EmployeeRepository employeeRepository;
    private final SystemSettingService settingService;

    public AttendanceService(AttendanceLogRepository attendanceLogRepository, AttendanceRepository repository, EmployeeRepository employeeRepository, SystemSettingService settingService) {
        this.attendanceLogRepository = attendanceLogRepository;
        this.repository = repository;
        this.employeeRepository = employeeRepository;
        this.settingService = settingService;
    }

    public void saveDeviceLog(final DeviceLogRequest request) {
        AttendanceLog log = new AttendanceLog();
        log.setDeviceId(request.getDeviceId());
        log.setEmployeeId(request.getEmployeeId());
        log.setType(request.getEventType());
        log.setTimestamp(request.getTimeStamp());

        attendanceLogRepository.save(log);
    }

    public AttendanceResponse processDailyLog(Long id, LocalTime checkIn, LocalTime checkOut, LocalDate date) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Attendance attendance = repository.findByEmployeeIdAndDate(id, date)
                .orElse(new Attendance());


        LocalTime workStart = settingService.getTime(GlobalConstants.WORK_START_TIME);
        LocalTime overtimeStart = settingService.getTime(GlobalConstants.OVER_TIME_START);

        attendance.setEmployee(employee);
        attendance.setDate(date);
        attendance.setTimeIn(checkIn);
        attendance.setTimeOut(checkOut);

        // Calculate hours worked
        double hoursWorked = Duration.between(checkIn, checkOut).toMinutes() / 60.0;
        attendance.setWorkedHours(hoursWorked);

        // Calculate late minutes (after 09:00) change this to configurable in the future
        int lateMinutes = 0;
        if (checkIn.isAfter(workStart)) {
            lateMinutes = (int) Duration.between(workStart, checkIn).toMinutes();
        }
        attendance.setLateMinutes(lateMinutes);


        // Overtime after 18:00
        double overtimeHours = 0;
        if (checkOut.isAfter(overtimeStart)) {
            overtimeHours = Duration.between(overtimeStart, checkOut).toMinutes() / 60.0;
        }
        attendance.setOvertimeHours(overtimeHours);

        //Minimum work hours (8 hours)
        attendance.setUnderTime(hoursWorked < settingService.getInt(GlobalConstants.MINIMUM_WORK_HOURS));

        repository.save(attendance);

        return mapToResponse(attendance);
    }

    private AttendanceResponse mapToResponse(final Attendance attendance) {
        AttendanceResponse response = new AttendanceResponse();
        response.setEmployeeCode(attendance.getEmployee().getEmployeeCode());
        response.setHoursWorked(attendance.getWorkedHours());
        response.setLateMinutes(attendance.getLateMinutes());
        response.setOverTimeHours(attendance.getOvertimeHours());
        response.setUnderTime(attendance.isUnderTime());
        response.setDate(attendance.getDate());
        response.setTimeIn(attendance.getTimeIn());
        response.setTimeOut(attendance.getTimeOut());

        return response;
    }
}
