package com.studentmanagementsystem.api.serviceimpl;

import java.time.LocalDateTime;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.studentmanagementsystem.api.dao.StudentMarksDao;
import com.studentmanagementsystem.api.model.custom.Response;
import com.studentmanagementsystem.api.model.custom.studentmarks.StudentMarksDto;
import com.studentmanagementsystem.api.model.custom.studentmarks.response.StudentMarkListResponse;
import com.studentmanagementsystem.api.model.custom.studentmarks.response.StudentWithPassOrFailListResponse;
import com.studentmanagementsystem.api.model.custom.studentmarks.response.TotalResultCountListResponse;
import com.studentmanagementsystem.api.model.entity.MarkModel;
import com.studentmanagementsystem.api.model.entity.StudentCodeModel;
import com.studentmanagementsystem.api.model.entity.StudentModel;
import com.studentmanagementsystem.api.model.entity.TeacherModel;
import com.studentmanagementsystem.api.repository.QuarterlyAttendanceModelRepository;
import com.studentmanagementsystem.api.repository.StudentCodeRespository;
import com.studentmanagementsystem.api.repository.StudentMarksRepository;
import com.studentmanagementsystem.api.repository.StudentModelRepository;
import com.studentmanagementsystem.api.repository.TeacherRepository;
import com.studentmanagementsystem.api.service.StudentMarksService;
import com.studentmanagementsystem.api.util.WebServiceUtil;
import com.studentmanagementsystem.api.validation.FieldValidation;

@Service
public class StudentMarksServiceImpl implements StudentMarksService {
	@Autowired
	private StudentMarksDao studentMarksDao;

@Autowired
private StudentModelRepository studentModelRepository;

	
	@Autowired
	private FieldValidation fieldValidation;
	
	@Autowired
	private StudentMarksRepository studentMarksRepository;
	
	@Autowired
	private QuarterlyAttendanceModelRepository quarterlyAttendanceModelRepository;
	
	@Autowired
	private TeacherRepository teacherRepository;
	
	@Autowired
	private StudentCodeRespository studentCodeRespository;
	

	/**
	 * Declare the marks of a student.
	 */
	
	@Override
	public Response saveStudentMarks(List<StudentMarksDto> studentMarksDto) {

	
		Response response = new Response();
		LocalDateTime today = LocalDateTime.now();
		MarkModel studentMark;
		for (StudentMarksDto mark : studentMarksDto) {

		
			List<String> requestMissedFieldList = fieldValidation.checkValidationStudentMarkSave(mark);
			if (!requestMissedFieldList.isEmpty()) {
				response.setStatus(WebServiceUtil.WARNING);	
				response.setData(requestMissedFieldList);		
				return response;
			}

			String ComplianceStatus = quarterlyAttendanceModelRepository.findAttendanceComplianceStatusByStudentIdandquarterAndYear(mark.getStudentId(),
					mark.getQuarterAndYear());
			
			if(ComplianceStatus == WebServiceUtil.NON_COMPLIANCE) {
				response.setStatus(WebServiceUtil.WARNING);	
				response.setData(WebServiceUtil.STUDENT_ATTENDANCE_STATUS);
				return response;
			}
			
			studentMark = studentMarksRepository.findByStudentIdAndQuarterAndYear(mark.getStudentId(),
					mark.getQuarterAndYear());
			TeacherModel teacher = teacherRepository.findTeacherByTeacherId(mark.getTeacherId());
			if(teacher==null) {
				response.setStatus(WebServiceUtil.WARNING);	
				response.setData(WebServiceUtil.TEACHER_ID_ERROR);
				return response;
			}
			
			if (studentMark == null) {
				studentMark = new MarkModel();
				StudentModel student = studentModelRepository.findStudentByStudentId(mark.getStudentId());
				
				
				studentMark.setStudentModel(student);
				studentMark.setQuarterAndYear(mark.getQuarterAndYear());

				studentMark.setCreateDate(today);
//				studentMark.setCreateTeacher(teacher.getTeacherId());
				studentMark.setTeacherModel(teacher);
			} else {
				
				studentMark.setUpdateTeacher(teacher.getTeacherId());
				studentMark.setUpdateTime(today);
			}

			Integer tamil = studentMark.setTamil(mark.getTamil());
			Integer english = studentMark.setEnglish(mark.getEnglish());
			Integer maths = studentMark.setMaths(mark.getMaths());
			Integer science = studentMark.setScience(mark.getScience());
			Integer socialscience = studentMark.setSocialScience(mark.getSocialScience());

			Integer totalMark = tamil + english + maths + science + socialscience;
			studentMark.setTotalMarks(totalMark);


		//	String compliance = quarterlyAttendanceReportDao.getComplianceStatus(mark.getStudentId(),
	//				mark.getQuarterAndYear());

			boolean allSubjectsPassed = tamil >= 35 && english >= 35 && maths >= 35 && science >= 35
					&& socialscience >= 35;

			if (allSubjectsPassed ) {
				StudentCodeModel resultStatus = studentCodeRespository.findStudentCodeByCode(WebServiceUtil.PASS);
				studentMark.setResult(resultStatus);
			} else {
				if (allSubjectsPassed != true) {
					studentMark.setFailedForMark(true);
				}
				StudentCodeModel resultStatus = studentCodeRespository.findStudentCodeByCode(WebServiceUtil.FAIL);

				studentMark.setResult(resultStatus);
			}


			studentMarksRepository.save(studentMark);
			response.setStatus(WebServiceUtil.SUCCESS);	
			response.setData(WebServiceUtil.DECLARE_MARK);

//			emailSentService.sendQuarterlyResultReportEmail(List<StudentMarksDto> studentMarksDto);

		}

		return response;
	}
	
	/**
	 * Retrieve the list of students with their result status (pass or fail) 
	 * for a given quarter and year.
	 */
	@Override
	public StudentWithPassOrFailListResponse getAllComplianceStudentPassOrFail(String quarterAndYear) {
		StudentWithPassOrFailListResponse response = new StudentWithPassOrFailListResponse();
		response.setStatus(WebServiceUtil.SUCCESS);	
		response.setData(studentMarksDao.getAllComplianceStudentPassOrFail(quarterAndYear));
		

		return response;
	}

	/**
	 * Retrieve the list of all student marks for a given quarter and year.
	 */
	@Override
	public StudentMarkListResponse getAllStudentMarks(String quarterAndYear,Boolean resultStatus, int classOfStudy) {
		StudentMarkListResponse response = new StudentMarkListResponse();
		response.setStatus(WebServiceUtil.SUCCESS);	
		response.setData(studentMarksDao.getAllStudentMarks(quarterAndYear,resultStatus,classOfStudy));
		

		return response;
	}

	/**
	 * Retrieve the overall result summary for students in a given quarter and year.
	 */
	@Override
	public TotalResultCountListResponse getToatalResultCount(String quarterAndYear) {
		TotalResultCountListResponse response =new  TotalResultCountListResponse();
		response.setStatus(WebServiceUtil.SUCCESS);	
		response.setData(studentMarksDao.getToatalResultCount(quarterAndYear));
		return response;
	}
	
	/**
	 * Retrieve the class topper in a given quarter and Year.
	 */
	@Override
	public Response getClassTopper(String quarterAndYear) {
		Response response = new Response();
		response.setStatus(WebServiceUtil.SUCCESS);	
		response.setData(studentMarksDao.getClassTopper(quarterAndYear));

		return response;
	}

}
