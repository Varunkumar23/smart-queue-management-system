package com.smartqueue.smart_queue_system.repository;

import com.smartqueue.smart_queue_system.entity.SmartUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SmartUserRepository extends JpaRepository<SmartUser,Long> {
     Optional<SmartUser> findByEmail(String email);

     boolean existsByEmail(String email);

}
