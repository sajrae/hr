package com.company.hr.device.entity;

import com.company.hr.attendance.entity.AttendanceLog;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private String location;

    private String ipAddress;

    @OneToMany(mappedBy = "device")
    private List<AttendanceLog> attendanceLogs;
}
