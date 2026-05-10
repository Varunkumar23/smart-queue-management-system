package com.smartqueue.smart_queue_system.dto.response;

import com.smartqueue.smart_queue_system.enums.Role;
import lombok.Data;

@Data
public class RegisterResponse {
    private String email;
    private Role role;
}
