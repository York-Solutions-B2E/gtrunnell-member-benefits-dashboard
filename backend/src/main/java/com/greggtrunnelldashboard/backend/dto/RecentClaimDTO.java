package com.greggtrunnelldashboard.backend.dto;

import com.greggtrunnelldashboard.backend.entities.Claim;
import com.greggtrunnelldashboard.backend.enums.ClaimStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RecentClaimDTO(
        String claimNumber,
        LocalDate serviceStartDate,
        LocalDate serviceEndDate,
        String providerName,
        ClaimStatus status,
        BigDecimal memberResponsibility
) {
    // Factory method to map from an entity
    public static RecentClaimDTO from(Claim claim) {
        return new RecentClaimDTO(
                claim.getClaimNumber(),
                claim.getServiceStartDate(),
                claim.getServiceEndDate(),
                claim.getProvider() != null ? claim.getProvider().getProviderName() : "Unknown Provider",
                claim.getStatus(),
                claim.getTotalMemberResponsibility()
        );
    }
}
