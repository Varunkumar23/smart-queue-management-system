package com.smartqueue.smart_queue_system.repository;

import com.smartqueue.smart_queue_system.entity.OrgAdminRequest;
import com.smartqueue.smart_queue_system.entity.SmartUser;
import com.smartqueue.smart_queue_system.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrgAdminRequestRepository extends JpaRepository<OrgAdminRequest,Long> {
    boolean existsByUserAndStatus(
            SmartUser user,
            RequestStatus status
    );

    Optional<OrgAdminRequest> findTopByUserOrderByRequestedAtDesc(
            SmartUser user
    );

    List<OrgAdminRequest> findByStatus(
            RequestStatus status
    );
}
