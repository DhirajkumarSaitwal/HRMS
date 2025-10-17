package com.example.User.dto;

import com.example.User.entity.ComplianceType;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplianceDTO {
    private Long complianceId;
    private String complianceName;
    private ComplianceType complianceType;
    private String description;
    private Boolean isActive;
    private String createdBy;
    private String updatedBy;
}
