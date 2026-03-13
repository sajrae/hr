package com.company.hr.attendance.controller;

import com.company.hr.attendance.dto.AttendanceResponse;
import com.company.hr.attendance.dto.DeviceLogRequest;
import com.company.hr.attendance.service.AttendanceService;
import com.company.hr.common.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping(value = "/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/device-logs")
    public ResponseEntity<Void> recieveLogs (@RequestBody DeviceLogRequest request){
        attendanceService.saveDeviceLog(request);
         return ResponseEntity.ok().build();
    }

    @PostMapping ("/{employeeId}/log")
    public ResponseEntity<ApiResponse<AttendanceResponse>> logAttendance(
            @PathVariable Long employeeId,
            @RequestParam String checkIn,
            @RequestParam String checkOut,
            @RequestParam String date ){

        try {
            LocalTime inTime = LocalTime.parse(checkIn);
            LocalTime outTime = LocalTime.parse(checkOut);
            LocalDate logDate = LocalDate.parse(date);

            AttendanceResponse response = attendanceService.processDailyLog(employeeId,inTime,outTime,logDate);

            return ResponseEntity.ok(ApiResponse.success("Attendance Processed",response));

        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ApiResponse.error(ex.getMessage()));
        }
    }

}
