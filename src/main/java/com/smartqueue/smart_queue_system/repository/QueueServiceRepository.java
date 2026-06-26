package com.smartqueue.smart_queue_system.repository;

import com.smartqueue.smart_queue_system.entity.Organization;
import com.smartqueue.smart_queue_system.entity.QueueService;
import com.smartqueue.smart_queue_system.enums.ServiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QueueServiceRepository extends JpaRepository<QueueService,Long> {
    boolean existsByOrganizationAndName(
            Organization organization,
            String name
    );

    List<QueueService> findByOrganizationAndStatus(
            Organization organization,
            ServiceStatus status
    );

    Optional<QueueService> findByIdAndOrganization(
            Long id,
            Organization organization
    );
}
