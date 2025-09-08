package com.studentmanagementsystem.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.studentmanagementsystem.api.model.entity.MarkModel;

@Repository
public interface StudentMarksRepository extends JpaRepository<MarkModel, Long> {

	@Query("SELECT sm FROM MarkModel sm WHERE sm.studentModel.studentId = :studentId AND sm.quarterAndYear = :quarterAndYear")
	MarkModel findByStudentIdAndQuarterAndYear(@Param("studentId") Long studentId,
			@Param("quarterAndYear") String quarterAndYear);

}
