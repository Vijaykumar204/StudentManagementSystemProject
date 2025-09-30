package com.studentmanagementsystem.api.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.studentmanagementsystem.api.model.entity.HolidayModel;

@Repository
public interface HolidayRepository extends JpaRepository<HolidayModel, Long> {

	
	
	@Query("SELECT COUNT(h.holidayId) FROM HolidayModel h")
	Long findTotalCount();

	HolidayModel getByHolidayDate(LocalDate holidayDate);

}
