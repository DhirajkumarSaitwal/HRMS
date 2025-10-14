package com.example.User.repository;


import com.example.User.dto.HolidayFilterRequestDTO;
import com.example.User.entity.HolidayCalendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface HolidayRepository extends JpaRepository<HolidayCalendar, Long> {

    // To check duplicate holiday (same date & region)
    Optional<HolidayCalendar> findByHolidayDateAndRegion(LocalDate holidayDate, String region);

    boolean existsByHolidayDateAndRegionAndHolidayIdNot(LocalDate date, String region, Long holidayId);

    // Filter by year and region
    @Query("SELECT h FROM HolidayCalendar h WHERE YEAR(h.holidayDate) = :year AND h.region = :region")
    List<HolidayCalendar> findByYearAndRegion(@Param("year") Integer year, @Param("region") String region);

    @Query("SELECT h FROM HolidayCalendar h WHERE YEAR(h.holidayDate) = :year")
    List<HolidayCalendar> findByYear(@Param("year") Integer year);

    List<HolidayCalendar> findByRegion(String region);

    // get by Id
    Optional<HolidayCalendar> findById(Long holidayId);
}

//




