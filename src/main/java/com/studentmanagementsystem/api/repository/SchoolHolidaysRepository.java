package com.studentmanagementsystem.api.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studentmanagementsystem.api.model.entity.SchoolHolidaysModel;

@Repository
public interface SchoolHolidaysRepository extends JpaRepository<SchoolHolidaysModel, Long> {

	SchoolHolidaysModel getHolidayByHolidayDate(LocalDate holidayDate);

}
