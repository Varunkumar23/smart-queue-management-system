package com.smartqueue.smart_queue_system.service.ServiceImplementation;

import com.smartqueue.smart_queue_system.dto.request.OrgAdminRequestDto;
import com.smartqueue.smart_queue_system.dto.response.OrgAdminRequestResponse;
import com.smartqueue.smart_queue_system.entity.OrgAdminRequest;
import com.smartqueue.smart_queue_system.entity.SmartUser;
import com.smartqueue.smart_queue_system.enums.RequestStatus;
import com.smartqueue.smart_queue_system.enums.Role;
import com.smartqueue.smart_queue_system.exception.*;
import com.smartqueue.smart_queue_system.payload.ApiResponse;
import com.smartqueue.smart_queue_system.repository.OrgAdminRequestRepository;
import com.smartqueue.smart_queue_system.repository.SmartUserRepository;
import com.smartqueue.smart_queue_system.service.OrgAdminRequestService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrgAdminRequestServiceImplementation
        implements OrgAdminRequestService {

    private final OrgAdminRequestRepository requestRepository;
    private final SmartUserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public ApiResponse<OrgAdminRequestResponse> submitRequest(
            OrgAdminRequestDto request,
            String email) {

        SmartUser user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        if (user.getRole() == Role.ROLE_ADMIN ||
                user.getRole() == Role.ROLE_SUPERADMIN) {

            throw new InvalidRoleRequestException(
                    "You already have admin privileges");
        }

        boolean pendingExists =
                requestRepository.existsByUserAndStatus(
                        user,
                        RequestStatus.PENDING
                );

        if (pendingExists) {
            throw new DuplicateOrgAdminRequestException(
                    "You already have a pending request");
        }

        OrgAdminRequest adminRequest =
                modelMapper.map(
                        request,
                        OrgAdminRequest.class
                );

        adminRequest.setUser(user);
        adminRequest.setStatus(RequestStatus.PENDING);
        adminRequest.setRequestedAt(LocalDateTime.now());

        OrgAdminRequest saved =
                requestRepository.save(adminRequest);

        OrgAdminRequestResponse response =
                mapToResponse(saved);

        return ApiResponse.<OrgAdminRequestResponse>builder()
                .success(true)
                .message("Request submitted successfully")
                .data(response)
                .build();
    }

    @Override
    public ApiResponse<OrgAdminRequestResponse> getMyRequest(
            String email) {

        SmartUser user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        OrgAdminRequest request =
                requestRepository
                        .findTopByUserOrderByRequestedAtDesc(user)
                        .orElseThrow(() ->
                                new OrgAdminRequestNotFoundException(
                                        "No request found"));

        return ApiResponse.<OrgAdminRequestResponse>builder()
                .success(true)
                .message("Request fetched successfully")
                .data(mapToResponse(request))
                .build();
    }

    @Override
    public ApiResponse<List<OrgAdminRequestResponse>>
    getPendingRequests() {

        List<OrgAdminRequestResponse> responses =
                requestRepository.findByStatus(
                                RequestStatus.PENDING)
                        .stream()
                        .map(this::mapToResponse)
                        .toList();

        return ApiResponse.<List<OrgAdminRequestResponse>>builder()
                .success(true)
                .message("Pending requests fetched successfully")
                .data(responses)
                .build();
    }

    @Override
    public ApiResponse<List<OrgAdminRequestResponse>> getApprovedRequests() {
        List<OrgAdminRequestResponse> responses =
                requestRepository.findByStatus(
                                RequestStatus.APPROVED)
                        .stream()
                        .map(this::mapToResponse)
                        .toList();

        return ApiResponse.<List<OrgAdminRequestResponse>>builder()
                .success(true)
                .message("Approved requests fetched successfully")
                .data(responses)
                .build();
    }

    @Override
    public ApiResponse<List<OrgAdminRequestResponse>> getRejectedRequests() {
        List<OrgAdminRequestResponse> responses =
                requestRepository.findByStatus(
                                RequestStatus.REJECTED)
                        .stream()
                        .map(this::mapToResponse)
                        .toList();

        return ApiResponse.<List<OrgAdminRequestResponse>>builder()
                .success(true)
                .message("Rejected requests fetched successfully")
                .data(responses)
                .build();
    }

    @Override
    @Transactional
    public ApiResponse<String> approveRequest(Long requestId) {

        OrgAdminRequest request =
                requestRepository.findById(requestId)
                        .orElseThrow(() ->
                                new OrgAdminRequestNotFoundException(
                                        "Request not found"));
        if(request.getStatus() != RequestStatus.PENDING){
            throw new IllegalStateException(
                    "Request has already been processed"
            );
        }

        request.setStatus(RequestStatus.APPROVED);
        request.setReviewedAt(LocalDateTime.now());

        SmartUser user = request.getUser();
        user.setRole(Role.ROLE_ADMIN);

        userRepository.save(user);
        requestRepository.save(request);

        return ApiResponse.<String>builder()
                .success(true)
                .message("Request approved successfully")
                .build();
    }

    @Override
    @Transactional
    public ApiResponse<String> rejectRequest(Long requestId) {

        OrgAdminRequest request =
                requestRepository.findById(requestId)
                        .orElseThrow(() ->
                                new OrgAdminRequestNotFoundException(
                                        "Request not found"));

        if(request.getStatus() != RequestStatus.PENDING){
            throw new IllegalStateException(
                    "Request has already been processed"
            );
        }

        request.setStatus(RequestStatus.REJECTED);
        request.setReviewedAt(LocalDateTime.now());

        requestRepository.save(request);

        return ApiResponse.<String>builder()
                .success(true)
                .message("Request rejected successfully")
                .build();
    }

    private OrgAdminRequestResponse mapToResponse(
            OrgAdminRequest request) {

        OrgAdminRequestResponse response =
                modelMapper.map(
                        request,
                        OrgAdminRequestResponse.class);

        response.setUserName(
                request.getUser().getName());

        response.setEmail(
                request.getUser().getEmail());

        return response;
    }
}