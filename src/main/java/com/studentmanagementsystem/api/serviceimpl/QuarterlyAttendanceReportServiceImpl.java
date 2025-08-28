package com.studentmanagementsystem.api.serviceimpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentmanagementsystem.api.dao.QuarterlyAttendanceReportDao;
import com.studentmanagementsystem.api.model.custom.quarterlyreport.ComplianceAndNonComplianceReportDto;
import com.studentmanagementsystem.api.model.custom.quarterlyreport.QuarterlyAttendanceReportDto;
import com.studentmanagementsystem.api.model.entity.QuarterlyAttendanceReportModel;

import com.studentmanagementsystem.api.service.QuarterlyAttendanceReportService;
import com.studentmanagementsystem.api.util.WebServiceUtil;

@Service
public class QuarterlyAttendanceReportServiceImpl implements QuarterlyAttendanceReportService {

	@Autowired
	private QuarterlyAttendanceReportDao quartlyAttendanceReportDao;

	@Override
	public void monthEndCheck(LocalDate attendanceDate) {
		int monthNumber = attendanceDate.getMonthValue();

		if (monthNumber == 1 || monthNumber == 2 || monthNumber == 3) {
			updateQuarterlyAttendanceReport(WebServiceUtil.QUART_MARCH_AND_YEAR, WebServiceUtil.MARCH_END);
		} else if (monthNumber == 4 || monthNumber == 5 || monthNumber == 6) {
			updateQuarterlyAttendanceReport(WebServiceUtil.QUART_JUNE_AND_YEAR, WebServiceUtil.JUNE_END);
		} else if (monthNumber == 7 || monthNumber == 8 || monthNumber == 9) {
			updateQuarterlyAttendanceReport(WebServiceUtil.QUART_SEP_AND_YEAR, WebServiceUtil.SEP_END);
		} else if (monthNumber == 10 || monthNumber == 11 || monthNumber == 12) {
			updateQuarterlyAttendanceReport(WebServiceUtil.QUART_DEC_AND_YEAR, WebServiceUtil.DEC_END);
		}
	}

	@Override
	public void updateQuarterlyAttendanceReport(String quarterAndYear, List<Integer> quarterMonthEnd) {

		List<QuarterlyAttendanceReportDto> quarterlyAttendanceList = quartlyAttendanceReportDao
				.getAttendanceSummary(quarterMonthEnd);
//		QuarterlyAttendanceReportModel quarterlyAttendanceReportModel;
		for (QuarterlyAttendanceReportDto singleQuarterlyAttendance : quarterlyAttendanceList) {
			
		
			QuarterlyAttendanceReportModel	 quarterlyAttendanceReportModel = quartlyAttendanceReportDao.getStudentIdUpdateReport(singleQuarterlyAttendance.getStudentId(),quarterAndYear);
			
			quarterlyAttendanceReportModel.setStudentModel(
					quartlyAttendanceReportDao.getStudentModelByStuId(singleQuarterlyAttendance.getStudentId()));

			quarterlyAttendanceReportModel.setQuarterAndYear(quarterAndYear);
			quarterlyAttendanceReportModel
					.setTotalSchoolWorkingDays(singleQuarterlyAttendance.getTotalSchoolWorkingDays());
			quarterlyAttendanceReportModel.setTotalDaysOfPresent(singleQuarterlyAttendance.getTotalDaysOfPresent());
			quarterlyAttendanceReportModel.setTotalDaysOfAbsents(singleQuarterlyAttendance.getTotalDaysOfAbsents());
			quarterlyAttendanceReportModel.setTotalApprovedActivitiesPermissionDays(
					singleQuarterlyAttendance.getTotalApprovedActivitiesPermissionDays());
			quarterlyAttendanceReportModel
					.setTotalApprovedSickdays(singleQuarterlyAttendance.getTotalApprovedSickdays());
			Long present = singleQuarterlyAttendance.getTotalDaysOfPresent();
			Long activities = singleQuarterlyAttendance.getTotalApprovedActivitiesPermissionDays();
			Long sick = singleQuarterlyAttendance.getTotalApprovedSickdays();
			Long totalWorking = singleQuarterlyAttendance.getTotalSchoolWorkingDays();

			Long percentageOfPresent = (long) Math.ceil((((present + activities + sick) / totalWorking) * 100));
			if (percentageOfPresent < 75) {

				quarterlyAttendanceReportModel.setAttendanceComplianceStatus(WebServiceUtil.NON_COMPLIANCE);
				quarterlyAttendanceReportModel.setComments(WebServiceUtil.NON_COMPLIANCE_COMMENT);
			} else {
				quarterlyAttendanceReportModel.setAttendanceComplianceStatus(WebServiceUtil.COMPLIANCE);
				quarterlyAttendanceReportModel.setComments(WebServiceUtil.COMPLIANCE_COMMENT);
			}
			quartlyAttendanceReportDao.saveQuarterlyAttendanceReport(quarterlyAttendanceReportModel);
		}

	}

	@Override
	public List<ComplianceAndNonComplianceReportDto> getNonComplianceStudents(String quarterAndYear) {
		
		return quartlyAttendanceReportDao.getNonComplianceStudents(quarterAndYear,WebServiceUtil.NON_COMPLIANCE_COMMENT) ;
	}

	@Override
	public List<ComplianceAndNonComplianceReportDto> getComplianceStudents(String quarterAndYear) {
		
		return quartlyAttendanceReportDao.getNonComplianceStudents(quarterAndYear,WebServiceUtil.COMPLIANCE) ;
	}

}
