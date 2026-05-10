package com.smartqueue.smart_queue_system.service;

import com.smartqueue.smart_queue_system.dto.request.LoginRequest;
import com.smartqueue.smart_queue_system.dto.request.RegisterRequest;
import com.smartqueue.smart_queue_system.dto.response.AuthResponse;
import com.smartqueue.smart_queue_system.dto.response.RegisterResponse;
import com.smartqueue.smart_queue_system.payload.ApiResponse;


public interface AuthService {
    ApiResponse<RegisterResponse> register(RegisterRequest request);

    ApiResponse<AuthResponse> login(LoginRequest request);
}
