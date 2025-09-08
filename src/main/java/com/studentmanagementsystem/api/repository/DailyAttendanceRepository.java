package com.studentmanagementsystem.api.repository;

import java.time.LocalDate;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.studentmanagementsystem.api.model.entity.DailyAttendanceModel;

@Repository
public interface DailyAttendanceRepository extends JpaRepository<DailyAttendanceModel, Long> {

	@Query("SELECT d FROM DailyAttendanceModel d WHERE d.studentModel.studentId = :studentId AND d.attendanceDate = :attendanceDate")
	DailyAttendanceModel findStudentIdAndAttendanceDate(@Param("studentId") Long studentId,
			@Param("attendanceDate") LocalDate attendanceDate);

	@Query("SELECT d FROM DailyAttendanceModel d " + "WHERE d.attendanceDate = :attendanceDate "
			+ "AND d.attendanceStatus.code = :statusCode")
	List<DailyAttendanceModel> findAbsentStudentIds(@Param("attendanceDate") LocalDate attendanceDate,
			@Param("statusCode") String statusCode);

}
