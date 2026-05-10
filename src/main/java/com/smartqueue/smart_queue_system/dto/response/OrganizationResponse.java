package com.smartqueue.smart_queue_system.dto.response;

import com.smartqueue.smart_queue_system.enums.OrgStatus;
import com.smartqueue.smart_queue_system.enums.OrgType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrganizationResponse {

    private Long id;
    private String name;
    private OrgType type;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private OrgStatus status;
    private String ownerName;
    private LocalDateTime createdAt;
}