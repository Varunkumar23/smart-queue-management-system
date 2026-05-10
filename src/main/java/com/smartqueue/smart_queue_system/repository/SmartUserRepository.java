package com.smartqueue.smart_queue_system.repository;

import com.smartqueue.smart_queue_system.entity.SmartUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SmartUserRepository extends JpaRepository<SmartUser,Long> {
     Optional<SmartUser> findByEmail(String email);

     boolean existsByEmail(String email);

}
