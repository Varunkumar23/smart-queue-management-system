package com.smartqueue.smart_queue_system.repository;

import com.smartqueue.smart_queue_system.entity.Organization;
import com.smartqueue.smart_queue_system.enums.OrgStatus;
import com.smartqueue.smart_queue_system.enums.OrgType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization,Long> {
    boolean existsByEmail(String email);

    Optional<Organization> findByEmail(String email);

    List<Organization> findByStatus(OrgStatus status);

    List<Organization> findByType(OrgType type);

    List<Organization> findByOwnerId(Long ownerId);
}
