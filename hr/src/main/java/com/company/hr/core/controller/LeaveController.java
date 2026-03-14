package com.company.hr.core.controller;

import com.company.hr.common.response.ApiResponse;
import com.company.hr.core.dto.LeaveRequestDto;
import com.company.hr.core.dto.LeaveResponseDto;
import com.company.hr.core.service.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/leave")
@RequiredArgsConstructor
public class LeaveController {

    private final LeaveService leaveService;

    @PostMapping("/request")
    public ResponseEntity<ApiResponse<LeaveResponseDto>> requestLeave(@RequestBody LeaveRequestDto dto) {

        try {
            LeaveResponseDto request = leaveService.requestLeave(dto);

            return ResponseEntity.ok(ApiResponse.success(request.getStatus().name(), request));

        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(ApiResponse.error(exception.getMessage()));
        }

    }

    @PostMapping("/approve/{id}")
    public ResponseEntity<ApiResponse<LeaveResponseDto>> approveLeave(@PathVariable Long id) {

        try {
            LeaveResponseDto request = leaveService.approveLeave(id);

            return ResponseEntity.ok(ApiResponse.success(request.getStatus().name(), request));

        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(ApiResponse.error(exception.getMessage()));
        }
    }

    @PostMapping("/reject/{id}")
    public ResponseEntity<ApiResponse<LeaveResponseDto>> rejectLeave(@PathVariable Long id) {

        try {
            LeaveResponseDto request = leaveService.rejectLeave(id);

            return ResponseEntity.ok(ApiResponse.success(request.getStatus().name(), request));

        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(ApiResponse.error(exception.getMessage()));
        }
    }

}
