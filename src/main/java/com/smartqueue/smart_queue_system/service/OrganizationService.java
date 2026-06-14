package com.smartqueue.smart_queue_system.service;

import com.smartqueue.smart_queue_system.dto.request.OrganizationRequest;
import com.smartqueue.smart_queue_system.dto.response.OrganizationResponse;
import com.smartqueue.smart_queue_system.enums.OrgStatus;
import com.smartqueue.smart_queue_system.payload.ApiResponse;

import java.util.List;

public interface OrganizationService {

    ApiResponse<OrganizationResponse> createOrganization(OrganizationRequest request, String ownerEmail);

    ApiResponse<OrganizationResponse> getOrganizationById(Long id);

    ApiResponse<List<OrganizationResponse>> getAllOrganizations();

    ApiResponse<OrganizationResponse> updateOrganization(Long id, OrganizationRequest request, String ownerEmail);

    ApiResponse<String> deleteOrganization(Long id, String requestorEmail);


}
