package com.greggtrunnelldashboard.backend.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.greggtrunnelldashboard.backend.enums.ClaimStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "claims")
public class Claim {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;

    private String claimNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

    private LocalDate serviceStartDate;
    private LocalDate serviceEndDate;
    private LocalDate receivedDate;

    @Enumerated(EnumType.STRING)
    private ClaimStatus status;

    @Column(precision = 10, scale = 2)
    private BigDecimal totalBilled;

    @Column(precision = 10, scale = 2)
    private BigDecimal totalAllowed;

    @Column(precision = 10, scale = 2)
    private BigDecimal totalPlanPaid;

    @Column(precision = 10, scale = 2)
    private BigDecimal totalMemberResponsibility;

    @OneToMany(mappedBy = "claim", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ClaimLine> lines;

    @OneToMany(mappedBy = "claim", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ClaimStatusEvent> statusHistory;

    private OffsetDateTime updatedAt;

    @PrePersist
    @PreUpdate
    public void updateTimestamp() {
        this.updatedAt = OffsetDateTime.now();
    }
    public void addStatusEvent(ClaimStatusEvent event) {
        statusHistory.add(event);
        event.setClaim(this);
    }

    public void addLine(ClaimLine line) {
        lines.add(line);
        line.setClaim(this);
    }

}
