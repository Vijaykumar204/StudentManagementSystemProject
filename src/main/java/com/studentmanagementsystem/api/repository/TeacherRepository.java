package com.studentmanagementsystem.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studentmanagementsystem.api.model.entity.TeacherModel;

public interface TeacherRepository extends JpaRepository<TeacherModel, Long> {

	

	TeacherModel getTeacherByTeacherId(Long teacherId);



}
	