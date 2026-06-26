package com.smartqueue.smart_queue_system.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class QueueServiceRequest {

    @NotBlank(message = "Service name is required")
    @Size(max = 100, message = "Service name cannot exceed 100 characters")
    private String name;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @NotNull(message = "Estimated service time is required")
    @Min(value = 1, message = "Estimated service time must be at least 1 minute")
    private Integer estimatedServiceTimeInMinutes;
}