package com.studentmanagementsystem.api.serviceimpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.studentmanagementsystem.api.dao.QuarterlyAttendanceReportDao;
import com.studentmanagementsystem.api.model.custom.quarterlyreport.ComplianceAndNonComplianceReportDto;
import com.studentmanagementsystem.api.model.custom.quarterlyreport.QuarterlyAttendanceReportDto;
import com.studentmanagementsystem.api.model.custom.quarterlyreport.response.ComplianceAndNonComplianceListResponse;
import com.studentmanagementsystem.api.model.entity.QuarterlyAttendanceReportModel;
import com.studentmanagementsystem.api.repository.QuarterlyAttendanceModelRepository;
import com.studentmanagementsystem.api.repository.StudentModelRepository;
import com.studentmanagementsystem.api.service.QuarterlyAttendanceReportService;
import com.studentmanagementsystem.api.util.WebServiceUtil;

@Service
public class QuarterlyAttendanceReportServiceImpl implements QuarterlyAttendanceReportService {

	@Autowired
	private QuarterlyAttendanceReportDao quartlyAttendanceReportDao;
	
	@Autowired
	private QuarterlyAttendanceModelRepository quarterlyAttendanceModelRepository;
	
	@Autowired
	private StudentModelRepository studentModelRepository;
	
	/**
	 * Find the quarter and update the quarterly attendance report.
	 *
	 * Author: Vijayakumar
	 * @param quarter The quarter to be updated (e.g., Q1, Q2, Q3, Q4)
	 * @param year The year for which the report is to be updated
	 * @return 
	 */


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
	
	/**
	 * Scheduled task that updates the quarterly attendance report.
	 * This method is executed automatically by a Cron job 
	 * every day at 5:00 PM.
	 *
	 * Author: Vijayakumar
	 */

	  @Scheduled(cron = "0 0 17 * * ?") // Every day at 5 PM
	    public void runQuarterlyAttendanceUpdate() {
	        
	        List<String> quarterAndYear = new ArrayList<>(
	                Arrays.asList(WebServiceUtil.QUART_MARCH_AND_YEAR,
	                		WebServiceUtil.QUART_JUNE_AND_YEAR,
	                		WebServiceUtil.QUART_SEP_AND_YEAR,
	                		WebServiceUtil.QUART_DEC_AND_YEAR));

	        for (String quarter : quarterAndYear) {

	    		if (quarter == WebServiceUtil.QUART_MARCH_AND_YEAR ) {
	    			updateQuarterlyAttendanceReport(WebServiceUtil.QUART_MARCH_AND_YEAR, WebServiceUtil.MARCH_END);
	    		} else if (quarter == WebServiceUtil.QUART_JUNE_AND_YEAR) {
	    			updateQuarterlyAttendanceReport(WebServiceUtil.QUART_JUNE_AND_YEAR, WebServiceUtil.JUNE_END);
	    		} else if (quarter == WebServiceUtil.QUART_SEP_AND_YEAR ) {
	    			updateQuarterlyAttendanceReport(WebServiceUtil.QUART_SEP_AND_YEAR, WebServiceUtil.SEP_END);
	    		} else if (quarter == WebServiceUtil.QUART_DEC_AND_YEAR) {
	    			updateQuarterlyAttendanceReport(WebServiceUtil.QUART_DEC_AND_YEAR, WebServiceUtil.DEC_END);
	    		}
	        }
	       
	  }
	  
	  /**
	   * Updates the quarterly attendance report for all students.
	   * <p>
	   * This method aggregates daily attendance data (present, absent, sick leave,
	   * extra-curricular leave) for the given quarter and persists the results 
	   * into the quarterly report table.
	   */

	@Override
	public void updateQuarterlyAttendanceReport(String quarterAndYear, List<Integer> quarterMonthEnd) {

		List<QuarterlyAttendanceReportDto> quarterlyAttendanceList = quartlyAttendanceReportDao
				.getAttendanceSummary(quarterMonthEnd);

		for (QuarterlyAttendanceReportDto singleQuarterlyAttendance : quarterlyAttendanceList) {
			
		
		//QuarterlyAttendanceReportModel	 quarterlyAttendanceReportModel = quartlyAttendanceReportDao.getStudentIdUpdateReport(singleQuarterlyAttendance.getStudentId(),quarterAndYear);
     	QuarterlyAttendanceReportModel	 quarterlyAttendanceReportModel = quarterlyAttendanceModelRepository.findByStudentAndQuarterAndYear(singleQuarterlyAttendance.getStudentId(),quarterAndYear);
			
			if(quarterlyAttendanceReportModel == null) {
					 quarterlyAttendanceReportModel = new QuarterlyAttendanceReportModel();
			}
			
			quarterlyAttendanceReportModel.setStudentModel(
					studentModelRepository.getStudentByStudentId(singleQuarterlyAttendance.getStudentId()));

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

	       int percentageOfPresent =  (int) Math.ceil((((present + activities + sick) / totalWorking) * 100));
	

			if (percentageOfPresent < 75) {

				quarterlyAttendanceReportModel.setAttendanceComplianceStatus(WebServiceUtil.NON_COMPLIANCE);
				quarterlyAttendanceReportModel.setComments(WebServiceUtil.NON_COMPLIANCE_COMMENT);
			} else {
				quarterlyAttendanceReportModel.setAttendanceComplianceStatus(WebServiceUtil.COMPLIANCE);
				quarterlyAttendanceReportModel.setComments(WebServiceUtil.COMPLIANCE_COMMENT);
			}
			quarterlyAttendanceModelRepository.save(quarterlyAttendanceReportModel);
			
		}
	}
	/**
	 * Retrieve the list of non-compliance students for a given quarter and year.
	 */

	@Override
	public ComplianceAndNonComplianceListResponse getNonComplianceStudents(String quarterAndYear) {
		
		ComplianceAndNonComplianceListResponse response = new ComplianceAndNonComplianceListResponse();
		response.setStatus(WebServiceUtil.SUCCESS);
		response.setData(quartlyAttendanceReportDao.getNonComplianceStudents(quarterAndYear,WebServiceUtil.NON_COMPLIANCE_COMMENT));	
		return response;
	}
	
	/**
	 * Retrieve the list of compliance students for a given quarter and year.
	 */

	@Override
	public ComplianceAndNonComplianceListResponse getComplianceStudents(String quarterAndYear) {
		ComplianceAndNonComplianceListResponse response = new ComplianceAndNonComplianceListResponse();
		response.setStatus(WebServiceUtil.SUCCESS);
		response.setData( quartlyAttendanceReportDao.getNonComplianceStudents(quarterAndYear,WebServiceUtil.COMPLIANCE) );	
		return response;
	}

}
