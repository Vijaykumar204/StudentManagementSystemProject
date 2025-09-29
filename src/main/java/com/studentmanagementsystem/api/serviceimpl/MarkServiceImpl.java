package com.studentmanagementsystem.api.serviceimpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.studentmanagementsystem.api.dao.MarkDao;
import com.studentmanagementsystem.api.model.custom.CommonFilterDto;
import com.studentmanagementsystem.api.model.custom.MessageResponse;
import com.studentmanagementsystem.api.model.custom.Response;
import com.studentmanagementsystem.api.model.custom.studentmarks.markDto;
import com.studentmanagementsystem.api.model.entity.MarkModel;
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
import jakarta.transaction.Transactional;

@Service
public class MarkServiceImpl implements MarkService {
	
	private static final Logger logger = LoggerFactory.getLogger(MarkServiceImpl.class);

	@Autowired
	private MarkDao markDao;

	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private FieldValidation fieldValidation;
	
	@Autowired
	private StudentMarksRepository markRepository;
	
	@Autowired
	private QuarterlyAttendanceRepository quarterlyAttendanceRepository;
	
	@Autowired
	private TeacherRepository teacherRepository;
	
	@Autowired
	private StudentCodeRespository studentCodeRespository;
	

	/**
	 * Declare the marks of a student.
	 */
	@Override
	@Transactional
	public MessageResponse saveStudentMarks(List<markDto> studentMarksDto) {

		logger.info("Before saveStudentMarks - Attempting to saving the student marks");
		MessageResponse response = new MessageResponse();
		LocalDateTime today = LocalDateTime.now();
		MarkModel studentMark;
		List<MarkModel> markList = new ArrayList<>();
		for (markDto mark : studentMarksDto) {

			List<String> requestMissedFieldList = fieldValidation.checkValidationStudentMarkSave(mark);
			if (!requestMissedFieldList.isEmpty()) {
				response.setStatus(WebServiceUtil.WARNING);
				response.setData(requestMissedFieldList);
				return response;
			}

			String ComplianceStatus = quarterlyAttendanceRepository
					.findAttendanceComplianceStatusByStudentIdandquarterAndYear(mark.getStudentId(),
							mark.getQuarterAndYear());
			if (ComplianceStatus == WebServiceUtil.NON_COMPLIANCE) {
				response.setStatus(WebServiceUtil.WARNING);
				response.setData(WebServiceUtil.STUDENT_ATTENDANCE_STATUS);
				return response;
			}

			studentMark = markRepository.findByStudentIdAndQuarterAndYear(mark.getStudentId(),
					mark.getQuarterAndYear());
			TeacherModel teacher = teacherRepository.findTeacherByTeacherId(mark.getTeacherId());
			if (teacher == null) {
				response.setStatus(WebServiceUtil.WARNING);
				response.setData(WebServiceUtil.TEACHER_ID_ERROR);
				return response;
			}

			if (studentMark == null) {
				studentMark = new MarkModel();
				StudentModel student = studentRepository.findStudentByStudentId(mark.getStudentId());
				studentMark.setStudentModel(student);
				studentMark.setQuarterAndYear(mark.getQuarterAndYear());
				studentMark.setCreateDate(today);
				studentMark.setCreateTeacher(teacher);
				response.setData(WebServiceUtil.DECLARE_MARK);
				logger.info("After saveStudentMarks - Successfully saved student marks");

			} else {

				studentMark.setUpdateTeacher(teacher);
				studentMark.setUpdateTime(today);
				response.setData(WebServiceUtil.UPDATE_MARK);
				logger.info("After saveStudentMarks - Successfully updated student marks");
			}

			Integer tamil = studentMark.setTamil(mark.getTamil());
			Integer english = studentMark.setEnglish(mark.getEnglish());
			Integer maths = studentMark.setMaths(mark.getMaths());
			Integer science = studentMark.setScience(mark.getScience());
			Integer socialscience = studentMark.setSocialScience(mark.getSocialScience());

			Integer totalMark = tamil + english + maths + science + socialscience;
			studentMark.setTotalMarks(totalMark);

			Integer percentage = totalMark / 5;
			studentMark.setPercentage(percentage);

			boolean allSubjectsPassed = tamil >= 35 && english >= 35 && maths >= 35 && science >= 35
					&& socialscience >= 35;

			if (allSubjectsPassed) {
				studentMark.setResult(studentCodeRespository.findStudentCodeByCode(WebServiceUtil.PASS));
			} else {
				studentMark.setResult(studentCodeRespository.findStudentCodeByCode(WebServiceUtil.FAIL));
			}
			markList.add(studentMark);
		}
		markRepository.saveAll(markList);
		response.setStatus(WebServiceUtil.SUCCESS);
		return response;
	}
	

	/**
	 * Retrieve the list of all student marks for a given quarter and year.
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Response listStudentMarks(CommonFilterDto filterDto) {
		logger.info("Before listStudentMarks - Attempting to retriving the student mark list  ");

		Map<String, Object> result = markDao.listStudentMarks(filterDto);

		List<markDto> markList = (List<markDto>) result.get("data");

		Long totalCount = markRepository.findTotalCount(filterDto.getClassOfStudy(), filterDto.getQuarterAndYear());

		if (markList != null) {

			if (WebServiceUtil.S_NO.equals(filterDto.getOrderColumn())
					&& WebServiceUtil.DESCENDING_ORDER.equals(filterDto.getOrderType())) {
				int sno = markList.size();
				for (markDto mark : markList) {
					mark.setSno(sno--);
				}
			} else {
				int sno = 1;
				for (markDto student : markList) {
					student.setSno(sno++);
				}
			}
		}

		Response response = new Response();
		response.setStatus(WebServiceUtil.SUCCESS);
		response.setDraw(filterDto.getDraw());
		response.setFilterCount((Long) result.get("filterCount"));
		response.setTotalCount(totalCount);
		response.setData(markList);
		logger.info("After listStudentMarks - Successfully retrived the student mark list  ");
		return response;
	}

	/*
	 * Retrive mark summary report
	 */
	@Override
	@Transactional
	public Response resultSummaryReport(CommonFilterDto filterDto) {
		logger.info("Before getAllStudentMarks - Attempting to retriving the mark summary report  ");
		Response response = new Response();
		response.setStatus(WebServiceUtil.SUCCESS);
		response.setData(markDao.resultSummaryReport(filterDto));
		logger.info("After getAllStudentMarks - Successfully retrived the mark summary report  ");
		return response;
	}
}
