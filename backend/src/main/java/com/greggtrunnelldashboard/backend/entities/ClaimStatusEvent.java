package com.greggtrunnelldashboard.backend.entities;

import com.greggtrunnelldashboard.backend.enums.ClaimStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.OffsetDateTime;
import java.util.UUID;

    @Entity
    @Table(name = "claim_status_events")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class ClaimStatusEvent {

        @Id
        @UuidGenerator(style = UuidGenerator.Style.TIME)
        private UUID id;

        @ManyToOne
        @JoinColumn(name = "claim_id", nullable = false)
        private Claim claim;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private ClaimStatus status;

        private OffsetDateTime occurredAt;

        private String note;

        @PrePersist
        public void setOccurredAtIfNull() {
            if (this.occurredAt == null) {
                this.occurredAt = OffsetDateTime.now();
            }
        }
    }


