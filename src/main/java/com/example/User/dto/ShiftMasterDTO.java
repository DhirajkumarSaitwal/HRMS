package com.example.User.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShiftMasterDTO {
    private Long shiftId;

    @NotBlank
    private String shiftName;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    private String weekOff;
    private Boolean isActive;
    private Integer graceMinutes;
    private Integer breakMinutes;
    private Boolean isRotational;
    private Boolean isOvernight;
}
