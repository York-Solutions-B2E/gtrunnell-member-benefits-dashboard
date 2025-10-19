package com.greggtrunnelldashboard.backend.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address {

    private  String line1;
    private  String line2;
    private  String city;
    private  String state;
    private  String postalCode;
}
