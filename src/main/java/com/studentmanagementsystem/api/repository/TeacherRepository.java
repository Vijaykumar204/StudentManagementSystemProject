package com.studentmanagementsystem.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.studentmanagementsystem.api.model.entity.TeacherModel;

public interface TeacherRepository extends JpaRepository<TeacherModel, Long> {

	TeacherModel findTeacherByTeacherId(Long teacherId);

	TeacherModel findTeacherIdByTeacherId(Long teacherId);
	
	@Query("SELECT COUNT(t.teacherId) FROM TeacherModel t")
	Long findTotalCount();

	TeacherModel findTeacherPasswordByTeacherEmail(String email);


	TeacherModel findByTeacherEmail(String email);

	TeacherModel findByTeacherPhoneNumber(String phoneNumber);

}
