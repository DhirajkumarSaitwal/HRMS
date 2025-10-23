package com.example.User.dto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TimesheetApprovalDTO {

    @NotNull
    private Long approvedBy;
    @NotNull
    private Boolean approve;
    private String remarks;

}
