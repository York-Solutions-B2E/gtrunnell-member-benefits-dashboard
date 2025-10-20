package com.greggtrunnelldashboard.backend.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "claim_lines")
public class ClaimLine {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "claim_id", nullable = false)
    private Claim claim;

    @Column(nullable = false)
    private Integer lineNumber;  // sequential number (1..n within a claim)

    private String cptCode;      // procedure code (e.g. "99213")
    private String description;

    @Column(precision = 10, scale = 2)
    private BigDecimal billedAmount;

    @Column(precision = 10, scale = 2)
    private BigDecimal allowedAmount;

    @Column(precision = 10, scale = 2)
    private BigDecimal deductibleApplied;

    @Column(precision = 10, scale = 2)
    private BigDecimal copayApplied;

    @Column(precision = 10, scale = 2)
    private BigDecimal coinsuranceApplied;

    @Column(precision = 10, scale = 2)
    private BigDecimal planPaid;

    @Column(precision = 10, scale = 2)
    private BigDecimal memberResponsibility;
}
