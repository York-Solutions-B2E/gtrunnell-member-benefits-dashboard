package com.greggtrunnelldashboard.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
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