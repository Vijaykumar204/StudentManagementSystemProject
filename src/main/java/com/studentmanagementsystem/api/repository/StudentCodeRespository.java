package com.studentmanagementsystem.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studentmanagementsystem.api.model.entity.StudentCodeModel;

@Repository
public interface StudentCodeRespository extends JpaRepository<StudentCodeModel, String> {

	StudentCodeModel findStudentCodeByCode(String code);

}
