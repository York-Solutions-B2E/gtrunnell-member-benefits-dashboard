package com.greggtrunnelldashboard.backend.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;

    @Column(nullable = false)
    private String authProvider;

    @Column(nullable = false, unique = true)
    private String authSub;

    @Column(nullable = false)
    private String email;

    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

}