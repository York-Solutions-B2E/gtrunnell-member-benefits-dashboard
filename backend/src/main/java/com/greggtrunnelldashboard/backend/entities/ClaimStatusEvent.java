package com.greggtrunnelldashboard.backend.entities;

import com.greggtrunnelldashboard.backend.enums.ClaimStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.OffsetDateTime;
import java.util.UUID;

    @Entity
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Table(name = "claim_status_events")
    public class ClaimStatusEvent {

        @Id
        @UuidGenerator(style = UuidGenerator.Style.TIME)
        private UUID id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "claim_id", nullable = false)
        private Claim claim;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private ClaimStatus status;

        private OffsetDateTime occurredAt;

    }


