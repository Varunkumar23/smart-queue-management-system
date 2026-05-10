package com.smartqueue.smart_queue_system.dto.response;

import com.smartqueue.smart_queue_system.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private String refreshToken;
    private Role role;

}
