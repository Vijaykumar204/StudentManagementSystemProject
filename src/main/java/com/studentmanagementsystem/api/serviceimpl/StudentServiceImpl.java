package com.studentmanagementsystem.api.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentmanagementsystem.api.dao.StudentRequestDao;
import com.studentmanagementsystem.api.model.custom.student.StudentListRequestDto;
import com.studentmanagementsystem.api.model.custom.student.StudentSaveRequestDto;
import com.studentmanagementsystem.api.service.StudentService;
import com.studentmanagementsystem.api.util.WebServiceUtil;
@Service
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	private StudentRequestDao studentRequestDao;
	

	@Override
	public List<StudentListRequestDto> listAllDetailsStudent() {
		
		return studentRequestDao.listAllDetailsStudent();
	}

	@Override
	public Object saveStudent(StudentSaveRequestDto studentSaveRequestDto) {

		return studentRequestDao.saveStudent(studentSaveRequestDto);
	}

	@Override
	public List<StudentListRequestDto> getAllHostelStudents(char studentActiveStatus) {
	
		return studentRequestDao.getAllHostelStudents(studentActiveStatus,WebServiceUtil.HOSTEL);
	}

	@Override
	public List<StudentListRequestDto> getAllDaysStudents(char studentActiveStatus) {
		// TODO Auto-generated method stub
		return studentRequestDao.getAllHostelStudents(studentActiveStatus,WebServiceUtil.DAYSCHOLAR);
	}

	@Override
	public List<StudentListRequestDto> getStudentsBy(Long studentId, String studentEmail, String studentPhoneNumber) {
		
		return studentRequestDao.getStudentsBy(studentId,studentEmail,studentPhoneNumber);
	}


	@Override
	public List<StudentListRequestDto> getBystudentStatus(char studentActiveStatus) {
		return studentRequestDao.getBystudentStatus(studentActiveStatus);
	}

	@Override
	public Object activeOrDeactiveByStudentId(char studentActiveStatus, Long studentId) {
		
		return studentRequestDao.activeOrDeactiveByStudentId(studentActiveStatus,studentId);
	}





}
