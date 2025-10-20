package com.greggtrunnelldashboard.backend.repositories;

import com.greggtrunnelldashboard.backend.entities.Claim;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClaimRepository extends JpaRepository<Claim, UUID> {
    Optional<Claim> findByClaimNumber(String claimNumber);
    List<Claim> findByMemberId(UUID memberId);
}
