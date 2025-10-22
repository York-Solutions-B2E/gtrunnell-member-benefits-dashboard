package com.greggtrunnelldashboard.backend.dto;

import com.greggtrunnelldashboard.backend.entities.ClaimStatusEvent;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class ClaimStatusEventDTO {
    private String status;
    private OffsetDateTime occurredAt;

    public static ClaimStatusEventDTO from(ClaimStatusEvent event) {
        ClaimStatusEventDTO dto = new ClaimStatusEventDTO();
        dto.setStatus(event.getStatus().name());
        dto.setOccurredAt(event.getOccurredAt());
        return dto;
    }
}
