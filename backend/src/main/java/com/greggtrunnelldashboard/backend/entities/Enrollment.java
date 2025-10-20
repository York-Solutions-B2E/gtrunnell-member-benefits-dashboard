package com.greggtrunnelldashboard.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "enrollments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name="member_id", nullable = true)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "plan_id", nullable = true)
    private Plan plan;

    private LocalDate coverageStart;
    private LocalDate coverageEnd;
    private Boolean active;

}

