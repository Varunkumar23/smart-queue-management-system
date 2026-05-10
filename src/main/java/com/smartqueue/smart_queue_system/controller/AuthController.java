package com.smartqueue.smart_queue_system.controller;

import com.smartqueue.smart_queue_system.dto.request.LoginRequest;
import com.smartqueue.smart_queue_system.dto.request.RegisterRequest;
import com.smartqueue.smart_queue_system.dto.response.AuthResponse;
import com.smartqueue.smart_queue_system.dto.response.RegisterResponse;
import com.smartqueue.smart_queue_system.payload.ApiResponse;
import com.smartqueue.smart_queue_system.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(@Valid @RequestBody RegisterRequest request){
        ApiResponse<RegisterResponse> response=service.register(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request){
        ApiResponse<AuthResponse> response=service.login(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }
}
