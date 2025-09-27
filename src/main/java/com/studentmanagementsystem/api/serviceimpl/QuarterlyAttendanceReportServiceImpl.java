package com.studentmanagementsystem.api.serviceimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.studentmanagementsystem.api.dao.QuarterlyAttendanceReportDao;
import com.studentmanagementsystem.api.model.custom.CommonFilterDto;
import com.studentmanagementsystem.api.model.custom.Response;
import com.studentmanagementsystem.api.model.custom.quarterlyreport.QuarterlyAttendanceReportDto;
import com.studentmanagementsystem.api.model.entity.QuarterlyAttendanceReportModel;
import com.studentmanagementsystem.api.model.entity.StudentCodeModel;
import com.studentmanagementsystem.api.repository.QuarterlyAttendanceModelRepository;
import com.studentmanagementsystem.api.repository.StudentCodeRespository;
import com.studentmanagementsystem.api.repository.StudentRepository;
import com.studentmanagementsystem.api.service.QuarterlyAttendanceReportService;
import com.studentmanagementsystem.api.util.WebServiceUtil;

@Service
public class QuarterlyAttendanceReportServiceImpl implements QuarterlyAttendanceReportService {
	
	private static final Logger logger = LoggerFactory.getLogger(QuarterlyAttendanceReportServiceImpl.class);


	@Autowired
	private QuarterlyAttendanceReportDao quartlyAttendanceReportDao;
	
	@Autowired
	private QuarterlyAttendanceModelRepository quarterlyAttendanceModelRepository;
	
	@Autowired
	private StudentRepository studentModelRepository;
	
	@Autowired
	private StudentCodeRespository studentCodeRespository;

	/**
	 *Updates the quarterly attendance report.
	 * This method is executed automatically by a Cron job 
	 * every day at 5:00 PM.
	 *
	 * Author: Vijayakumar
	 */
	@Override
	public void findQuarterToUpadte(List<Integer> quarterMonth) {
		int march =0,june=0,sep=0,dec=0;
		for(Integer monthNumber : quarterMonth)
		{	
		if (monthNumber == 1 || monthNumber == 2 || monthNumber == 3  && march==0) {
			updateQuarterlyAttendanceReport(WebServiceUtil.QUART_MARCH_AND_YEAR, WebServiceUtil.MARCH_END);
			march =1;
		} else if (monthNumber == 4 || monthNumber == 5 || monthNumber == 6 && june == 0) {
			updateQuarterlyAttendanceReport(WebServiceUtil.QUART_JUNE_AND_YEAR, WebServiceUtil.JUNE_END);		
			june =1;
		} else if (monthNumber == 7 || monthNumber == 8 || monthNumber == 9 && sep == 0) {
			updateQuarterlyAttendanceReport(WebServiceUtil.QUART_SEP_AND_YEAR, WebServiceUtil.SEP_END);
			sep=1;
		} else if (monthNumber == 10 || monthNumber == 11 || monthNumber == 12 && dec == 0) {
			updateQuarterlyAttendanceReport(WebServiceUtil.QUART_DEC_AND_YEAR, WebServiceUtil.DEC_END);
			dec=1;
		}
		}
	}


	/**
	 * Scheduled task that updates the quarterly attendance report.
	 * This method is executed automatically by a Cron job 
	 * every day at 5:00 PM.
	 *
	 * Author: Vijayakumar
	 */
	 // @Scheduled(cron = "0 0 17 * * ?") // Every day at 5 PM
	    public void runQuarterlyAttendanceUpdate() {
	    	
			logger.info("Before runQuarterlyAttendanceUpdate - Update the quarterly Attendance ");

	        
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
			logger.info("After runQuarterlyAttendanceUpdate -Successfully updated the quarterly Attendance ");

	       
	  }
	  
	  /**
	   * Updates the quarterly attendance report for all students.
	   * <p>
	   * This method aggregates daily attendance data (present, absent, sick leave,
	   * extra-curricular leave) for the given quarter and persists the results 
	   * into the quarterly report table.
	   */
//	@Override
	public void updateQuarterlyAttendanceReport(String quarterAndYear, List<Integer> quarterMonthEnd) {
		
		logger.info("Before updateQuarterlyAttendanceReport - Update the quarterly Attendance , quarter : {} ",quarterAndYear);

		List<QuarterlyAttendanceReportDto> quarterlyAttendanceList = quartlyAttendanceReportDao
				.getAttendanceSummary(quarterMonthEnd);
		
		Long totalWorkingDays = quartlyAttendanceReportDao.getTotalWorkingDays( quarterMonthEnd);

		for (QuarterlyAttendanceReportDto quarterlyAttendance : quarterlyAttendanceList) {
			
     	QuarterlyAttendanceReportModel	 quarterlyAttendanceReportModel = quarterlyAttendanceModelRepository.findByStudentAndQuarterAndYear(quarterlyAttendance.getStudentId(),quarterAndYear);
			
			if(quarterlyAttendanceReportModel == null) {
					 quarterlyAttendanceReportModel = new QuarterlyAttendanceReportModel();
			}
			
			quarterlyAttendanceReportModel.setStudentModel(
					studentModelRepository.findStudentByStudentId(quarterlyAttendance.getStudentId()));

			quarterlyAttendanceReportModel.setQuarterAndYear(quarterAndYear);
			quarterlyAttendanceReportModel
					.setTotalSchoolWorkingDays(totalWorkingDays);
			quarterlyAttendanceReportModel.setTotalDaysOfPresent(quarterlyAttendance.getTotalDaysOfPresent());
			quarterlyAttendanceReportModel.setTotalDaysOfAbsents(quarterlyAttendance.getTotalDaysOfAbsents());
			quarterlyAttendanceReportModel.setTotalApprovedActivitiesPermissionDays(
					quarterlyAttendance.getTotalApprovedActivitiesPermissionDays());
			quarterlyAttendanceReportModel
					.setTotalApprovedSickdays(quarterlyAttendance.getTotalApprovedSickdays());
			Long present = quarterlyAttendance.getTotalDaysOfPresent();
			Long activities = quarterlyAttendance.getTotalApprovedActivitiesPermissionDays();
			Long sick = quarterlyAttendance.getTotalApprovedSickdays();			

	       int percentageOfPresent =  (int) Math.ceil((((present + activities + sick) / totalWorkingDays) * 100));
	       		
	       quarterlyAttendanceReportModel.setAttendancePercentage(percentageOfPresent);

			if (percentageOfPresent < 75) {
				StudentCodeModel nonCompliance = studentCodeRespository.findStudentCodeByCode(WebServiceUtil.NON_COMPLIANCE);

				quarterlyAttendanceReportModel.setAttendanceComplianceStatus(nonCompliance);
				quarterlyAttendanceReportModel.setComments(WebServiceUtil.NON_COMPLIANCE_COMMENT);
			} else {
				StudentCodeModel compliance = studentCodeRespository.findStudentCodeByCode(WebServiceUtil.COMPLIANCE);

				quarterlyAttendanceReportModel.setAttendanceComplianceStatus(compliance);
				quarterlyAttendanceReportModel.setComments(WebServiceUtil.COMPLIANCE_COMMENT);
			}
			quarterlyAttendanceModelRepository.save(quarterlyAttendanceReportModel);
			logger.info("After updateQuarterlyAttendanceReport - SuccessFully updated");
		}
	}
	
	/**
	 * Retrieve the list of compliance or non-compliance students for a given quarter and year.
	 */
	@Override
	public Response quarterlyAttendanceList(CommonFilterDto filterDto) {
		logger.info("Before listQuarterlyAttendance - Retrive the quarterly attendance report ");
		List<QuarterlyAttendanceReportDto> quarterlyAttendanceList = quartlyAttendanceReportDao.quarterlyAttendanceList(filterDto);
		Long totalCount = quarterlyAttendanceModelRepository.findTotalCount(filterDto.getClassOfStudy(),filterDto.getQuarterAndYear());
		int sno=1;
		for(QuarterlyAttendanceReportDto quaterlyAttendance : quarterlyAttendanceList) {
			quaterlyAttendance.setSno(sno++);
		}
		
		Response response = new Response();
		response.setStatus(WebServiceUtil.SUCCESS);
		response.setDraw(filterDto.getDraw());
		response.setTotalCount(totalCount);
		response.setFilterCount((long) (sno-1));
		response.setData(quarterlyAttendanceList);
		logger.info("After listQuarterlyAttendance - Successfully retrive the quarterly attendance report ");
		return response;
	}

}
