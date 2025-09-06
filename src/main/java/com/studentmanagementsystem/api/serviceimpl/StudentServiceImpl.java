package com.studentmanagementsystem.api.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.studentmanagementsystem.api.dao.StudentModelDao;
import com.studentmanagementsystem.api.model.custom.Response;
import com.studentmanagementsystem.api.model.custom.student.StudentDto;

import com.studentmanagementsystem.api.model.custom.student.response.StudentModelListResponse;
import com.studentmanagementsystem.api.model.entity.StudentCodeModel;
import com.studentmanagementsystem.api.model.entity.StudentModel;
import com.studentmanagementsystem.api.model.entity.TeacherModel;
import com.studentmanagementsystem.api.repository.StudentCodeRespository;
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
	
	@Autowired
	private StudentCodeRespository studentCodeRespository;
	
	/**
	 * Retrieve the list of all student details.
	 */
	@Override
	public StudentModelListResponse listAllDetailsStudent() {
		
		List<StudentDto> studentDto = studentRequestDao.listAllDetailsStudent();
		StudentModelListResponse response = new StudentModelListResponse();
		response.setStatus(WebServiceUtil.SUCCESS);	
		response.setData(studentDto);

		return response;
	}

	/**
	 * Save or update student details.
	 */
	@Override
	public Response saveStudent(StudentDto studentDto) {
		
		Response response = new Response();

		LocalDateTime today = LocalDateTime.now();

		StudentModel studentModel;

		List<String> requestMissedFieldList= fieldValidation.checkValidationStudentSaveMethod(studentDto);

		if (!requestMissedFieldList.isEmpty()) {
			response.setStatus(WebServiceUtil.WARNING);	
			response.setData(requestMissedFieldList);		
			return response;
		}
		//isstudentUnique 
		StudentModel isstudentUniqueCheck = studentModelRepository.findByStudentFirstNameAndStudentMiddleNameAndStudentLastNameAndStudentDateOfBirth(studentDto.getStudentFirstName(),studentDto.getStudentMiddleName(),studentDto.getStudentLastName(),studentDto.getStudentDateOfBirth());
		
		if(isstudentUniqueCheck!=null) {
			
			response.setStatus(WebServiceUtil.WARNING);	
			response.setData(WebServiceUtil.STUDENT_EXISTS);
			
			return response;
		}
		
		if (studentDto.getStudentId() == null) {
			studentModel = new StudentModel();
			studentModel.setStudentCreateDate(today);
			TeacherModel teacher = teacherRepository.findTeacherByTeacherId(studentDto.getTeacherId());
			if(teacher == null) {
				response.setStatus(WebServiceUtil.WARNING);	
				response.setData(WebServiceUtil.TEACHER_ID_ERROR);
				return response;
			}
			studentModel.setTeacherModel(teacher);
		}

		else {
		    studentModel = studentModelRepository.findStudentByStudentId(studentDto.getStudentId());
		    
		    if(studentModel == null) {
		    	response.setStatus(WebServiceUtil.FAILURE);	
				response.setData(String.format(WebServiceUtil.STUDENT_NOT_FOUND,studentDto.getStudentId()));
				return response;
		    }
			
			
		    TeacherModel teacher = teacherRepository.findTeacherIdByTeacherId(studentDto.getTeacherId());
			if(teacher==null) {
				response.setStatus(WebServiceUtil.WARNING);	
				response.setData(WebServiceUtil.TEACHER_ID_ERROR);
				return response;
			}
			studentModel.setUpdateTeacher(teacher.getTeacherId());
			studentModel.setUpdateDate(today);

		}
		studentModel.setStudentFirstName(studentDto.getStudentFirstName());
		studentModel.setStudentMiddleName(studentDto.getStudentMiddleName());
		studentModel.setStudentLastName(studentDto.getStudentLastName());
		studentModel.setStudentDateOfBirth(studentDto.getStudentDateOfBirth());
		
		StudentCodeModel gender = studentCodeRespository.findStudentCodeByCode(studentDto.getStudentGender());
		if(gender == null) {
			response.setStatus(WebServiceUtil.WARNING);	
			response.setData("invalid");
		}
		studentModel.setStudentGender(gender);
		studentModel.setStudentClassOfStudy(studentDto.getStudentClassOfStudy());
		
		StudentCodeModel residingStatus = studentCodeRespository.findStudentCodeByCode(studentDto.getStudentResidingStatus());
		studentModel.setStudentResidingStatus(residingStatus);
		studentModel.setStudentPhoneNumber(studentDto.getStudentPhoneNumber());
		studentModel.setEmergencyContactPersonName(studentDto.getEmergencyContactPersonName());
		studentModel.setEmergencyContactPhoneNumber(studentDto.getEmergencyContactPhoneNumber());
		studentModel.setHomeStreetName(studentDto.getHomeStreetName());
		studentModel.setHomeCityName(studentDto.getHomeCityName());
		studentModel.setHomePostalCode(studentDto.getHomePostalCode());
		studentModel.setStudentEmail(studentDto.getStudentEmail());
		String studentActiveStatus;
		if(studentDto.getStudentActiveStatus() == null) {
			 studentActiveStatus = WebServiceUtil.PRESENT;
		}
		else {
			 studentActiveStatus =studentDto.getStudentActiveStatus();
		}
		StudentCodeModel isStatus = studentCodeRespository.findStudentCodeByCode(studentActiveStatus);

		studentModel.setStudentActiveStatus(isStatus);
		
		studentModelRepository.save(studentModel);
		
		response.setStatus(WebServiceUtil.SUCCESS);	
		response.setData(WebServiceUtil.SAVE);
		
		return response;
	}

	/**
	 * Retrive all hostel students
	 */
	@Override
	public StudentModelListResponse getAllHostelStudents(String studentActiveStatus) {
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
	public StudentModelListResponse getAllDaysStudents(String studentActiveStatus) {
		
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
	public StudentModelListResponse getByStudentStatus(String studentActiveStatus) {
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
	public Response activeOrDeactiveByStudentId(String studentActiveStatus, Long studentId,Long teacherId) {
		
		
		Response response = new Response();
		
		List<String> requestMissedFieldList = fieldValidation.checkValidationActiveOrDeactiveByStudentId(studentActiveStatus,studentId,teacherId);

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

		if (studentActiveStatus == WebServiceUtil.ACTIVE &&  !studentActiveStatus.equals(student.getStudentActiveStatus().getCode())) {
			StudentCodeModel isStatus = studentCodeRespository.findStudentCodeByCode(studentActiveStatus);

			student.setStudentActiveStatus(isStatus);
			student.setLasteffectivedate(null);

			student.setUpdateTeacher(teacher.getTeacherId());
			student.setUpdateDate(today);

			
		} else if (studentActiveStatus == WebServiceUtil.DEACTIVE
				&&  !studentActiveStatus.equals(student.getStudentActiveStatus().getCode())) {
			
			StudentCodeModel isStatus = studentCodeRespository.findStudentCodeByCode(studentActiveStatus);
			student.setStudentActiveStatus(isStatus);
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
