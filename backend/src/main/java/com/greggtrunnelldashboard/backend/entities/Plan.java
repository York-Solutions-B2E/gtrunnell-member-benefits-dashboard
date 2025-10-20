package com.greggtrunnelldashboard.backend.entities;

import com.greggtrunnelldashboard.backend.enums.PlanType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="plans")
public class Plan {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;

    private String planName;

    @Enumerated(EnumType.STRING)
    private PlanType planType;

    private String networkName;

    private Integer planYear;

}
