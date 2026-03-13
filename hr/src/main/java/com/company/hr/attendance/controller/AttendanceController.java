package com.company.hr.attendance.controller;

import com.company.hr.attendance.dto.DeviceLogRequest;
import com.company.hr.attendance.service.AttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
