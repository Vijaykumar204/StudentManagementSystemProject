package com.studentmanagementsystem.api.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.studentmanagementsystem.api.model.entity.StudentModel;

@Repository
public interface StudentRepository extends JpaRepository<StudentModel, Long> {


	StudentModel findByFirstNameAndMiddleNameAndLastNameAndDateOfBirth(
			String studentFirstName, String studentMiddleName, String studentLastName, LocalDate studentDateOfBirth);

	StudentModel findStudentByStudentId(Long studentId);

	@Query("SELECT COUNT(s.studentId) FROM StudentModel s WHERE s.classOfStudy = :classOfStudy")
	Long findTotalCount(@Param("classOfStudy") Integer classOfStudy);

	@Query("select s.studentId from StudentModel s where s.classOfStudy = :classOfStudy")
	List<Long> findStudentIdByClassOfStudy(@Param("classOfStudy") Integer classOfStudy);

	StudentModel findByEmail(String email);

	StudentModel findByPhoneNumber(String phoneNumber);



}
