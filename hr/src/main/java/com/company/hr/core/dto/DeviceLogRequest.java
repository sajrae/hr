package com.company.hr.core.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class DeviceLogRequest {


    private Long employeeId;

    private LocalDateTime timeStamp;

    private String deviceId;

    private String eventType;
}
