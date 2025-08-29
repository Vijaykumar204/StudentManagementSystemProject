package com.studentmanagementsystem.api.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentmanagementsystem.api.dao.StudentModelDao;
import com.studentmanagementsystem.api.dao.TeacherRequestDao;
import com.studentmanagementsystem.api.model.custom.student.StudentListRequestDto;
import com.studentmanagementsystem.api.model.custom.student.StudentSaveRequestDto;
import com.studentmanagementsystem.api.model.entity.StudentModel;
import com.studentmanagementsystem.api.model.entity.TeacherModel;
import com.studentmanagementsystem.api.service.StudentService;
import com.studentmanagementsystem.api.util.WebServiceUtil;
@Service
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	private StudentModelDao studentRequestDao;
	
	@Autowired
	private TeacherRequestDao teacherRequestDao;
	

	@Override
	public List<StudentListRequestDto> listAllDetailsStudent() {
		
		return studentRequestDao.listAllDetailsStudent();
	}

	@Override
	public Object saveStudent(StudentSaveRequestDto studentSaveRequestDto) {
		
		LocalDateTime today=LocalDateTime.now();
		StudentModel student;
		
		if(studentSaveRequestDto.getStudentId() == null)
		{
		    student=new StudentModel();
			student.setStudentCreateDate(today);	
			TeacherModel teacherData=teacherRequestDao.getTeacherByTeacherId(studentSaveRequestDto.getTeacherId());
			student.setTeacherModel(teacherData);
		}	
		
		else {
			
			Optional<StudentModel> student1=studentRequestDao.getByStudentId(studentSaveRequestDto.getStudentId());
		    student = student1.get();
			student.setUpdateDate(today);
			student.setUpdateTeacher(studentSaveRequestDto.getTeacherId());
			
			
		}
		student.setStudentFirstName(studentSaveRequestDto.getStudentFirstName());
		student.setStudentMiddleName(studentSaveRequestDto.getStudentMiddleName());
		student.setStudentLastName(studentSaveRequestDto.getStudentLastName());
		student.setStudentDateOfBirth(studentSaveRequestDto.getStudentDateOfBirth());
		student.setStudentGender(studentSaveRequestDto.getStudentGender());
		student.setStudentClassOfStudy(studentSaveRequestDto.getStudentClassOfStudy());
		student.setStudentResidingStatus(studentSaveRequestDto.getStudentResidingStatus());
		student.setStudentPhoneNumber(studentSaveRequestDto.getStudentPhoneNumber());
		student.setEmergencyContactPersonName(studentSaveRequestDto.getEmergencyContactPersonName());
		student.setEmergencyContactPhoneNumber(studentSaveRequestDto.getEmergencyContactPhoneNumber());
		student.setHomeStreetName(studentSaveRequestDto.getHomeStreetName());
		student.setHomeCityName(studentSaveRequestDto.getHomeCityName());
		student.setHomePostalCode(studentSaveRequestDto.getHomePostalCode());
		student.setStudentEmail(studentSaveRequestDto.getStudentEmail());

		return studentRequestDao.saveStudent(student);
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
		
		 LocalDateTime today = LocalDateTime.now();
		    Optional<StudentModel> student1 = studentRequestDao.getByStudentId(studentId);

		    if (student1.isEmpty()) {
		        throw new RuntimeException("Student not found with ID: " + studentId);
		    }

		    StudentModel student = student1.get();

		    if (studentActiveStatus == WebServiceUtil.ACTIVE && 
		            studentActiveStatus != student.getStudentActiveStatus()) {

		        student.setStudentActiveStatus(studentActiveStatus);
		        student.setLasteffectivedate(null);
		    } 
		    else if (studentActiveStatus == WebServiceUtil.DEACTIVE && 
		               studentActiveStatus != student.getStudentActiveStatus()) {

		        student.setStudentActiveStatus(studentActiveStatus);
		        student.setLasteffectivedate(today);
		    } 
		    else {
		    	throw new RuntimeException("No need to change");
		    }
		
		return studentRequestDao.activeOrDeactiveByStudentId(student);
	}





}
