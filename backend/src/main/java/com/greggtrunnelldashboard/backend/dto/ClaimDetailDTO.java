package com.greggtrunnelldashboard.backend.dto;

import com.greggtrunnelldashboard.backend.entities.Claim;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ClaimDetailDTO {
    private String claimNumber;
    private String providerName;
    private LocalDate serviceStartDate;
    private LocalDate serviceEndDate;
    private String status;
    private BigDecimal totalBilled;
    private BigDecimal totalAllowed;
    private BigDecimal totalPlanPaid;
    private BigDecimal totalMemberResponsibility;
    private List<ClaimLineDTO> lines;
    private List<ClaimStatusEventDTO> statusHistory;

    public static ClaimDetailDTO from(Claim claim) {
        ClaimDetailDTO dto = new ClaimDetailDTO();
        dto.setClaimNumber(claim.getClaimNumber());
        dto.setProviderName(claim.getProvider().getProviderName());
        dto.setServiceStartDate(claim.getServiceStartDate());
        dto.setServiceEndDate(claim.getServiceEndDate());
        dto.setStatus(claim.getStatus().name());
        dto.setTotalBilled(claim.getTotalBilled());
        dto.setTotalAllowed(claim.getTotalAllowed());
        dto.setTotalPlanPaid(claim.getTotalPlanPaid());
        dto.setTotalMemberResponsibility(claim.getTotalMemberResponsibility());

        dto.setLines(claim.getLines() != null
                ? claim.getLines().stream().map(ClaimLineDTO::from).collect(Collectors.toList())
                : List.of());

        dto.setStatusHistory(claim.getStatusHistory() != null
                ? claim.getStatusHistory().stream().map(ClaimStatusEventDTO::from).collect(Collectors.toList())
                : List.of());

        return dto;
    }
}
