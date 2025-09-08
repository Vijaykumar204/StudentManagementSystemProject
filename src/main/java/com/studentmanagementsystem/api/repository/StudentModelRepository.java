package com.studentmanagementsystem.api.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.studentmanagementsystem.api.model.entity.StudentModel;

@Repository
public interface StudentModelRepository extends JpaRepository<StudentModel, Long> {

	StudentModel findStudentEmailByStudentId(Long studentId);

	StudentModel findByStudentFirstNameAndStudentMiddleNameAndStudentLastNameAndStudentDateOfBirth(
			String studentFirstName, String studentMiddleName, String studentLastName, LocalDate studentDateOfBirth);

	StudentModel findStudentByStudentId(Long teacherId);

	StudentModel findStudentFirstNameAndStudentMiddleNameAndStudentLastNameAndStudentEmailByStudentId(Long studentId);

}
