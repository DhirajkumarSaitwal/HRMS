package com.example.User.repository;

import com.example.User.entity.Attendance;
import com.example.User.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    @Repository
    interface AttendanceRepository extends JpaRepository<Attendance, UUID> {

        Optional<Attendance> findByEmployeeIdAndAttendanceDate(UUID employeeId, Class<?> date);

        List<Attendance> findAllByEmployeeIdAndAttendanceDateBetween(UUID employeeId, LocalDate start, LocalDate end);

        List<Attendance> findAllByShiftIdAndAttendanceDate(Long shiftId, LocalDate date);

        List<Attendance> findByEmployeeIdAndAttendanceDateBetween(UUID employeeId, LocalDate startDate, LocalDate endDate);

        boolean existsByEmployeeIdAndAttendanceDate(UUID employeeId, Class<?> aClass);
    }
}
