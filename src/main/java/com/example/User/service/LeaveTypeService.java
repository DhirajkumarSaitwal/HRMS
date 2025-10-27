package com.example.User.service;


import com.example.User.entity.LeaveType;
import java.util.List;

public interface LeaveTypeService {


    List<LeaveType> getAllLeaveTypes();


    LeaveType createLeaveType(LeaveType leaveType);


    LeaveType updateLeaveType(Long leaveTypeId, LeaveType leaveType);


    void deleteLeaveType(Long leaveTypeId);
}

