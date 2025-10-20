package com.greggtrunnelldashboard.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "providers")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Provider {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;

    private String providerName;
    private String providerSpeciality;
    private Address address;
    private String providerPhone;
}