package com.greggtrunnelldashboard.backend.entities;

import jakarta.persistence.Embeddable;
import lombok.*;

@Data
@NoArgsConstructor
@Embeddable
public class Address {

    private  String line1;
    private  String line2;
    private  String city;
    private  String state;
    private  String postalCode;
}
