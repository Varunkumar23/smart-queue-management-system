package com.smartqueue.smart_queue_system.service.ServiceImplementation;

import com.smartqueue.smart_queue_system.dto.request.LoginRequest;
import com.smartqueue.smart_queue_system.dto.request.RegisterRequest;
import com.smartqueue.smart_queue_system.dto.response.AuthResponse;
import com.smartqueue.smart_queue_system.dto.response.RegisterResponse;
import com.smartqueue.smart_queue_system.entity.SmartUser;
import com.smartqueue.smart_queue_system.enums.Role;
import com.smartqueue.smart_queue_system.exception.UserAlreadyExistsException;
import com.smartqueue.smart_queue_system.exception.UserNotFoundException;
import com.smartqueue.smart_queue_system.payload.ApiResponse;
import com.smartqueue.smart_queue_system.repository.SmartUserRepository;
import com.smartqueue.smart_queue_system.service.AuthService;
import com.smartqueue.smart_queue_system.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;


@Service
@RequiredArgsConstructor
public class AuthServiceImplementation implements AuthService {

    private final SmartUserRepository repository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ApiResponse<RegisterResponse> register(RegisterRequest request) {
        if(repository.existsByEmail(request.getEmail())){
            throw new UserAlreadyExistsException("User already exists with this email");
        }

        SmartUser user=modelMapper.map(request,SmartUser.class);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ROLE_USER);
        SmartUser savedUser=repository.save(user);
        RegisterResponse response=modelMapper.map(savedUser,RegisterResponse.class);

        return ApiResponse.<RegisterResponse>builder()
                .success(true)
                .message("User registered successfully")
                .data(response)
                .build();
    }

    @Override
    public ApiResponse<AuthResponse> login(LoginRequest request) {
        try{
            authenticationManager.authenticate(new
                    UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
        }catch(BadCredentialsException ex)
        {
        throw new BadCredentialsException("Invalid email or password");
        }
        SmartUser user=repository.findByEmail(request.getEmail()).orElseThrow(()->new UserNotFoundException("User not found"));
        String token=jwtUtil.generateToken(user.getEmail(),user.getRole().name());
        AuthResponse response=new AuthResponse(token,null,user.getRole());

        return ApiResponse.<AuthResponse>builder().success(true).message("Login successful").data(response).build();
    }
}
