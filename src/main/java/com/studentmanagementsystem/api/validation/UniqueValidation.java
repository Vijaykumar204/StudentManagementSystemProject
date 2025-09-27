package com.studentmanagementsystem.api.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentmanagementsystem.api.model.custom.student.StudentDto;
import com.studentmanagementsystem.api.model.custom.teacher.TeacherDto;
import com.studentmanagementsystem.api.model.entity.StudentModel;
import com.studentmanagementsystem.api.model.entity.TeacherModel;
import com.studentmanagementsystem.api.repository.StudentRepository;
import com.studentmanagementsystem.api.repository.TeacherRepository;
import com.studentmanagementsystem.api.util.WebServiceUtil;

import jakarta.transaction.Transactional;
@Service
public class UniqueValidation {
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private TeacherRepository teacherRepository;

	@Transactional
	public List<String> uniqueCheckStudentUpdate(StudentDto studentDto) {
		
		List<String> unique = new ArrayList<>();
					StudentModel isstudentUniqueCheck = studentRepository
							.findByFirstNameAndMiddleNameAndLastNameAndDateOfBirth(studentDto.getFirstName(),
									studentDto.getMiddleName(), studentDto.getLastName(), studentDto.getDateOfBirth());
					if (isstudentUniqueCheck != null) {
						
						if(studentDto.getStudentId()!=null && !studentDto.getStudentId().equals(isstudentUniqueCheck.getStudentId()))
						       unique.add(WebServiceUtil.STUDENT_EXISTS);	
					}
					
					StudentModel email = studentRepository.findByEmail(studentDto.getEmail());
					if (email != null) {	
						
						if(studentDto.getStudentId()!=null &&  !studentDto.getStudentId().equals(email.getStudentId()))
							unique.add( String.format(WebServiceUtil.UNIQUE_ERROR,"email"));
						
					}
					
					StudentModel phoneNumber = studentRepository.findByPhoneNumber(studentDto.getPhoneNumber());
					if (phoneNumber != null) {
						if(studentDto.getStudentId()!=null && !studentDto.getStudentId().equals(phoneNumber.getStudentId()))
								unique.add(String.format(WebServiceUtil.UNIQUE_ERROR,"phoneNumber"));
					}
							
		return unique;
	}
	
	@Transactional
	public List<String> uniqueCheckStudentSave(StudentDto studentDto) {
		
		List<String> unique = new ArrayList<>();
		// isstudentUnique
					StudentModel isstudentUniqueCheck = studentRepository
							.findByFirstNameAndMiddleNameAndLastNameAndDateOfBirth(studentDto.getFirstName(),
									studentDto.getMiddleName(), studentDto.getLastName(), studentDto.getDateOfBirth());
					if (isstudentUniqueCheck != null) {
						
						       unique.add(WebServiceUtil.STUDENT_EXISTS);	
					}
					
					StudentModel email = studentRepository.findByEmail(studentDto.getEmail());
					if (email != null) {	
						
							unique.add( String.format(WebServiceUtil.UNIQUE_ERROR,"email"));
						
					}
					
					StudentModel phoneNumber = studentRepository.findByPhoneNumber(studentDto.getPhoneNumber());
					if (phoneNumber != null) {
								unique.add(String.format(WebServiceUtil.UNIQUE_ERROR,"phoneNumber"));
					}
							
		return unique;
	}

	@Transactional
	public List<String> uniqueCheckTeacherSave(TeacherDto teacherDto) {
		
		List<String> unique = new ArrayList<>();
		
		TeacherModel email = teacherRepository.findByTeacherEmail(teacherDto.getEmail());
		if (email != null) {
			
			unique.add(String.format(WebServiceUtil.UNIQUE_ERROR, "email"));
			
		}

		TeacherModel phoneNumber = teacherRepository.findByTeacherPhoneNumber(teacherDto.getPhoneNumber());
		if (phoneNumber != null) {
		
			unique.add(String.format(WebServiceUtil.UNIQUE_ERROR, "phoneNumber"));
			
		}
		return unique;
	}
	
	@Transactional
	public List<String> uniqueCheckTeacherUpdate(TeacherDto teacherDto) {
		
		List<String> unique = new ArrayList<>();
		
		TeacherModel email = teacherRepository.findByTeacherEmail(teacherDto.getEmail());
		if (email != null) {
		
			if(teacherDto.getId()!=null && ! teacherDto.getId().equals(email.getTeacherId()))
					unique.add(String.format(WebServiceUtil.UNIQUE_ERROR, "email"));
		 }

		TeacherModel phoneNumber = teacherRepository.findByTeacherPhoneNumber(teacherDto.getPhoneNumber());
		if (phoneNumber != null) {
			if(teacherDto.getId()!=null && ! teacherDto.getId().equals(phoneNumber.getTeacherId()))
					unique.add(String.format(WebServiceUtil.UNIQUE_ERROR, "phoneNumber"));
			
		}
		return unique;
	}

}
