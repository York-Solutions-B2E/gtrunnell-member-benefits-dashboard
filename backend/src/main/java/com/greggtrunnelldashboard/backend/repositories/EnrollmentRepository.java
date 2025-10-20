package com.greggtrunnelldashboard.backend.repositories;

import com.greggtrunnelldashboard.backend.entities.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {
//    List<Enrollment> findByMemberId(UUID memberId);
Enrollment findByMember_IdAndActiveTrue(UUID memberId);
}
