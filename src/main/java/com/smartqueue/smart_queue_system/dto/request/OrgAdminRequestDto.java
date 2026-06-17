package com.smartqueue.smart_queue_system.dto.request;

import com.smartqueue.smart_queue_system.enums.OrgType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrgAdminRequestDto {

    @NotBlank(message = "Organization name is required")
    private String organizationName;

    @NotNull(message = "Organization type is required")
    private OrgType organizationType;

    @NotBlank(message = "Designation is required")
    private String designation;

    private String websiteUrl;

    @NotBlank(message = "Contact number is required")
    private String contactNumber;

    @NotBlank(message = "Reason is required")
    private String reason;
}