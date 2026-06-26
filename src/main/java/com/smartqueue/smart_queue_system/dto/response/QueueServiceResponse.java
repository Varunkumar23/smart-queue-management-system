package com.smartqueue.smart_queue_system.dto.response;

import com.smartqueue.smart_queue_system.enums.ServiceStatus;
import lombok.Data;

@Data
public class QueueServiceResponse {

    private Long id;

    private String name;

    private String description;

    private Integer estimatedServiceTimeInMinutes;

    private ServiceStatus status;

    private String organizationName;
}