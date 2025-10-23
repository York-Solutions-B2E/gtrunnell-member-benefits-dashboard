package com.greggtrunnelldashboard.backend.repositories;

import com.greggtrunnelldashboard.backend.entities.Enrollment;
import com.greggtrunnelldashboard.backend.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {
    Enrollment findByMember_IdAndActiveTrue(UUID memberId);
    Optional<Enrollment> findFirstByMemberAndActiveTrue(Member member);

}
