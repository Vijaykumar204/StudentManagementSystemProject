package com.studentmanagementsystem.api.serviceimpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentmanagementsystem.api.dao.QuarterlyAttendanceReportDao;
import com.studentmanagementsystem.api.dao.StudentMarksDao;
import com.studentmanagementsystem.api.dao.StudentModelDao;

import com.studentmanagementsystem.api.model.custom.studentmarks.ClassTopperDto;
import com.studentmanagementsystem.api.model.custom.studentmarks.ComplianceStudentWithPassOrFail;
import com.studentmanagementsystem.api.model.custom.studentmarks.StudentMarksDto;
import com.studentmanagementsystem.api.model.custom.studentmarks.TotalResultCountdto;
import com.studentmanagementsystem.api.model.entity.StudentMarks;
import com.studentmanagementsystem.api.model.entity.StudentModel;
import com.studentmanagementsystem.api.service.StudentMarksService;
import com.studentmanagementsystem.api.util.WebServiceUtil;

@Service
public class StudentMarksServiceImpl implements StudentMarksService {
	@Autowired
	private StudentMarksDao studentMarksDao;

	@Autowired
	private StudentModelDao studentRequestDao;

	@Autowired
	private QuarterlyAttendanceReportDao quarterlyAttendanceReportDao;

	@Autowired
	private EmailSentService emailSentService;

	@Override
	public Object saveStudentMarks(List<StudentMarksDto> studentMarksDto) {

		List<String> requestMissedField = new ArrayList<>();

		LocalDateTime today = LocalDateTime.now();
		StudentMarks studentMark;
		for (StudentMarksDto mark : studentMarksDto) {

			if (mark.getStudentId() == null) {
				requestMissedField.add(WebServiceUtil.STUDENT_ID_ERROR);
			}
			if (mark.getTeacherId() == null) {
				requestMissedField.add(WebServiceUtil.TEACHER_ID_ERROR);
			}
			if (mark.getQuarterAndYear() == null) {
				requestMissedField.add(WebServiceUtil.QUARTER_AND_YEAR_ERROR);
			}
			if (!requestMissedField.isEmpty()) {
				return requestMissedField;
			}

			studentMark = studentMarksDao.getStudentModelandquarterAndYear(mark.getStudentId(),
					mark.getQuarterAndYear());
			if (studentMark == null) {
				studentMark = new StudentMarks();
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

			int tamil = studentMark.setTamil(mark.getTamil());
			int english = studentMark.setEnglish(mark.getEnglish());
			int maths = studentMark.setMaths(mark.getMaths());
			int science = studentMark.setScience(mark.getScience());
			int socialscience = studentMark.setSocialScience(mark.getSocialScience());

			int totalMark = tamil + english + maths + science + socialscience;
			studentMark.setTotalMarks(totalMark);

//			
//			int tamilPercentage = tamil/100 * 100;
//			
//			
//			int englishPercentage = english/100 *100;
//			int mathsPercentage = maths/100 *100;
//			int sciencePercentage = science/100 *100;
//			int socialsciencePercentage = socialscience/100 *100;

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
