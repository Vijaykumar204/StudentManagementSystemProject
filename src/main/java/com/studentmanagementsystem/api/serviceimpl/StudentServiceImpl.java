package com.studentmanagementsystem.api.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.studentmanagementsystem.api.dao.StudentDao;
import com.studentmanagementsystem.api.model.custom.CommonFilterDto;
import com.studentmanagementsystem.api.model.custom.MessageResponse;
import com.studentmanagementsystem.api.model.custom.Response;
import com.studentmanagementsystem.api.model.custom.student.StudentDto;
import com.studentmanagementsystem.api.model.entity.StudentCodeModel;
import com.studentmanagementsystem.api.model.entity.StudentModel;
import com.studentmanagementsystem.api.model.entity.TeacherModel;
import com.studentmanagementsystem.api.repository.StudentCodeRespository;
import com.studentmanagementsystem.api.repository.StudentRepository;
import com.studentmanagementsystem.api.repository.TeacherRepository;
import com.studentmanagementsystem.api.service.StudentService;
import com.studentmanagementsystem.api.util.WebServiceUtil;
import com.studentmanagementsystem.api.validation.FieldValidation;
import com.studentmanagementsystem.api.validation.UniqueValidation;

import jakarta.transaction.Transactional;


@Service
public class StudentServiceImpl implements StudentService {

	private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

	@Autowired
	private StudentDao studentRequestDao;

	@Autowired
	private FieldValidation fieldValidation;
	
	@Autowired
	private UniqueValidation uniqueValidation;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private TeacherRepository teacherRepository;

	@Autowired
	private StudentCodeRespository studentCodeRespository;
	

	/**
	 * Save or update student details.
	 */
	@Override
	@Transactional
	public MessageResponse saveStudent(StudentDto studentDto) {
		
		//Logger
		logger.info("Before saveStudent - Attempting to saving the student for TeacherId: {}", studentDto.getTeacherId());

		LocalDateTime today = LocalDateTime.now();
		// Declaration
		StudentModel studentModel;
		
		//Field validation
		MessageResponse response = new MessageResponse();
		List<String> requestFieldList = fieldValidation.studentFieldValidation(studentDto);
		if (!requestFieldList.isEmpty()) {
			response.setStatus(WebServiceUtil.WARNING);
			response.setData(requestFieldList);
			return response;
		}
		
		//Verify if the teacher ID exists
		TeacherModel teacher = teacherRepository.findTeacherByTeacherId(studentDto.getTeacherId());
		if (teacher == null) {
			response.setStatus(WebServiceUtil.WARNING);
			response.setData(WebServiceUtil.TEACHER_ID_ERROR);
			return response;
		}
		//Create new student
		if (studentDto.getStudentId() == null) {
			//Unique vaildation
			List<String> uniqueField =uniqueValidation.uniqueCheckStudentSave(studentDto);
			if(!uniqueField.isEmpty()) {
				response.setStatus(WebServiceUtil.WARNING);
				response.setData(uniqueField);
				return response;
			}
			studentModel = new StudentModel();
			studentModel.setCreateDate(today);
			studentModel.setCreateTeacher(teacher);	
			studentModel.setStatus(studentCodeRespository.findStudentCodeByCode(studentDto.getStatus()));
			response.setData( String.format(WebServiceUtil.SAVE ,"Student"));
			logger.info("After saveStudent - Succeccfully saved Student");
			}
		
		// Update the exists student
		else {
			//Unique Vaildation
			List<String> uniqueField =uniqueValidation.uniqueCheckStudentUpdate(studentDto);
			if(!uniqueField.isEmpty()) {
				response.setStatus(WebServiceUtil.WARNING);
				response.setData(uniqueField);
				return response;
			}	
			//Verify if student ID exists
			studentModel = studentRepository.findStudentByStudentId(studentDto.getStudentId());
			if (studentModel == null) {
				response.setStatus(WebServiceUtil.FAILURE);
				response.setData(String.format(WebServiceUtil.STUDENT_NOT_FOUND, studentDto.getStudentId()));
				return response;
			}

			studentModel.setUpdateTeacher(teacher);
			studentModel.setUpdateDate(today);
			response.setData(String.format(WebServiceUtil.UPDATE,"Student"));
			logger.info("After saveStudent - Succeccfully Updates StudentId: {}", studentDto.getStudentId());
		}
		studentModel.setFirstName(studentDto.getFirstName());
		studentModel.setMiddleName(studentDto.getMiddleName());
		studentModel.setLastName(studentDto.getLastName());
		studentModel.setDateOfBirth(studentDto.getDateOfBirth());
		studentModel.setClassOfStudy(studentDto.getClassOfStudy());
		studentModel.setGender(studentCodeRespository.findStudentCodeByCode(studentDto.getGender()));
		studentModel.setResidingStatus(studentCodeRespository.findStudentCodeByCode(studentDto.getResidingStatus()));
		studentModel.setPhoneNumber(studentDto.getPhoneNumber());
		studentModel.setParentsName(studentDto.getParentsName());
		studentModel.setHomeStreetName(studentDto.getHomeStreetName());
		studentModel.setHomeCityName(studentDto.getHomeCityName());
		studentModel.setHomePostalCode(studentDto.getHomePostalCode());
		studentModel.setEmail(studentDto.getEmail());
		studentModel.setParentsEmail(studentDto.getParentsEmail());
		
	    studentRepository.save(studentModel);
		response.setStatus(WebServiceUtil.SUCCESS);
		return response;
	}

	/**
	 * Retrive list of student details
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Response listStudentDetails(CommonFilterDto filterDto) {

		logger.info("Before listStudentDetails - Attempting to retrive student details ");

		Map<String, Object> result = studentRequestDao.listStudentDetails(filterDto);

		List<StudentDto> studentList = (List<StudentDto>) result.get("data");

		Long totalCount = studentRepository.findTotalCount(filterDto.getClassOfStudy());

		if (studentList != null) {

			if (WebServiceUtil.S_NO.equals(filterDto.getOrderColumn())
					&& WebServiceUtil.DESCENDING_ORDER.equals(filterDto.getOrderType())) {
				int sno = studentList.size();
				for (StudentDto student : studentList) {
					student.setSno(sno--);
				}
			} else {
				int sno = 1;
				for (StudentDto student : studentList) {
					student.setSno(sno++);
				}
			}
		}

		Response response = new Response();
		response.setStatus(WebServiceUtil.SUCCESS);
		response.setDraw(filterDto.getDraw());
		response.setTotalCount(totalCount);
		response.setFilterCount((Long) result.get("filterCount"));
		response.setData(studentList);

		logger.info("After listStudentDetails :  Successfully retrived the student details");

		return response;
	}

	/**
	 * Activate or deactivate a student by ID.
	 */
	@Override
	@Transactional
	public MessageResponse activeOrDeactiveByStudentId(String status, Long studentId, Long teacherId) {

		// Logger
		if (status == WebServiceUtil.ACTIVE)
			logger.info("Before activeOrDeactiveByStudentId : Activate the StudentId : '{}' for TeacherId :{} ",
					studentId, teacherId);
		else
			logger.info("Before activeOrDeactiveByStudentId : Deactive the StudentId : '{}' for TeacherId :{} ",
					studentId, teacherId);
		// Field validation
		MessageResponse response = new MessageResponse();
		List<String> requestFieldList = fieldValidation.statusFieldValidation(status, studentId, teacherId);
		if (!requestFieldList.isEmpty()) {
			response.setStatus(WebServiceUtil.WARNING);
			response.setData(requestFieldList);
			return response;
		}

		LocalDateTime today = LocalDateTime.now();
		// Verify if the student ID exists
		StudentModel student = studentRepository.findStudentByStudentId(studentId);
		if (student == null) {
			response.setStatus(WebServiceUtil.WARNING);
			response.setData(WebServiceUtil.STUDENT_NOT_FOUND + studentId);
			return response;
		}

		// Verify if the teacher ID exists
		TeacherModel teacher = teacherRepository.findTeacherByTeacherId(teacherId);
		if (teacher == null) {
			response.setStatus(WebServiceUtil.WARNING);
			response.setData(WebServiceUtil.TEACHER_ID_ERROR);
			return response;
		}
		// Deactivate student

		if (status.equals(WebServiceUtil.DEACTIVE)) {

			if (!status.equals(student.getStatus().getCode())) {

				StudentCodeModel activeStatus = studentCodeRespository.findStudentCodeByCode(status);

				student.setStatus(activeStatus);
				student.setLastEffectiveDate(today);
				student.setUpdateTeacher(teacher);
				student.setUpdateDate(today);
				response.setData(WebServiceUtil.DEACTIVETED_MESSAGE);
				logger.info(
						"After activeOrDeactiveByStudentId : Successfully deactivated  StudentId : '{}' for TeacherId :{} ",
						studentId, teacherId);
			} else {
				response.setData(WebServiceUtil.DEACTIVETED_MESSAGE);
				logger.info(
						"After activeOrDeactiveByStudentId : Successfully deactivated  StudentId : '{}' for TeacherId :{} ",
						studentId, teacherId);
			}

			// Active student
		} else if (status.equals(WebServiceUtil.ACTIVE)) {

			if (!status.equals(student.getStatus().getCode())) {

				StudentCodeModel activeStatus = studentCodeRespository.findStudentCodeByCode(status);
				student.setStatus(activeStatus);
				student.setLastEffectiveDate(null);
				student.setUpdateTeacher(teacher);
				student.setUpdateDate(today);
				response.setData(WebServiceUtil.ACTIVETED_MESSAGE);
				logger.info(
						"After activeOrDeactiveByStudentId : Successfully activated  StudentId : '{}' for TeacherId :{} ",
						studentId, teacherId);
			} else {
				response.setData(WebServiceUtil.ACTIVETED_MESSAGE);
				logger.info(
						"After activeOrDeactiveByStudentId : Successfully activated  StudentId : '{}' for TeacherId :{} ",
						studentId, teacherId);

			}
		}

		studentRepository.save(student);

		response.setStatus(WebServiceUtil.SUCCESS);
		return response;
	}

}
