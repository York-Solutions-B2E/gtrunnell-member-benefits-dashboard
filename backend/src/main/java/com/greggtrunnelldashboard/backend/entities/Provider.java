package com.greggtrunnelldashboard.backend.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "providers")
public class Provider {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;

    private String providerName;
    private String providerSpeciality;

    @Embedded
    private Address address;

    private String providerPhone;
}