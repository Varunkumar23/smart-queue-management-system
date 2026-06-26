package com.smartqueue.smart_queue_system.controller;

import com.smartqueue.smart_queue_system.dto.request.OrgAdminRequestDto;
import com.smartqueue.smart_queue_system.dto.response.OrgAdminRequestResponse;
import com.smartqueue.smart_queue_system.payload.ApiResponse;
import com.smartqueue.smart_queue_system.service.OrgAdminRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.constraintvalidators.bv.time.past.AbstractPastInstantBasedValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/org-admin-requests")
public class OrgAdminRequestController {

    private final OrgAdminRequestService service;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<ApiResponse<OrgAdminRequestResponse>> submitRequest(@Valid @RequestBody OrgAdminRequestDto request, Principal principal){
        ApiResponse<OrgAdminRequestResponse> response=service.submitRequest(request,principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/me")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<ApiResponse<OrgAdminRequestResponse>> getMyRequest(Principal principal){
        ApiResponse<OrgAdminRequestResponse> response=service.getMyRequest(principal.getName());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/pending")
    @PreAuthorize("hasAuthority('ROLE_SUPERADMIN')")
    public ResponseEntity<ApiResponse<List<OrgAdminRequestResponse>>> getPendingRequests(){
        ApiResponse<List<OrgAdminRequestResponse>> response=service.getPendingRequests();
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @GetMapping("/approved")
    @PreAuthorize("hasAuthority('ROLE_SUPERADMIN')")
    public ResponseEntity<ApiResponse<List<OrgAdminRequestResponse>>> getApprovedRequests(){
        ApiResponse<List<OrgAdminRequestResponse>> response=service.getApprovedRequests();
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @GetMapping("/rejected")
    @PreAuthorize("hasAnyAuthority('ROLE_SUPERADMIN')")
    public ResponseEntity<ApiResponse<List<OrgAdminRequestResponse>>> getRejectedRequests(){
        ApiResponse<List<OrgAdminRequestResponse>> response=service.getRejectedRequests();
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @PatchMapping("/{id}/approve")
    @PreAuthorize("hasAuthority('ROLE_SUPERADMIN')")
    public ResponseEntity<ApiResponse<String>> approveRequest(@PathVariable Long id){
        ApiResponse<String> response=service.approveRequest(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @PatchMapping("/{id}/reject")
    @PreAuthorize("hasAuthority('ROLE_SUPERADMIN')")
    public ResponseEntity<ApiResponse<String>> rejectRequest(@PathVariable Long id){
        ApiResponse<String> response=service.rejectRequest(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }





    }
