package com.greggtrunnelldashboard.backend.repositories;

import com.greggtrunnelldashboard.backend.entities.Accumulator;
import com.greggtrunnelldashboard.backend.entities.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AccumulatorRepository extends JpaRepository<Accumulator, UUID> {
    List<Accumulator> findByEnrollmentId(UUID enrollmentId);
}
