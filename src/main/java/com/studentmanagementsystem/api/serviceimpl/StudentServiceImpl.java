package com.studentmanagementsystem.api.serviceimpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

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

		final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z ]+$");
		final Pattern PHONE_PATTERN = Pattern.compile("^[0-9]{10}$");
		final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@gmail\\.com$");

		List<String> requestMissedField = new ArrayList<>();

		LocalDateTime today = LocalDateTime.now();
		StudentModel student;

		if (studentSaveRequestDto.getStudentFirstName() == null) {
			requestMissedField.add(WebServiceUtil.NAME_ERROR);
			
		 }
		else if (!NAME_PATTERN.matcher(studentSaveRequestDto.getStudentFirstName()).matches()) {
			requestMissedField.add(WebServiceUtil.NAME_REGEX_ERROR);
		}

		if (studentSaveRequestDto.getStudentMiddleName() != null
				&& !NAME_PATTERN.matcher(studentSaveRequestDto.getStudentMiddleName()).matches())

		{
			requestMissedField.add(WebServiceUtil.NAME_REGEX_ERROR);
		}
		

		if (studentSaveRequestDto.getStudentLastName() == null)
				{
			requestMissedField.add(WebServiceUtil.NAME_ERROR);

		} 
		else if( !NAME_PATTERN.matcher(studentSaveRequestDto.getStudentLastName()).matches() ) {
			requestMissedField.add(WebServiceUtil.NAME_REGEX_ERROR);
		}
		
		if(studentSaveRequestDto.getStudentDateOfBirth() == null) {
			requestMissedField.add(WebServiceUtil.DOB_ERROR);
		}
		
		
		if (studentSaveRequestDto.getStudentGender() == '\u0000')
				 {
			requestMissedField.add(WebServiceUtil.GENDER_ERROR);
		}
		else if( studentSaveRequestDto.getStudentGender() != WebServiceUtil.MALE
				&& studentSaveRequestDto.getStudentGender() != WebServiceUtil.FEMALE
				&& studentSaveRequestDto.getStudentGender() != WebServiceUtil.UNKNOWN) {
			requestMissedField.add(WebServiceUtil.GENDER_VALIDATION_ERROR);
		}
		

		if (studentSaveRequestDto.getStudentClassOfStudy() <= 6
				|| studentSaveRequestDto.getStudentClassOfStudy() >= 10) {
			requestMissedField.add(WebServiceUtil.STUDENT_CLASS_OF_STUDY_ERROR);

		}
		
		
		if (studentSaveRequestDto.getStudentResidingStatus() == '\u0000')
				 {
			requestMissedField.add(WebServiceUtil.RESIDING_STATUS_ERROR);
		}
		else if(studentSaveRequestDto.getStudentResidingStatus() != WebServiceUtil.HOSTEL
				&& studentSaveRequestDto.getStudentResidingStatus() != WebServiceUtil.DAYSCHOLAR) {
			requestMissedField.add(WebServiceUtil.RESIDING_STATUS_VALIDATION_ERROR);
		}
		
		
		if (studentSaveRequestDto.getStudentPhoneNumber() == null) {
			requestMissedField.add(WebServiceUtil.PH_NO_ERROR);
		}
		else if(!PHONE_PATTERN.matcher(studentSaveRequestDto.getStudentPhoneNumber()).matches()) {
			requestMissedField.add(WebServiceUtil.PH_NO_REGEX_ERROR);
		}
		
		
		if (studentSaveRequestDto.getStudentEmail() == null) {
			requestMissedField.add(WebServiceUtil.EMAIL_ERROR);
			
		}
		else if(!EMAIL_PATTERN.matcher(studentSaveRequestDto.getStudentEmail()).matches()) {
			requestMissedField.add(WebServiceUtil.EMAIL_REGEX_ERROR);
		}
		
		if (studentSaveRequestDto.getHomeStreetName() == null) {
			requestMissedField.add(WebServiceUtil.STREET_NAME_ERROR);
		}
		
		
		if (studentSaveRequestDto.getHomeCityName() == null) {
			requestMissedField.add(WebServiceUtil.CITY_NAME_ERROR);
		}
		if (studentSaveRequestDto.getHomePostalCode() == null) {
			requestMissedField.add(WebServiceUtil.POSTAL_CODE_ERROR);
		}
		if (studentSaveRequestDto.getTeacherId() == null) {
			requestMissedField.add(WebServiceUtil.TEACHER_ID_ERROR);
		}

		if (!requestMissedField.isEmpty()) {
			return requestMissedField;
		}
		
		StudentModel studentModel = studentRequestDao.findByStudentFirstNameAndStudentMiddleNameAndStudentLastNameAndStudentDateOfBirth(studentSaveRequestDto.getStudentFirstName(),studentSaveRequestDto.getStudentMiddleName(),studentSaveRequestDto.getStudentLastName(),studentSaveRequestDto.getStudentDateOfBirth());
		
		if(studentModel!=null) {
			return "Student name and DOB alresy exists";
		}

		if (studentSaveRequestDto.getStudentId() == null) {
			student = new StudentModel();
			student.setStudentCreateDate(today);
			TeacherModel teacherData = teacherRequestDao.getTeacherByTeacherId(studentSaveRequestDto.getTeacherId());
			student.setTeacherModel(teacherData);
		}

		else {

			Optional<StudentModel> student1 = studentRequestDao.getByStudentId(studentSaveRequestDto.getStudentId());
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

		return studentRequestDao.getAllHostelStudents(studentActiveStatus, WebServiceUtil.HOSTEL);
	}

	@Override
	public List<StudentListRequestDto> getAllDaysStudents(char studentActiveStatus) {
		// TODO Auto-generated method stub
		return studentRequestDao.getAllHostelStudents(studentActiveStatus, WebServiceUtil.DAYSCHOLAR);
	}

	@Override
	public List<StudentListRequestDto> getStudentsBy(Long studentId, String studentEmail, String studentPhoneNumber) {

		return studentRequestDao.getStudentsBy(studentId, studentEmail, studentPhoneNumber);
	}

	@Override
	public List<StudentListRequestDto> getBystudentStatus(char studentActiveStatus) {
		return studentRequestDao.getBystudentStatus(studentActiveStatus);
	}

	@Override
	public Object activeOrDeactiveByStudentId(char studentActiveStatus, Long studentId) {
		
		
		
		List<String> requestMissedField = new ArrayList<>();
		
		
		if(studentActiveStatus!=WebServiceUtil.ACTIVE && studentActiveStatus!=WebServiceUtil.DEACTIVE ) {
			requestMissedField.add("StudentActiveAndDeactiveStatus");
		}
		
		if(studentId!=null) {
			requestMissedField.add("StudentId");
		}
		
		
		if (!requestMissedField.isEmpty()) {
			return requestMissedField;
		}

		LocalDateTime today = LocalDateTime.now();
		Optional<StudentModel> student1 = studentRequestDao.getByStudentId(studentId);

		if (student1.isEmpty()) {
			throw new RuntimeException("Student not found with ID: " + studentId);
		}

		StudentModel student = student1.get();

		if (studentActiveStatus == WebServiceUtil.ACTIVE && studentActiveStatus != student.getStudentActiveStatus()) {

			student.setStudentActiveStatus(studentActiveStatus);
			student.setLasteffectivedate(null);
		} else if (studentActiveStatus == WebServiceUtil.DEACTIVE
				&& studentActiveStatus != student.getStudentActiveStatus()) {

			student.setStudentActiveStatus(studentActiveStatus);
			student.setLasteffectivedate(today);
		} else {
			throw new RuntimeException("No need to change");
		}

		return studentRequestDao.activeOrDeactiveByStudentId(student);
	}

}
