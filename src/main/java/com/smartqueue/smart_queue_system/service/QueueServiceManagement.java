package com.smartqueue.smart_queue_system.service;

import com.smartqueue.smart_queue_system.dto.request.QueueServiceRequest;
import com.smartqueue.smart_queue_system.dto.response.QueueServiceResponse;
import com.smartqueue.smart_queue_system.payload.ApiResponse;

import java.util.List;

public interface QueueServiceManagement {

    ApiResponse<QueueServiceResponse> createService(
            QueueServiceRequest request,
            String ownerEmail
    );

    ApiResponse<QueueServiceResponse> getServiceById(
            Long id,
            String ownerEmail
    );

    ApiResponse<List<QueueServiceResponse>> getAllServices(
            String ownerEmail
    );

    ApiResponse<QueueServiceResponse> updateService(
            Long id,
            QueueServiceRequest request,
            String ownerEmail
    );

    ApiResponse<String> deleteService(
            Long id,
            String ownerEmail
    );
}