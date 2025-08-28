package com.studentmanagementsystem.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.studentmanagementsystem.api.model.entity.QuarterlyAttendanceReportModel;
import com.studentmanagementsystem.api.model.entity.StudentMarks;
import com.studentmanagementsystem.api.model.entity.StudentModel;

@Repository
public interface QuarterlyAttendanceModelRepository extends JpaRepository<QuarterlyAttendanceReportModel, Long> {


// QuarterlyAttendanceReportModel findByStudentModel_StudentIdAndQuarterAndYear(StudentModel studentModel, String quarterAndYear);
//	
//	QuarterlyAttendanceReportModel findByStudentAndQuarterAndYear(StudentModel student, String quarterAndYear);
	 
	@Query("SELECT q FROM QuarterlyAttendanceReportModel q " +
		       "WHERE q.studentModel.studentId = :studentId " +
		       "AND q.quarterAndYear = :quarterAndYear")
		QuarterlyAttendanceReportModel findByStudentAndQuarterAndYear(
		        @Param("studentId") Long studentId,
		        @Param("quarterAndYear") String quarterAndYear);

//	StudentMarks findByStudentmodel_StudentIdAndQuarterlyAttendanceModel_QuarterAndYear(Long studentId,
//			String quarterAndYear);

//	QuarterlyAttendanceReportModel findByStudentModel_StudentIdAndQuarterAndYear(Long studentId, String quarterAndYear);
}
