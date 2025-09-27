package com.studentmanagementsystem.api.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.studentmanagementsystem.api.dao.MarkDao;
import com.studentmanagementsystem.api.model.custom.CommonFilterDto;
import com.studentmanagementsystem.api.model.custom.Response;
import com.studentmanagementsystem.api.model.custom.studentmarks.StudentMarksDto;
import com.studentmanagementsystem.api.model.entity.MarkModel;
import com.studentmanagementsystem.api.model.entity.StudentCodeModel;
import com.studentmanagementsystem.api.model.entity.StudentModel;
import com.studentmanagementsystem.api.model.entity.TeacherModel;
import com.studentmanagementsystem.api.repository.QuarterlyAttendanceRepository;
import com.studentmanagementsystem.api.repository.StudentCodeRespository;
import com.studentmanagementsystem.api.repository.StudentMarksRepository;
import com.studentmanagementsystem.api.repository.StudentRepository;
import com.studentmanagementsystem.api.repository.TeacherRepository;
import com.studentmanagementsystem.api.service.MarkService;
import com.studentmanagementsystem.api.util.WebServiceUtil;
import com.studentmanagementsystem.api.validation.FieldValidation;

@Service
public class MarkServiceImpl implements MarkService {
	
	private static final Logger logger = LoggerFactory.getLogger(MarkServiceImpl.class);

	@Autowired
	private MarkDao studentMarksDao;

	@Autowired
	private StudentRepository studentModelRepository;
	
	@Autowired
	private FieldValidation fieldValidation;
	
	@Autowired
	private StudentMarksRepository studentMarksRepository;
	
	@Autowired
	private QuarterlyAttendanceRepository quarterlyAttendanceModelRepository;
	
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
			logger.info("Before saveStudentMarks - Attempting to saving the student marks for TeacherId: {}",mark.getTeacherId());
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
				studentMark.setCreateTeacher(teacher);
			} else {
				
				studentMark.setUpdateTeacher(teacher);
				studentMark.setUpdateTime(today);
			}
			
			Integer tamil = studentMark.setTamil(mark.getTamil());
			Integer english = studentMark.setEnglish(mark.getEnglish());
			Integer maths = studentMark.setMaths(mark.getMaths());
			Integer science = studentMark.setScience(mark.getScience());
			Integer socialscience = studentMark.setSocialScience(mark.getSocialScience());

			Integer totalMark = tamil + english + maths + science + socialscience;
			studentMark.setTotalMarks(totalMark);
			
			Integer percentage = totalMark/5;
			
			studentMark.setPercentage(percentage);
			
			
			boolean allSubjectsPassed = tamil >= 35 && english >= 35 && maths >= 35 && science >= 35
					&& socialscience >= 35;
							
	
			if (allSubjectsPassed ) {
				
				StudentCodeModel resultStatus = studentCodeRespository.findStudentCodeByCode(WebServiceUtil.PASS);
				
				studentMark.setResult(resultStatus);
			} else {			
				StudentCodeModel resultStatus = studentCodeRespository.findStudentCodeByCode(WebServiceUtil.FAIL);
				
				studentMark.setResult(resultStatus);
			}

			studentMarksRepository.save(studentMark);
			response.setStatus(WebServiceUtil.SUCCESS);	
			response.setData(WebServiceUtil.DECLARE_MARK);
			
			logger.info("After saveStudentMarks - Successfully saved student marks");


		}

		return response;
	}
	

	/**
	 * Retrieve the list of all student marks for a given quarter and year.
	 */
	@Override
	public Response listStudentMarks(CommonFilterDto filterDto) {
		logger.info("Before getAllStudentMarks - Attempting to retriving the student mark list  ");
		List<StudentMarksDto> markList = studentMarksDao.getAllStudentMarks(filterDto);
		
		Long totalCount = studentMarksRepository.findTotalCount(filterDto.getClassOfStudy(),filterDto.getQuarterAndYear());
		int sno=1;
		
		for(StudentMarksDto mark : markList) {
			mark.setSno(sno++);
		}
		
		Response response = new Response();
		response.setStatus(WebServiceUtil.SUCCESS);	
		response.setDraw(filterDto.getDraw());
		response.setFilterCount((long) (sno-1));
		response.setTotalCount(totalCount);
		response.setData(markList);
		
		logger.info("After getAllStudentMarks - Successfully retrived the student mark list  ");
		return response;
	}


	@Override
	public Response resultSummaryReport(CommonFilterDto filterDto) {
		logger.info("Before getAllStudentMarks - Attempting to retriving the mark summary report  ");
		Response response = new Response();
		response.setStatus(WebServiceUtil.SUCCESS);
		response.setData(studentMarksDao.resultSummaryReport(filterDto));
		logger.info("After getAllStudentMarks - Successfully retrived the mark summary report  ");
		return response;
	}
}
