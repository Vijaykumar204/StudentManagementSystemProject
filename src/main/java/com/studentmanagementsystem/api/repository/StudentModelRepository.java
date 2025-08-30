package com.studentmanagementsystem.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.studentmanagementsystem.api.model.entity.StudentModel;

@Repository
public interface StudentModelRepository extends JpaRepository<StudentModel, Long>{

     StudentModel getStudentByStudentId(Long studentId);

//	String findStudentEmailByStudentId(Long studentId);

	

}
