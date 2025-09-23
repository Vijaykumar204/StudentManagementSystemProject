package com.studentmanagementsystem.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.studentmanagementsystem.api.model.entity.QuarterlyAttendanceReportModel;

@Repository
public interface QuarterlyAttendanceModelRepository extends JpaRepository<QuarterlyAttendanceReportModel, Long> {

	@Query("SELECT q FROM QuarterlyAttendanceReportModel q " + "WHERE q.studentModel.studentId = :studentId "
			+ "AND q.quarterAndYear = :quarterAndYear")
	QuarterlyAttendanceReportModel findByStudentAndQuarterAndYear(@Param("studentId") Long studentId,
			@Param("quarterAndYear") String quarterAndYear);

	@Query("SELECT q.attendanceComplianceStatus.code FROM QuarterlyAttendanceReportModel q "
			+ "WHERE q.studentModel.studentId = :studentId " + "AND q.quarterAndYear = :quarterAndYear")
	String findAttendanceComplianceStatusByStudentIdandquarterAndYear(@Param("studentId") Long studentId,
			@Param("quarterAndYear") String quarterAndYear);
	@Query("SELECT COUNT(q.quarterlyAttendanceId) FROM QuarterlyAttendanceReportModel q "
			+ "WHERE q.studentModel.classOfStudy = :classOfStudy " + "AND q.quarterAndYear = :quarterAndYear")
	Integer findTotalCount(@Param("classOfStudy")Integer classOfStudy,@Param("quarterAndYear") String quarterAndYear);

}
