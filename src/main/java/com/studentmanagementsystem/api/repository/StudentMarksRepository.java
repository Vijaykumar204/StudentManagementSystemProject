package com.studentmanagementsystem.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.studentmanagementsystem.api.model.entity.StudentMarks;

@Repository
public interface StudentMarksRepository extends JpaRepository<StudentMarks, Long> {


//	StudentMarks findByStudentModel_StudentIdAndQuarterAndYear(Long studentId, String quarterAndYear);

	
	@Query("SELECT sm FROM StudentMarks sm WHERE sm.studentModel.studentId = :studentId AND sm.quarterAndYear = :quarterAndYear")
	StudentMarks findByStudentIdAndQuarterAndYear(@Param("studentId") Long studentId, 
	                                              @Param("quarterAndYear") String quarterAndYear);

	
}
