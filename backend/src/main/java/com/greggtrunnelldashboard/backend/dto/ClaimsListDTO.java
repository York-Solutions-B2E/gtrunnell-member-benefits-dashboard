package com.greggtrunnelldashboard.backend.dto;

import com.greggtrunnelldashboard.backend.entities.Claim;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClaimsListDTO {

    private String claimNumber;
    private LocalDate serviceStartDate;
    private LocalDate serviceEndDate;
    private String providerName;
    private String status;
    private BigDecimal memberResponsibility;

    public static ClaimsListDTO from(Claim claim) {
        ClaimsListDTO dto = new ClaimsListDTO();
        dto.setClaimNumber(claim.getClaimNumber());
        dto.setServiceStartDate(claim.getServiceStartDate());
        dto.setServiceEndDate(claim.getServiceEndDate());
        // ✅ fix below
        dto.setProviderName(claim.getProvider().getProviderName());
        dto.setStatus(claim.getStatus().name());
        dto.setMemberResponsibility(claim.getTotalMemberResponsibility());
        return dto;
    }
}
