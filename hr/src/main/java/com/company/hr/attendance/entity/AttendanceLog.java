package com.company.hr.attendance.entity;

import com.company.hr.common.BaseEntity;
import com.company.hr.device.entity.Device;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(indexes = {
        @Index(name = "idx_attendance_employee", columnList = "employee_id"),
        @Index(name = "idx_attendance_timestamp", columnList = "timestamp"),
        @Index(name = "idx_attendance_employee_timestamp", columnList = "employee_id,timestamp")
})
public class AttendanceLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Long employeeId;

    @ManyToOne
    @JoinColumn(name = "biometric_device_id")
    private Device device;

    private LocalDateTime timestamp;

    private String deviceId;

    private String type;
}
