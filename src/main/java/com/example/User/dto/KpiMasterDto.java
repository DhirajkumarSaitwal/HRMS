package com.example.User.dto;

import com.example.User.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KpiMasterDto {

    private Long kpiId;
    private Long departmentId;
    private Role role; // ✅ Fixed (was Long before)
    private String kpiName;
    private String kpiDescription;
    private BigDecimal weightage;
}
