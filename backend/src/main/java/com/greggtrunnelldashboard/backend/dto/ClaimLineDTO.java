package com.greggtrunnelldashboard.backend.dto;

import com.greggtrunnelldashboard.backend.entities.ClaimLine;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ClaimLineDTO {
    private Integer lineNumber;
    private String cptCode;
    private String description;
    private BigDecimal billedAmount;
    private BigDecimal allowedAmount;
    private BigDecimal deductibleApplied;
    private BigDecimal copayApplied;
    private BigDecimal coinsuranceApplied;
    private BigDecimal planPaid;
    private BigDecimal memberResponsibility;

    public static ClaimLineDTO from(ClaimLine line) {
        ClaimLineDTO dto = new ClaimLineDTO();
        dto.setLineNumber(line.getLineNumber());
        dto.setCptCode(line.getCptCode());
        dto.setDescription(line.getDescription());
        dto.setBilledAmount(line.getBilledAmount());
        dto.setAllowedAmount(line.getAllowedAmount());
        dto.setDeductibleApplied(line.getDeductibleApplied());
        dto.setCopayApplied(line.getCopayApplied());
        dto.setCoinsuranceApplied(line.getCoinsuranceApplied());
        dto.setPlanPaid(line.getPlanPaid());
        dto.setMemberResponsibility(line.getMemberResponsibility());
        return dto;
    }
}
