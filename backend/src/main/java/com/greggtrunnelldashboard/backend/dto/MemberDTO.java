package com.greggtrunnelldashboard.backend.dto;

import com.greggtrunnelldashboard.backend.entities.*;
import com.greggtrunnelldashboard.backend.enums.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class MemberDTO {

    private String fullName;
    private String email;

    private String planName;
    private String networkName;
    private Integer planYear;

    private BigDecimal deductibleUsed;
    private BigDecimal deductibleLimit;
    private BigDecimal oopUsed;
    private BigDecimal oopLimit;

    private List<RecentClaimDTO> recentClaims;

    public MemberDTO(Member member, Enrollment enrollment,
                     List<Accumulator> accumulators, List<Claim> claims) {

        this.fullName = member.getFirstName() + " " + member.getLastName();
        this.email = member.getEmail();

        this.planName = enrollment.getPlan().getPlanName();
        this.networkName = enrollment.getPlan().getNetworkName();
        this.planYear = enrollment.getPlan().getPlanYear();

        this.deductibleUsed = getAccumulatorValue(accumulators, AccumulatorType.DEDUCTIBLE, true);
        this.deductibleLimit = getAccumulatorValue(accumulators, AccumulatorType.DEDUCTIBLE, false);
        this.oopUsed = getAccumulatorValue(accumulators, AccumulatorType.OOP_MAX, true);
        this.oopLimit = getAccumulatorValue(accumulators, AccumulatorType.OOP_MAX, false);

        this.recentClaims = claims.stream()
                .map(RecentClaimDTO::from)
                .limit(5)
                .collect(Collectors.toList());
    }
    
    private BigDecimal getAccumulatorValue(List<Accumulator> accs, AccumulatorType type, boolean used) {
        return accs.stream()
                .filter(a -> a.getType() == type)
                .findFirst()
                .map(a -> used ? a.getUsedAmount() : a.getLimitAmount())
                .orElse(BigDecimal.ZERO);
    }
}
