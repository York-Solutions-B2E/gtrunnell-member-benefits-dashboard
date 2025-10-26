package com.greggtrunnelldashboard.backend.dto;

import com.greggtrunnelldashboard.backend.entities.*;
import com.greggtrunnelldashboard.backend.enums.AccumulatorType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Combines all information shown on the member dashboard:
 * - Member profile info
 * - Plan details
 * - Accumulator progress (deductible, out-of-pocket)
 * - Recent claims summary
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardDTO {

    // 👤 Member info
    private String fullName;
    private String email;

    // 🏥 Plan info
    private String planName;
    private String networkName;
    private Integer planYear;

    // 💰 Accumulators
    private BigDecimal deductibleUsed;
    private BigDecimal deductibleLimit;
    private BigDecimal oopUsed;
    private BigDecimal oopLimit;

    // 📄 Recent claims
    private List<RecentClaimDTO> recentClaims;

    /**
     * Factory method to build the full DashboardDTO from related entities.
     */
    public static DashboardDTO from(Member member, Enrollment enrollment,
                                    List<Accumulator> accumulators, List<Claim> claims) {

        DashboardDTO dto = new DashboardDTO();
        dto.setFullName(member.getFirstName() + " " + member.getLastName());
        dto.setEmail(member.getEmail());

        // Plan info
        Plan plan = enrollment.getPlan();
        dto.setPlanName(plan.getPlanName());
        dto.setNetworkName(plan.getNetworkName());
        dto.setPlanYear(plan.getPlanYear());

        // Accumulators
        dto.setDeductibleUsed(getAccumulatorValue(accumulators, AccumulatorType.DEDUCTIBLE, true));
        dto.setDeductibleLimit(getAccumulatorValue(accumulators, AccumulatorType.DEDUCTIBLE, false));
        dto.setOopUsed(getAccumulatorValue(accumulators, AccumulatorType.OOP_MAX, true));
        dto.setOopLimit(getAccumulatorValue(accumulators, AccumulatorType.OOP_MAX, false));

        // Recent claims (limit 5)
        dto.setRecentClaims(claims.stream()
                .map(RecentClaimDTO::from)
                .limit(5)
                .collect(Collectors.toList()));

        return dto;
    }

    private static BigDecimal getAccumulatorValue(List<Accumulator> accs, AccumulatorType type, boolean used) {
        return accs.stream()
                .filter(a -> a.getType() == type)
                .findFirst()
                .map(a -> used ? a.getUsedAmount() : a.getLimitAmount())
                .orElse(BigDecimal.ZERO);
    }
}
