package com.studentmanagementsystem.api.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentmanagementsystem.api.dao.QuarterlyAttendanceReportDao;
import com.studentmanagementsystem.api.dao.StudentMarksDao;
import com.studentmanagementsystem.api.dao.StudentModelDao;
import com.studentmanagementsystem.api.model.custom.student.StudentListRequestDto;
import com.studentmanagementsystem.api.model.custom.studentmarks.ComplianceStudentWithPassOrFail;
import com.studentmanagementsystem.api.model.custom.studentmarks.StudentMarksDto;
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

	@Override
	public Object saveStudentMarks(List<StudentMarksDto> studentMarksDto) {

		LocalDateTime today = LocalDateTime.now();
		StudentMarks studentMark;
		for (StudentMarksDto mark : studentMarksDto) {

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

			System.out.println("tamil" + tamil);

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

			System.out.println(compliance);
			boolean allSubjectsPassed = tamil >= 35 && english >= 35 && maths >= 35 && science >= 35
					&& socialscience >= 35;

			System.out.println(allSubjectsPassed);

			if (allSubjectsPassed && WebServiceUtil.COMPLIANCE.equals(compliance)) {
				studentMark.setResult(WebServiceUtil.PASS);
			} else {
				System.out.println("else class");
				studentMark.setResult(WebServiceUtil.FAIL);
			}

			studentMarksDao.saveStudentMarks(studentMark);

		}

		return "Successfully saved";
	}

	@Override
	public List<ComplianceStudentWithPassOrFail> getAllComplianceStudentPassOrFail(String quarterAndYear) {

		return studentMarksDao.getAllComplianceStudentPassOrFail(quarterAndYear);
	}

}
