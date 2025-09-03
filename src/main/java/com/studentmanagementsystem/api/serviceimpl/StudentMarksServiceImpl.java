package com.studentmanagementsystem.api.serviceimpl;

import java.time.LocalDateTime;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentmanagementsystem.api.dao.QuarterlyAttendanceReportDao;
import com.studentmanagementsystem.api.dao.StudentMarksDao;
import com.studentmanagementsystem.api.dao.StudentModelDao;
import com.studentmanagementsystem.api.model.custom.Response;
import com.studentmanagementsystem.api.model.custom.studentmarks.ClassTopperDto;
import com.studentmanagementsystem.api.model.custom.studentmarks.ComplianceStudentWithPassOrFail;
import com.studentmanagementsystem.api.model.custom.studentmarks.StudentMarksDto;
import com.studentmanagementsystem.api.model.custom.studentmarks.TotalResultCountdto;
import com.studentmanagementsystem.api.model.entity.MarkModel;
import com.studentmanagementsystem.api.model.entity.StudentModel;
import com.studentmanagementsystem.api.service.StudentMarksService;
import com.studentmanagementsystem.api.util.WebServiceUtil;
import com.studentmanagementsystem.api.validation.FieldValidation;

@Service
public class StudentMarksServiceImpl implements StudentMarksService {
	@Autowired
	private StudentMarksDao studentMarksDao;

	@Autowired
	private StudentModelDao studentRequestDao;

	@Autowired
	private QuarterlyAttendanceReportDao quarterlyAttendanceReportDao;
	
	@Autowired
	private FieldValidation fieldValidation;


	@Override
	public Object saveStudentMarks(List<StudentMarksDto> studentMarksDto) {

	
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

			studentMark = studentMarksDao.getStudentModelandquarterAndYear(mark.getStudentId(),
					mark.getQuarterAndYear());
			if (studentMark == null) {
				studentMark = new MarkModel();
				StudentModel student = studentRequestDao.getStudentModel(mark.getStudentId());
				studentMark.setStudentModel(student);
				studentMark.setQuarterAndYear(mark.getQuarterAndYear());
//				studentMark.setCreateTeacher(mark.getTecaherId());
				studentMark.setCreateDate(today);
				studentMark.setCreateTeacher(mark.getTeacherId());
			} else {
				studentMark.setUpdateTeacher(mark.getTeacherId());
				studentMark.setUpdateTime(today);
			}

			Integer tamil = studentMark.setTamil(mark.getTamil());
			Integer english = studentMark.setEnglish(mark.getEnglish());
			Integer maths = studentMark.setMaths(mark.getMaths());
			Integer science = studentMark.setScience(mark.getScience());
			Integer socialscience = studentMark.setSocialScience(mark.getSocialScience());

			Integer totalMark = tamil + english + maths + science + socialscience;
			studentMark.setTotalMarks(totalMark);


			String compliance = quarterlyAttendanceReportDao.getComplianceStatus(mark.getStudentId(),
					mark.getQuarterAndYear());

			boolean allSubjectsPassed = tamil >= 35 && english >= 35 && maths >= 35 && science >= 35
					&& socialscience >= 35;

			if (allSubjectsPassed && WebServiceUtil.COMPLIANCE.equals(compliance)) {
				studentMark.setResult(WebServiceUtil.PASS);
			} else {
				if (allSubjectsPassed != true) {
					studentMark.setFailedForMark(true);
				}

				studentMark.setResult(WebServiceUtil.FAIL);
			}

			studentMarksDao.saveStudentMarks(studentMark);

//			emailSentService.sendQuarterlyResultReportEmail(List<StudentMarksDto> studentMarksDto);

		}

		return "Successfully saved";
	}

	@Override
	public List<ComplianceStudentWithPassOrFail> getAllComplianceStudentPassOrFail(String quarterAndYear) {

		return studentMarksDao.getAllComplianceStudentPassOrFail(quarterAndYear);
	}

	@Override
	public List<StudentMarksDto> getAllStudentMarks(String quarterAndYear) {

		return studentMarksDao.getAllStudentMarks(quarterAndYear);
	}

	@Override
	public List<TotalResultCountdto> getToatalResultCount(String quarterAndYear) {

		return studentMarksDao.getToatalResultCount(quarterAndYear);
	}

	@Override
	public ClassTopperDto getClassTopper(String quarterAndYear) {

		return studentMarksDao.getClassTopper(quarterAndYear);
	}

}
