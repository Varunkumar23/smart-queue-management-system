package com.smartqueue.smart_queue_system.entity;

import com.smartqueue.smart_queue_system.enums.OrgType;
import com.smartqueue.smart_queue_system.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "org_admin_requests")
public class OrgAdminRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private SmartUser user;

    @Column(nullable = false)
    private String organizationName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrgType organizationType;

    @Column(nullable = false)
    private String designation;

    private String websiteUrl;

    @Column(nullable = false)
    private String contactNumber;

    @Column(nullable = false, length = 1000)
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status;

    private LocalDateTime requestedAt;

    private LocalDateTime reviewedAt;
}