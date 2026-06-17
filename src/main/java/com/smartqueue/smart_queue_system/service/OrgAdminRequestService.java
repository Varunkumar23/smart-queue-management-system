package com.smartqueue.smart_queue_system.service;

import com.smartqueue.smart_queue_system.dto.request.OrgAdminRequestDto;
import com.smartqueue.smart_queue_system.dto.response.OrgAdminRequestResponse;
import com.smartqueue.smart_queue_system.payload.ApiResponse;

import java.util.List;

public interface OrgAdminRequestService {
    ApiResponse<OrgAdminRequestResponse> submitRequest(
            OrgAdminRequestDto request,
            String email
    );

    ApiResponse<OrgAdminRequestResponse> getMyRequest(
            String email
    );

    ApiResponse<List<OrgAdminRequestResponse>> getPendingRequests();

    ApiResponse<List<OrgAdminRequestResponse>> getApprovedRequests();

    ApiResponse<List<OrgAdminRequestResponse>> getRejectedRequests();


    ApiResponse<String> approveRequest(Long requestId);

    ApiResponse<String> rejectRequest(Long requestId);


}
