package com.example.User.serviceimpl;

import com.example.User.entity.LeaveType;
import com.example.User.exception.NotFoundException;
import com.example.User.repository.LeaveTypeRepository;
import com.example.User.service.LeaveTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LeaveTypeServiceImpl implements LeaveTypeService {

    private final LeaveTypeRepository leaveTypeRepository;

    @Override
    public List<LeaveType> getAllLeaveTypes() {
        return leaveTypeRepository.findAll();
    }

    @Override
    public LeaveType createLeaveType(LeaveType leaveType) {

        boolean exists = leaveTypeRepository.existsByNameIgnoreCase(leaveType.getName());
        if (exists) {
            throw new IllegalArgumentException("Leave type with name '" + leaveType.getName() + "' already exists.");
        }
        return leaveTypeRepository.save(leaveType);
    }

    @Override
    public LeaveType updateLeaveType(Long leaveTypeId, LeaveType updatedLeaveType) {
        LeaveType existing = leaveTypeRepository.findById(leaveTypeId)
                .orElseThrow(() -> new NotFoundException("Leave type not found with ID: " + leaveTypeId));


        existing.setName(updatedLeaveType.getName());
        existing.setDescription(updatedLeaveType.getDescription());
        existing.setAnnualLimit(updatedLeaveType.getAnnualLimit());
        existing.setCarryForward(updatedLeaveType.getCarryForward());
        existing.setEncashable(updatedLeaveType.getEncashable());
        existing.setIsActive(updatedLeaveType.getIsActive());

        return leaveTypeRepository.save(existing);
    }

    @Override
    public void deleteLeaveType(Long leaveTypeId) {
        LeaveType existing = leaveTypeRepository.findById(leaveTypeId)
                .orElseThrow(() -> new NotFoundException("Leave type not found with ID: " + leaveTypeId));
        leaveTypeRepository.delete(existing);
    }
}

