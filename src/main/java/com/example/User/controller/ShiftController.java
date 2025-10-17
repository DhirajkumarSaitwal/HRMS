package com.example.User.controller;

import com.example.User.dto.ShiftMasterDTO;
import com.example.User.service.ShiftService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/shifts")
@RequiredArgsConstructor
public class ShiftController {

@Autowired
    private ShiftService shiftService;

    @PreAuthorize("hasRole('HR') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ShiftMasterDTO> create(@Valid @RequestBody ShiftMasterDTO dto) {
        return ResponseEntity.ok(shiftService.createShift(dto));
    }

    @PreAuthorize("hasRole('HR') or hasRole('ADMIN')")
    @PutMapping("/{shiftId}")
    public ResponseEntity<ShiftMasterDTO> update(@PathVariable Long shiftId, @Valid @RequestBody ShiftMasterDTO dto) {
        return ResponseEntity.ok(shiftService.updateShift(shiftId, dto));
    }

    @PreAuthorize("hasAnyRole('HR','ADMIN','MANAGER','EMPLOYEE')")
    @GetMapping
    public ResponseEntity<List<ShiftMasterDTO>> list() {
        return ResponseEntity.ok(shiftService.listShifts());
    }

    @PreAuthorize("hasRole('HR') or hasRole('ADMIN')")
    @DeleteMapping("/{shiftId}")
    public ResponseEntity<Void> delete(@PathVariable Long shiftId) {
        shiftService.deleteShift(shiftId);
        return ResponseEntity.noContent().build();
    }
}
