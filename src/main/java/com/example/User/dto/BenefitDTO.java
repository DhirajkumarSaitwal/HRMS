package com.example.User.dto;

import com.example.User.entity.BenefitMaster;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BenefitDTO {
    private Long benefitId;
    private String benefitName;
    private BenefitMaster.BenefitType benefitType;
    private String description;
    private Boolean isActive;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
