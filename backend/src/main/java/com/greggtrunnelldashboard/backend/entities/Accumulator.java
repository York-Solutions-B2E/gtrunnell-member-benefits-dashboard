package com.greggtrunnelldashboard.backend.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.greggtrunnelldashboard.backend.enums.AccumulatorType;
import com.greggtrunnelldashboard.backend.enums.NetworkTier;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accumulators")
public class Accumulator {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;

//a lot of accumulator(many) can be in one enrollment
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "enrollment_id", nullable = false)
    private Enrollment enrollment;

    @Enumerated(EnumType.STRING)
    private AccumulatorType type;

    @Enumerated(EnumType.STRING)
    private NetworkTier tier;

    @Column(precision = 10, scale = 2)
    private BigDecimal limitAmount;

    @Column(precision = 10, scale = 2)
    private BigDecimal usedAmount;
}
