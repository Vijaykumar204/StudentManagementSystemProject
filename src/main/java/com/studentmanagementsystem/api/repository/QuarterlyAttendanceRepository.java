package com.studentmanagementsystem.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.studentmanagementsystem.api.model.entity.QuarterlyAttendanceModel;

@Repository
public interface QuarterlyAttendanceRepository extends JpaRepository<QuarterlyAttendanceModel, Long> {

	@Query("SELECT q FROM QuarterlyAttendanceModel q " + "WHERE q.studentModel.studentId = :studentId "
			+ "AND q.quarterAndYear = :quarterAndYear")
	QuarterlyAttendanceModel findByStudentAndQuarterAndYear(@Param("studentId") Long studentId,
			@Param("quarterAndYear") String quarterAndYear);

	@Query("SELECT q.attendanceComplianceStatus.code FROM QuarterlyAttendanceModel q "
			+ "WHERE q.studentModel.studentId = :studentId " + "AND q.quarterAndYear = :quarterAndYear")
	String findAttendanceComplianceStatusByStudentIdandquarterAndYear(@Param("studentId") Long studentId,
			@Param("quarterAndYear") String quarterAndYear);
	@Query("SELECT COUNT(q.quarterlyAttendanceId) FROM QuarterlyAttendanceModel q "
			+ "WHERE q.studentModel.classOfStudy = :classOfStudy " + "AND q.quarterAndYear = :quarterAndYear")
	Long findTotalCount(@Param("classOfStudy")Integer classOfStudy,@Param("quarterAndYear") String quarterAndYear);

}
