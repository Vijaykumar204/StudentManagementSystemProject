package com.studentmanagementsystem.api.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.studentmanagementsystem.api.dao.StudentModelDao;
import com.studentmanagementsystem.api.model.custom.Response;
import com.studentmanagementsystem.api.model.custom.student.StudentDto;
import com.studentmanagementsystem.api.model.custom.student.StudentSaveRequestDto;
import com.studentmanagementsystem.api.model.custom.student.response.StudentModelListResponse;
import com.studentmanagementsystem.api.model.entity.StudentModel;
import com.studentmanagementsystem.api.model.entity.TeacherModel;
import com.studentmanagementsystem.api.repository.StudentModelRepository;
import com.studentmanagementsystem.api.repository.TeacherRepository;
import com.studentmanagementsystem.api.service.StudentService;
import com.studentmanagementsystem.api.util.WebServiceUtil;
import com.studentmanagementsystem.api.validation.FieldValidation;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentModelDao studentRequestDao;


	
	@Autowired
	private FieldValidation fieldValidation;
	
	@Autowired
	private StudentModelRepository studentModelRepository;
	
	@Autowired
	private TeacherRepository teacherRepository;
	
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
		
		StudentModel studentModel = studentModelRepository.findByStudentFirstNameAndStudentMiddleNameAndStudentLastNameAndStudentDateOfBirth(studentSaveRequestDto.getStudentFirstName(),studentSaveRequestDto.getStudentMiddleName(),studentSaveRequestDto.getStudentLastName(),studentSaveRequestDto.getStudentDateOfBirth());
		
		if(studentModel!=null) {
			
			response.setStatus(WebServiceUtil.WARNING);	
			response.setData(WebServiceUtil.STUDENT_EXISTS);
			
			return response;
		}
		
		if (studentSaveRequestDto.getStudentId() == null) {
			student = new StudentModel();
			student.setStudentCreateDate(today);
			TeacherModel teacher = teacherRepository.findTeacherByTeacherId(studentSaveRequestDto.getTeacherId());
			if(teacher == null) {
				response.setStatus(WebServiceUtil.WARNING);	
				response.setData(WebServiceUtil.TEACHER_ID_ERROR);
				return response;
			}
			student.setTeacherModel(teacher);
		}

		else {
		    student = studentModelRepository.findStudentByStudentId(studentSaveRequestDto.getStudentId());
//			Optional<StudentModel> student1 = studentRequestDao.getByStudentId(studentSaveRequestDto.getStudentId());
//			student = student1.get();
		    
		    if(student == null) {
		    	response.setStatus(WebServiceUtil.FAILURE);	
				response.setData(String.format(WebServiceUtil.STUDENT_NOT_FOUND,studentSaveRequestDto.getStudentId()));
				return response;
		    }
			
			
		    TeacherModel teacher = teacherRepository.findTeacherIdByTeacherId(studentSaveRequestDto.getTeacherId());
			if(teacher==null) {
				response.setStatus(WebServiceUtil.WARNING);	
				response.setData(WebServiceUtil.TEACHER_ID_ERROR);
				return response;
			}
			student.setUpdateTeacher(teacher.getTeacherId());
			student.setUpdateDate(today);

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

	/**
	 * Retrive all dayscholar students
	 */
	
	@Override
	public StudentModelListResponse getAllDaysStudents(char studentActiveStatus) {
		
	List<StudentDto> studentListRequestDto = studentRequestDao.getAllHostelStudents(studentActiveStatus, WebServiceUtil.DAYSCHOLAR);
		StudentModelListResponse response = new StudentModelListResponse();
		response.setStatus(WebServiceUtil.SUCCESS);	
		response.setData(studentListRequestDto);		
		return response;
	}

	/**
	 * Filter students by id or email or phone number
	 */
	
	@Override
	public StudentModelListResponse getStudentsBy(Long studentId, String studentEmail, String studentPhoneNumber) {
		List<StudentDto> studentListRequestDto = studentRequestDao.getStudentsBy(studentId, studentEmail, studentPhoneNumber);
		StudentModelListResponse response = new StudentModelListResponse();
		response.setStatus(WebServiceUtil.SUCCESS);	
		response.setData(studentListRequestDto);		
		return response;
	}

	/**
	 * Retrieve the list of students based on active or deactive status.
	 */
	
	@Override
	public StudentModelListResponse getByStudentStatus(char studentActiveStatus) {
		List<StudentDto> studentListRequestDto = studentRequestDao.getByStudentStatus(studentActiveStatus);
		StudentModelListResponse response = new StudentModelListResponse();
		response.setStatus(WebServiceUtil.SUCCESS);	
		response.setData(studentListRequestDto);	
		
		return response;
	}

	/**
	 * Activate or deactivate a student by ID.
	 */
	
	@Override
	public Response activeOrDeactiveByStudentId(Character studentActiveStatus, Long studentId,Long teacherId) {
		
		
		Response response = new Response();
		
		List<String> requestMissedFieldList = fieldValidation.checkValidationActiveOrDeactiveByStudentId(studentActiveStatus,studentId);

		if (!requestMissedFieldList.isEmpty()) {
			response.setStatus(WebServiceUtil.WARNING);	
			response.setData(requestMissedFieldList);
					
			return response;
		}

		LocalDateTime today = LocalDateTime.now();
	    StudentModel  student = studentModelRepository.findStudentByStudentId(studentId);


		if (student==null) {
			response.setStatus(WebServiceUtil.WARNING);	
			response.setData(WebServiceUtil.STUDENT_NOT_FOUND + studentId );
			
			return response;
			
		}
		TeacherModel teacher = teacherRepository.findTeacherIdByTeacherId(teacherId);
		if(teacher==null) {
			response.setStatus(WebServiceUtil.WARNING);	
			response.setData(WebServiceUtil.TEACHER_ID_ERROR);
			return response;
		}

		if (studentActiveStatus == WebServiceUtil.ACTIVE && studentActiveStatus != student.getStudentActiveStatus()) {

			student.setStudentActiveStatus(studentActiveStatus);
			student.setLasteffectivedate(null);

			student.setUpdateTeacher(teacher.getTeacherId());
			student.setUpdateDate(today);

			
		} else if (studentActiveStatus == WebServiceUtil.DEACTIVE
				&& studentActiveStatus != student.getStudentActiveStatus()) {

			student.setStudentActiveStatus(studentActiveStatus);
			student.setLasteffectivedate(today);

			student.setUpdateTeacher(teacher.getTeacherId());
			student.setUpdateDate(today);
			
		} else {
			response.setStatus(WebServiceUtil.WARNING);	
			response.setData(String.format(WebServiceUtil.NO_CHANGES , studentActiveStatus ));			
			return response;

		}
		
		studentModelRepository.save(student);
		
		response.setStatus(WebServiceUtil.SUCCESS);	
		response.setData(String.format(WebServiceUtil.A_OR_D_STATUS,studentActiveStatus));

		return response;
	}

}
