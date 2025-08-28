package com.studentmanagementsystem.api.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentmanagementsystem.api.dao.QuarterlyAttendanceReportDao;
import com.studentmanagementsystem.api.dao.StudentMarksDao;
import com.studentmanagementsystem.api.dao.StudentRequestDao;
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
	private StudentRequestDao studentRequestDao;
	
	@Autowired
	private QuarterlyAttendanceReportDao quarterlyAttendanceReportDao;

	@Override
	public Object saveStudentMarks(List<StudentMarksDto> studentMarksDto) {
		
		
		StudentMarks studentMark;
		for(StudentMarksDto mark :studentMarksDto ) {
			
		
		
			studentMark = studentMarksDao.getStudentModelandquarterAndYear(mark.getStudentId(),mark.getQuarterAndYear());		
			if(studentMark == null) {
				studentMark = new StudentMarks();
				StudentModel student = studentRequestDao.getStudentModel(mark.getStudentId());
				studentMark.setStudentModel(student);
				studentMark.setQuarterAndYear(mark.getQuarterAndYear());
			}
			 
			int tamil= studentMark.setTamil(mark.getTamil());
			int english= studentMark.setEnglish(mark.getEnglish());
			int maths= studentMark.setMaths(mark.getMaths());
			 int science=studentMark.setScience(mark.getScience());
			int socialscience= studentMark.setSocialScience(mark.getSocialScience());
			 
			int totalMark =tamil+english+maths+science+socialscience;			 
			studentMark.setTotalMarks(totalMark);
			
			int tamilPercentage = tamil/100 * 100;
			int englishPercentage = english/100 *100;
			int mathsPercentage = maths/100 *100;
			int sciencePercentage = science/100 *100;
			int socialsciencePercentage = socialscience/100 *100;
			
			   String compliance = quarterlyAttendanceReportDao.getComplianceStatus(mark.getStudentId(),mark.getQuarterAndYear());
			
			if(tamilPercentage > 35 && englishPercentage >= 35 && mathsPercentage >= 35 && sciencePercentage >= 35 && socialsciencePercentage >= 35 && compliance == WebServiceUtil.COMPLIANCE ) {
				
				studentMark.setResult(WebServiceUtil.PASS);
				
			}
			else {
				studentMark.setResult(WebServiceUtil.FAIL);
			}
			
			studentMarksDao.saveStudentMarks(studentMark);
			
		}
	
		return "Successfully saved" ;
	}

	@Override
	public List<ComplianceStudentWithPassOrFail> getAllComplianceStudentPassOrFail(String quarterAndYear) {
		
		return studentMarksDao.getAllComplianceStudentPassOrFail(quarterAndYear);
	}

	

}
