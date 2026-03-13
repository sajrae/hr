package com.company.hr.attendance.service;

import com.company.hr.attendance.dto.DeviceLogRequest;
import com.company.hr.attendance.entity.AttendanceLog;
import com.company.hr.attendance.repository.AttendanceLogRepository;
import org.springframework.stereotype.Service;

@Service
public class AttendanceService {

    private final AttendanceLogRepository attendanceLogRepository;

    public AttendanceService(AttendanceLogRepository attendanceLogRepository) {
        this.attendanceLogRepository = attendanceLogRepository;
    }

    public void saveDeviceLog(final DeviceLogRequest request) {
        AttendanceLog log = new AttendanceLog();
        log.setDeviceId(request.getDeviceId());
        log.setEmployeeId(request.getEmployeeId());
        log.setType(request.getEventType());
        log.setTimestamp(request.getTimeStamp());

        attendanceLogRepository.save(log);
    }
}
