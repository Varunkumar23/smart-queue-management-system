package com.smartqueue.smart_queue_system.controller;


import com.smartqueue.smart_queue_system.dto.request.OrganizationRequest;
import com.smartqueue.smart_queue_system.dto.response.OrganizationResponse;
import com.smartqueue.smart_queue_system.enums.OrgStatus;
import com.smartqueue.smart_queue_system.payload.ApiResponse;
import com.smartqueue.smart_queue_system.service.OrganizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/organizations")
public class OrganizationController {

    private final OrganizationService service;

    @PostMapping
    public ResponseEntity<ApiResponse<OrganizationResponse>> createOrganization(@Valid @RequestBody OrganizationRequest request, java.security.Principal principal){
        ApiResponse<OrganizationResponse> response=service.createOrganization(request, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrganizationResponse>> getOrganizationById(@PathVariable Long id){
        ApiResponse<OrganizationResponse> response=service.getOrganizationById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrganizationResponse>>> getAllOrganizations(){
        ApiResponse<List<OrganizationResponse>> response=service.getAllOrganizations();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<OrganizationResponse>> updateOrganization(@PathVariable Long id, @Valid @RequestBody OrganizationRequest request, java.security.Principal principal){
        ApiResponse<OrganizationResponse> response=service.updateOrganization(id, request, principal.getName());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<OrganizationResponse>> updateStatus(@PathVariable Long id, @RequestParam OrgStatus status, java.security.Principal principal){
        ApiResponse<OrganizationResponse> response=service.updateStatus(id, status, principal.getName());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/{id}/inactive")
    public ResponseEntity<ApiResponse<String>> setOrganizationInactive(@PathVariable Long id, java.security.Principal principal){
        ApiResponse<String> response=service.setOrganizationInactive(id, principal.getName());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}/hard")
    public ResponseEntity<ApiResponse<String>> hardDeleteOrganization(@PathVariable Long id, java.security.Principal principal){
        ApiResponse<String> response=service.hardDeleteOrganization(id, principal.getName());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
