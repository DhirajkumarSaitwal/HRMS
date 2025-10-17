package com.example.User.service;

import com.example.User.dto.ShiftMasterDTO;

import java.util.List;

public interface ShiftService {
    ShiftMasterDTO createShift(ShiftMasterDTO dto);

    ShiftMasterDTO updateShift(Long shiftId, ShiftMasterDTO dto);

    List<ShiftMasterDTO> listShifts();

    void deleteShift(Long shiftId);


}
