package com.studentmanagementsystem.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studentmanagementsystem.api.model.entity.StudentCodeModel;

@Repository
public interface StudentCodeRespository extends JpaRepository<StudentCodeModel, String> {

//	StudentCodeModel findStudentCodeByCode(Character gender);

//	StudentCodeModel findStudentCodeByCode(StudentCodeModel studentGender);

	StudentCodeModel findStudentCodeByCode(String code);

	List<StudentCodeModel> findByGroupCode(String groupCode);

	

}
