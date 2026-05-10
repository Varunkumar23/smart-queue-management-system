package com.smartqueue.smart_queue_system.dto.response;

import com.smartqueue.smart_queue_system.enums.Role;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegisterResponse {
    private String name;
    private String email;
    private Role role;
    private LocalDateTime createdAt;
}
