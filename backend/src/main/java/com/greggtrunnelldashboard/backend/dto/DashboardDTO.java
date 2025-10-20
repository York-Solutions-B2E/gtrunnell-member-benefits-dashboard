package com.greggtrunnelldashboard.backend.dto;

import java.math.BigDecimal;
import java.util.List;

public record DashboardDTO(
        String memberName,
        String planName,
        String networkName,
        Integer planYear,
        BigDecimal deductibleUsed,
        BigDecimal deductibleLimit,
        BigDecimal oopUsed,
        BigDecimal oopLimit,
        List<RecentClaimDTO> recentClaims
) {}

