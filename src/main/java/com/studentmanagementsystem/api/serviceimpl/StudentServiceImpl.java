package com.studentmanagementsystem.api.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

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
	 * Save or update student details.
	 */
	@Override
	public Response saveStudent(StudentDto studentDto) {

		//Logger
		logger.info("Before saveStudent - Attempting to saving the student for TeacherId: {}", studentDto.getTeacherId());

		LocalDateTime today = LocalDateTime.now();
		// Declaration
		StudentModel studentModel;
		int updateFlag = 0;
		
		//Field validation
		Response response = new Response();
		List<String> requestMissedFieldList = fieldValidation.checkValidationStudentSaveMethod(studentDto);
		if (!requestMissedFieldList.isEmpty()) {
			response.setStatus(WebServiceUtil.WARNING);
			response.setData(requestMissedFieldList);
			return response;
		}
		
		//Check whether the teacher ID exists or not
		TeacherModel teacher = teacherRepository.findTeacherByTeacherId(studentDto.getTeacherId());
		if (teacher == null) {
			response.setStatus(WebServiceUtil.WARNING);
			response.setData(WebServiceUtil.TEACHER_ID_ERROR);
			return response;
		}
		
		//Create new student
		if (studentDto.getStudentId() == null) {
			
			// isstudentUnique
			StudentModel isstudentUniqueCheck = studentModelRepository
					.findByFirstNameAndMiddleNameAndLastNameAndDateOfBirth(studentDto.getFirstName(),
							studentDto.getMiddleName(), studentDto.getLastName(), studentDto.getDateOfBirth());

			if (isstudentUniqueCheck != null) {

				response.setStatus(WebServiceUtil.WARNING);
				response.setData(WebServiceUtil.STUDENT_EXISTS);

				return response;
			}
			studentModel = new StudentModel();
			studentModel.setCreateDate(today);
			studentModel.setCreateTeacher(teacher);		
			}
		
		// Update the exists student
		else {
			//Check whether the student ID exists or not
			studentModel = studentModelRepository.findStudentByStudentId(studentDto.getStudentId());
			if (studentModel == null) {
				response.setStatus(WebServiceUtil.FAILURE);
				response.setData(String.format(WebServiceUtil.STUDENT_NOT_FOUND, studentDto.getStudentId()));
				return response;
			}

			studentModel.setUpdateTeacher(teacher);
			studentModel.setUpdateDate(today);
			updateFlag = 1;

		}
		studentModel.setFirstName(studentDto.getFirstName());
		studentModel.setMiddleName(studentDto.getMiddleName());
		studentModel.setLastName(studentDto.getLastName());
		studentModel.setDateOfBirth(studentDto.getDateOfBirth());

		StudentCodeModel gender = studentCodeRespository.findStudentCodeByCode(studentDto.getGender());
		if (gender == null) {
			response.setStatus(WebServiceUtil.WARNING);
			response.setData("invalid");
		}
		studentModel.setGender(gender);
		studentModel.setClassOfStudy(studentDto.getClassOfStudy());
		StudentCodeModel residingStatus = studentCodeRespository.findStudentCodeByCode(studentDto.getResidingStatus());
		System.out.println("Residing status : residingStatus ");
		studentModel.setResidingStatus(residingStatus);
		studentModel.setPhoneNumber(studentDto.getPhoneNumber());
		studentModel.setParentsName(studentDto.getParentsName());
		studentModel.setHomeStreetName(studentDto.getHomeStreetName());
		studentModel.setHomeCityName(studentDto.getHomeCityName());
		studentModel.setHomePostalCode(studentDto.getHomePostalCode());
		studentModel.setEmail(studentDto.getEmail());
		studentModel.setParentsEmail(studentDto.getParentsEmail());

		String studentActiveStatus;
		if (studentDto.getStatus() == null) {
			studentActiveStatus = WebServiceUtil.PRESENT;
		} else {
			studentActiveStatus = studentDto.getStatus();
		}
		StudentCodeModel isStatus = studentCodeRespository.findStudentCodeByCode(studentActiveStatus);
		studentModel.setStatus(isStatus);
		
		studentModelRepository.save(studentModel);

		response.setStatus(WebServiceUtil.SUCCESS);
		if (updateFlag == 0) {
			response.setData( String.format(WebServiceUtil.SAVE ,"Student"));
		} else {
			response.setData(String.format(WebServiceUtil.UPDATE,"Student"));
		}

		logger.info("After saveStudent - Succeccfully saved StudentId: {}", studentDto.getStudentId());
		return response;
	}

	/**
	 * Filter students by id or email or phone number or
	 * residingStatus,status,classOfStudy
	 */
	@Override
	public StudentModelListResponse  getStudentsList(StudentDto studentDto) {
		
		logger.info("Before getStudentsList - Attempting to retrive student details ");
		
		List<StudentDto> studentListRequestDto = studentRequestDao. getStudentsList(studentDto);
		
		StudentModelListResponse response = new StudentModelListResponse();
		response.setStatus(WebServiceUtil.SUCCESS);
		response.setData(studentListRequestDto);
		
		logger.info("After getStudentsList :  Successfully retrived the student details");
		
		return response;
	}

	/**
	 * Activate or deactivate a student by ID.
	 */
	@Override
	public Response activeOrDeactiveByStudentId(String studentActiveStatus, Long studentId, Long teacherId) {
		
		//Logger
		if (studentActiveStatus == WebServiceUtil.ACTIVE)
			logger.info("Before activeOrDeactiveByStudentId : Activate the StudentId : '{}' for TeacherId :{} ", studentId, teacherId);
		else
			logger.info("Before activeOrDeactiveByStudentId : Deactive the StudentId : '{}' for TeacherId :{} ", studentId, teacherId);
		
		// Field validation
		Response response = new Response();
		List<String> requestMissedFieldList = fieldValidation
				.checkValidationActiveOrDeactiveByStudentId(studentActiveStatus, studentId, teacherId);
		if (!requestMissedFieldList.isEmpty()) {
			response.setStatus(WebServiceUtil.WARNING);
			response.setData(requestMissedFieldList);
			return response;
		}

		LocalDateTime today = LocalDateTime.now();
		//Check whether the student ID exists or not
		StudentModel student = studentModelRepository.findStudentByStudentId(studentId);
		if (student == null) {
			response.setStatus(WebServiceUtil.WARNING);
			response.setData(WebServiceUtil.STUDENT_NOT_FOUND + studentId);
			return response;
		}
		
		//Check whether the teacher ID exists or not
		TeacherModel teacher = teacherRepository.findTeacherByTeacherId(teacherId);
		if (teacher == null) {
			response.setStatus(WebServiceUtil.WARNING);
			response.setData(WebServiceUtil.TEACHER_ID_ERROR);
			return response;
		}

		//Active student
		if (!studentActiveStatus.equals(WebServiceUtil.ACTIVE)
				&& !studentActiveStatus.equals(student.getStatus().getCode())) {
			StudentCodeModel isStatus = studentCodeRespository.findStudentCodeByCode(studentActiveStatus);

			student.setStatus(isStatus);
			student.setLasteffectivedate(null);

			student.setUpdateTeacher(teacher);
			student.setUpdateDate(today);

		// Deactivate student
		} else if (!studentActiveStatus.equals(WebServiceUtil.DEACTIVE)
				&& !studentActiveStatus.equals(student.getStatus().getCode())) {

			StudentCodeModel isStatus = studentCodeRespository.findStudentCodeByCode(studentActiveStatus);
			student.setStatus(isStatus);
			student.setLasteffectivedate(today);

			student.setUpdateTeacher(teacher);
			student.setUpdateDate(today);
		} else {
			response.setStatus(WebServiceUtil.WARNING);
			response.setData(String.format(WebServiceUtil.NO_CHANGES, studentActiveStatus));
			return response;

		}

		studentModelRepository.save(student);

		response.setStatus(WebServiceUtil.SUCCESS);
		response.setData(String.format(WebServiceUtil.A_OR_D_STATUS, studentActiveStatus));
		
		if (studentActiveStatus == WebServiceUtil.ACTIVE)
			logger.info("After activeOrDeactiveByStudentId : Successfully activated  StudentId : '{}' for TeacherId :{} ", studentId, teacherId);
		else
			logger.info("After activeOrDeactiveByStudentId :Successfully activated  StudentId : '{}' for TeacherId :{} ", studentId, teacherId);
		
		return response;
	}

}
