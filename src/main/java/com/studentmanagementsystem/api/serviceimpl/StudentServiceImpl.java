package com.studentmanagementsystem.api.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.studentmanagementsystem.api.dao.StudentModelDao;
import com.studentmanagementsystem.api.dao.TeacherRequestDao;
import com.studentmanagementsystem.api.model.custom.Response;
import com.studentmanagementsystem.api.model.custom.student.StudentDto;
import com.studentmanagementsystem.api.model.custom.student.StudentModelListResponse;
import com.studentmanagementsystem.api.model.custom.student.StudentSaveRequestDto;
import com.studentmanagementsystem.api.model.entity.StudentModel;
import com.studentmanagementsystem.api.model.entity.TeacherModel;
import com.studentmanagementsystem.api.repository.StudentModelRepository;
import com.studentmanagementsystem.api.service.StudentService;
import com.studentmanagementsystem.api.util.WebServiceUtil;
import com.studentmanagementsystem.api.validation.FieldValidation;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentModelDao studentRequestDao;

	@Autowired
	private TeacherRequestDao teacherRequestDao;
	
	@Autowired
	private FieldValidation fieldValidation;
	
	@Autowired
	private StudentModelRepository studentModelRepository;
	
	/**
	 * Retrieve the list of all student details.
	 */

	@Override
	public StudentModelListResponse listAllDetailsStudent() {
		
		List<StudentDto> studentListRequestDto = studentRequestDao.listAllDetailsStudent();
		StudentModelListResponse response = new StudentModelListResponse();
		response.setStatus(WebServiceUtil.SUCCESS);	
		response.setData(studentListRequestDto);

		return response;
	}

	/**
	 * Save or update student details.
	 */
	
	@Override
	public Response saveStudent(StudentSaveRequestDto studentSaveRequestDto) {
		
		Response response = new Response();

		LocalDateTime today = LocalDateTime.now();
		StudentModel student;

		List<String> requestMissedFieldList= fieldValidation.checkValidationStudentSaveMethod(studentSaveRequestDto);

		if (!requestMissedFieldList.isEmpty()) {
			response.setStatus(WebServiceUtil.WARNING);	
			response.setData(requestMissedFieldList);		
			return response;
		}
		
		StudentModel studentModel = studentRequestDao.findByStudentFirstNameAndStudentMiddleNameAndStudentLastNameAndStudentDateOfBirth(studentSaveRequestDto.getStudentFirstName(),studentSaveRequestDto.getStudentMiddleName(),studentSaveRequestDto.getStudentLastName(),studentSaveRequestDto.getStudentDateOfBirth());
		
		if(studentModel!=null) {
			
			response.setStatus(WebServiceUtil.WARNING);	
			response.setData(WebServiceUtil.STUDENT_EXISTS);
			
			return response;
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
		
		studentModelRepository.save(student);
		
		response.setStatus(WebServiceUtil.SUCCESS);	
		response.setData(WebServiceUtil.SAVE);
		
		return response;
	}

	@Override
	public StudentModelListResponse getAllHostelStudents(char studentActiveStatus) {
		 List<StudentDto> studentListRequestDto =studentRequestDao.getAllHostelStudents(studentActiveStatus, WebServiceUtil.HOSTEL);
		 StudentModelListResponse response = new StudentModelListResponse();
		 response.setStatus(WebServiceUtil.SUCCESS);	
		 response.setData(studentListRequestDto);
		return response;
	}

	@Override
	public StudentModelListResponse getAllDaysStudents(char studentActiveStatus) {
		
	List<StudentDto> studentListRequestDto = studentRequestDao.getAllHostelStudents(studentActiveStatus, WebServiceUtil.DAYSCHOLAR);
		StudentModelListResponse response = new StudentModelListResponse();
		response.setStatus(WebServiceUtil.SUCCESS);	
		response.setData(studentListRequestDto);		
		return response;
	}

	@Override
	public StudentModelListResponse getStudentsBy(Long studentId, String studentEmail, String studentPhoneNumber) {
		List<StudentDto> studentListRequestDto = studentRequestDao.getStudentsBy(studentId, studentEmail, studentPhoneNumber);
		StudentModelListResponse response = new StudentModelListResponse();
		response.setStatus(WebServiceUtil.SUCCESS);	
		response.setData(studentListRequestDto);		
		return response;
	}

	@Override
	public StudentModelListResponse getBystudentStatus(char studentActiveStatus) {
		List<StudentDto> studentListRequestDto = studentRequestDao.getBystudentStatus(studentActiveStatus);
		StudentModelListResponse response = new StudentModelListResponse();
		response.setStatus(WebServiceUtil.SUCCESS);	
		response.setData(studentListRequestDto);	
		
		return response;
	}

	@Override
	public Object activeOrDeactiveByStudentId(Character studentActiveStatus, Long studentId) {
		
		
		Response response = new Response();
		
		List<String> requestMissedFieldList = fieldValidation.checkValidationActiveOrDeactiveByStudentId(studentActiveStatus,studentId);

		if (!requestMissedFieldList.isEmpty()) {
			response.setStatus(WebServiceUtil.WARNING);	
			response.setData(requestMissedFieldList);
					
			return response;
		}

		LocalDateTime today = LocalDateTime.now();
		Optional<StudentModel> studentOptional = studentRequestDao.getByStudentId(studentId);

		if (studentOptional.isEmpty()) {
			response.setStatus(WebServiceUtil.WARNING);	
			response.setData(WebServiceUtil.STUDENT_NOT_FOUND + studentId );
			
			return response;
			
		}

		StudentModel student = studentOptional.get();

		if (studentActiveStatus == WebServiceUtil.ACTIVE && studentActiveStatus != student.getStudentActiveStatus()) {

			student.setStudentActiveStatus(studentActiveStatus);
			student.setLasteffectivedate(null);
		} else if (studentActiveStatus == WebServiceUtil.DEACTIVE
				&& studentActiveStatus != student.getStudentActiveStatus()) {

			student.setStudentActiveStatus(studentActiveStatus);
			student.setLasteffectivedate(today);
		} else {
			response.setStatus(WebServiceUtil.WARNING);	
			response.setData(String.format(WebServiceUtil.NO_CHANGES , studentActiveStatus ));			
			return response;
			
			
		}
		studentRequestDao.activeOrDeactiveByStudentId(student);
		response.setStatus(WebServiceUtil.SUCCESS);	
		response.setData(String.format(WebServiceUtil.A_OR_D_STATUS,studentActiveStatus));

		return response;
	}

}
