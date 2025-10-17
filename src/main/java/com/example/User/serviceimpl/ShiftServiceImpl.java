package com.example.User.serviceimpl;

import com.example.User.dto.ShiftMasterDTO;
import com.example.User.entity.ShiftMaster;
import com.example.User.exception.BadRequestException;
import com.example.User.exception.ConflictException;
import com.example.User.exception.ResourceNotFoundException;
import com.example.User.repository.ShiftMasterRepository;
import com.example.User.service.ShiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShiftServiceImpl implements ShiftService {

    @Autowired
    private  ShiftMasterRepository repo;

    private boolean isOvernight(LocalTime startTime, LocalTime endTime) {
        return endTime.isBefore(startTime);
    }

    @Override
    public ShiftMasterDTO createShift(ShiftMasterDTO dto) {
        if (repo.existsByShiftNameIgnoreCase(dto.getShiftName())) {
            throw new ConflictException("Shift name already exists");
        }
        if (dto.getStartTime().equals(dto.getEndTime())) {
            throw new BadRequestException("startTime and endTime cannot be equal");
        }

        ShiftMaster entity = ShiftMaster.builder()
                .shiftName(dto.getShiftName())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .weekOff(dto.getWeekOff())
                .graceMinutes(dto.getGraceMinutes() == null ? 0 : dto.getGraceMinutes())
                .breakMinutes(dto.getBreakMinutes() == null ? 0 : dto.getBreakMinutes())
                .isRotational(dto.getIsRotational() == null ? false : dto.getIsRotational())
                .isActive(dto.getIsActive() == null ? true : dto.getIsActive())
                .isOvernight(isOvernight(dto.getStartTime(), dto.getEndTime()))
                .createdBy("Admin")
                .updatedBy("Admin")
                .build();

        ShiftMaster saved = repo.save(entity);
        return mapToDto(saved);
    }

    private ShiftMasterDTO mapToDto(ShiftMaster entity) {
        return ShiftMasterDTO.builder()
                .shiftId(entity.getShiftId())
                .shiftName(entity.getShiftName())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .weekOff(entity.getWeekOff())
                .graceMinutes(entity.getGraceMinutes())
                .breakMinutes(entity.getBreakMinutes())
                .isRotational(entity.getIsRotational())
                .isActive(entity.getIsActive())
                .isOvernight(Boolean.valueOf(entity.getIsOvernight() ? "Yes" : "No"))
                .build();
    }


    @Override
    public ShiftMasterDTO updateShift(Long shiftId, ShiftMasterDTO dto) {
        ShiftMaster existing = repo.findById(shiftId)
                .orElseThrow(() -> new ResourceNotFoundException("Shift not found with ID: " + shiftId));

        if (dto.getStartTime().equals(dto.getEndTime())) {
            throw new BadRequestException("startTime and endTime cannot be equal");
        }

        if (repo.existsByShiftNameIgnoreCaseAndShiftIdNot(dto.getShiftName(), shiftId)) {
            throw new ConflictException("Shift name already exists for another shift");
        }

        existing.setShiftName(dto.getShiftName());
        existing.setStartTime(dto.getStartTime());
        existing.setEndTime(dto.getEndTime());
        existing.setWeekOff(dto.getWeekOff());
        existing.setGraceMinutes(dto.getGraceMinutes());
        existing.setBreakMinutes(dto.getBreakMinutes());
        existing.setIsRotational(dto.getIsRotational());
        existing.setIsActive(dto.getIsActive());
        existing.setIsOvernight(isOvernight(dto.getStartTime(), dto.getEndTime()));
        existing.setUpdatedBy("system");

        ShiftMaster updated = repo.save(existing);
        return mapToDto(updated);
    }
    @Override
    public List<ShiftMasterDTO> listShifts() {
        return repo.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteShift(Long shiftId) {
        ShiftMaster entity = repo.findById(shiftId)
                .orElseThrow(() -> new ResourceNotFoundException("Shift not found with ID: " + shiftId));
        repo.delete(entity);
    }

}
