package com.studentmanagementsystem.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.studentmanagementsystem.api.model.custom.studentmarks.StudentMarksDto;
import com.studentmanagementsystem.api.model.entity.MarkModel;

@Repository
public interface StudentMarksRepository extends JpaRepository<MarkModel, Long> {

	@Query("SELECT sm FROM MarkModel sm WHERE sm.studentModel.studentId = :studentId AND sm.quarterAndYear = :quarterAndYear")
	MarkModel findByStudentIdAndQuarterAndYear(@Param("studentId") Long studentId,
			@Param("quarterAndYear") String quarterAndYear);
	@Query("SELECT sm FROM MarkModel sm WHERE  sm.quarterAndYear = :quarterAndYear AND sm.studentModel.classOfStudy = :classOfStudy")
	List<StudentMarksDto> findMarkByQuarterAndYear(@Param("quarterAndYear") String quarterAndYear,@Param("classOfStudy") Integer classOfStudy);

}
