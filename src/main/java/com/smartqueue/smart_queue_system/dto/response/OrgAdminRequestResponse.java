package com.smartqueue.smart_queue_system.dto.response;

import com.smartqueue.smart_queue_system.enums.OrgType;
import com.smartqueue.smart_queue_system.enums.RequestStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrgAdminRequestResponse {

    private Long id;

    private String userName;

    private String email;

    private String organizationName;

    private OrgType organizationType;

    private String designation;

    private String websiteUrl;

    private String contactNumber;

    private String reason;

    private RequestStatus status;

    private LocalDateTime requestedAt;

    private LocalDateTime reviewedAt;
}