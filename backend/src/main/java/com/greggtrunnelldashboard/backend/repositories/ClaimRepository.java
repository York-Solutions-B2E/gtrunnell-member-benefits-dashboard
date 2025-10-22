package com.greggtrunnelldashboard.backend.repositories;

import com.greggtrunnelldashboard.backend.entities.Claim;
import com.greggtrunnelldashboard.backend.enums.ClaimStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClaimRepository extends JpaRepository<Claim, UUID> {

    @Query("""
    SELECT c FROM Claim c
    WHERE c.member.id = :memberId
      AND (:status IS NULL OR c.status = :status)
      AND (:provider IS NULL OR
           CAST(LOWER(c.provider.providerName) AS string) LIKE
           LOWER(CAST(CONCAT('%', :provider, '%') AS string)))
      AND (:claimNumber IS NULL OR c.claimNumber = :claimNumber)
    """)

    Page<Claim> findFiltered(
            @Param("memberId") UUID memberId,
            @Param("status") ClaimStatus status,
            @Param("provider") String provider,
            @Param("claimNumber") String claimNumber,
            Pageable pageable
    );
    List<Claim> findTop5ByMemberIdOrderByReceivedDateDesc(UUID memberId);
    Optional<Claim> findByClaimNumber(String claimNumber);
}
