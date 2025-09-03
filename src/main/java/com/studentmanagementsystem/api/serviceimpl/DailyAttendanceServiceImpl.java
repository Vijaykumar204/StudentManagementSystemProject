package com.studentmanagementsystem.api.serviceimpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.studentmanagementsystem.api.dao.DailyAttendanceDao;
import com.studentmanagementsystem.api.dao.SchoolHolidaysDao;
import com.studentmanagementsystem.api.model.custom.Response;
import com.studentmanagementsystem.api.model.custom.dailyattendance.DailyAttendanceDto;
import com.studentmanagementsystem.api.model.custom.dailyattendance.ExceedingDaysLeaveDto;
import com.studentmanagementsystem.api.model.custom.dailyattendance.MonthlyAbsenceDto;
import com.studentmanagementsystem.api.model.entity.DailyAttendanceModel;
import com.studentmanagementsystem.api.model.entity.SchoolHolidaysModel;
import com.studentmanagementsystem.api.service.DailyAttendanceService;
import com.studentmanagementsystem.api.service.QuarterlyAttendanceReportService;

import com.studentmanagementsystem.api.util.WebServiceUtil;
import com.studentmanagementsystem.api.validation.FieldValidation;

@Service
public class DailyAttendanceServiceImpl implements DailyAttendanceService {

	@Autowired
	private DailyAttendanceDao dailyAttendanceDao;
	
	@Autowired
	private QuarterlyAttendanceReportService quartlyAttendanceReportService;
	
	@Autowired
	private EmailSentService emailSentService;
	
	@Autowired
	private SchoolHolidaysDao schoolHolidaysDao; 
	
	@Autowired
	private FieldValidation fieldValidation;

	@Override
	public Object setAttendanceToSingleStudent(DailyAttendanceDto dailyAttendanceDto) {
		
		Response response = new Response();
		List<String> requestMissedFieldList = fieldValidation.checkValidationSetAttendanceToSingleStudent(dailyAttendanceDto);

		if (!requestMissedFieldList.isEmpty()) {
			response.setStatus(WebServiceUtil.WARNING);	
			response.setData(requestMissedFieldList);		
			return response;
		}
		
	
		LocalDateTime today = LocalDateTime.now();

		SchoolHolidaysModel schoolHoliday = schoolHolidaysDao.getHolidayByHolidayDate(dailyAttendanceDto.getAttendanceDate());
	
		if(schoolHoliday != null && Boolean.FALSE.equals(schoolHoliday.getIsHolidayCancelled())) {
			return dailyAttendanceDto.getAttendanceDate() + WebServiceUtil.DO_NOT_MARK_ATTENDANCE;
		}
		
		DailyAttendanceModel dailyAttendance;
//    	dailyAttendance = dailyAttendanceDao.findAttendanceById(dailyAttendanceDto.getAttendanceId());
		dailyAttendance = dailyAttendanceDao.getStudentIdAndAttendanceDate(dailyAttendanceDto.getStudentId(),dailyAttendanceDto.getAttendanceDate());
		if(dailyAttendance == null) {
			dailyAttendance = dailyAttendanceDao.createNewAttendance(dailyAttendanceDto.getStudentId());
			dailyAttendance.setCreateTeacher(dailyAttendanceDto.getTeacherId());
			dailyAttendance.setCreateDate(today);
			dailyAttendance.setAttendanceDate(dailyAttendanceDto.getAttendanceDate());
		}
		else {
			dailyAttendance.setUpdateTeacher(dailyAttendanceDto.getTeacherId());
			dailyAttendance.setUpdateTime(today);
		}
		dailyAttendance.setAttendanceStatus(dailyAttendanceDto.getAttendanceStatus());
		if (dailyAttendanceDto.getAttendanceStatus() == WebServiceUtil.ABSENT) {
			dailyAttendance.setLongApprovedSickLeaveFlag(dailyAttendanceDto.getLongApprovedSickLeaveFlag());
			dailyAttendance.setApprovedExtraCurricularActivitiesFlag(
					dailyAttendanceDto.getApprovedExtraCurricularActivitiesFlag());
		} else {
			dailyAttendance.setLongApprovedSickLeaveFlag(WebServiceUtil.NO);
			dailyAttendance.setApprovedExtraCurricularActivitiesFlag(WebServiceUtil.NO);
		}
		    dailyAttendanceDao.setAttendanceToSingleStudent(dailyAttendance);
		    quartlyAttendanceReportService.monthEndCheck(dailyAttendanceDto.getAttendanceDate());
		    
		    if(dailyAttendanceDto.getAttendanceStatus() == WebServiceUtil.ABSENT && dailyAttendanceDto.getLongApprovedSickLeaveFlag() == WebServiceUtil.NO && dailyAttendanceDto.getApprovedExtraCurricularActivitiesFlag() == WebServiceUtil.NO) {
		    	emailSentService.sendEmailAlert(dailyAttendanceDto.getStudentId(),dailyAttendanceDto.getAttendanceDate(),WebServiceUtil.ABSENT_ALERT_SUBJECT,WebServiceUtil.ABSENT_ALERT_MESSAGE);
		    }
		    else if(dailyAttendanceDto.getAttendanceStatus() == WebServiceUtil.ABSENT && dailyAttendanceDto.getLongApprovedSickLeaveFlag() == WebServiceUtil.YES) {
		    	emailSentService.sendEmailAlert(dailyAttendanceDto.getStudentId(),dailyAttendanceDto.getAttendanceDate(),WebServiceUtil.SICK_LEVAE_SUBJECT,WebServiceUtil.SICK_LEVAE_MESSAGE);
		    }
		    else if (dailyAttendanceDto.getAttendanceStatus() == WebServiceUtil.ABSENT && dailyAttendanceDto.getApprovedExtraCurricularActivitiesFlag() == WebServiceUtil.YES) {
		    	emailSentService.sendEmailAlert(dailyAttendanceDto.getStudentId(),dailyAttendanceDto.getAttendanceDate(),WebServiceUtil.EXTRA_CUR_ACTIVITIES_LEAVE_SUBJECT,WebServiceUtil.EXTRA_CUR_ACTIVITIES_LEAVE_MESSAGE);
		    }
		

		return "Attendance Marked";
	}

    

	@Override
	public Object setAttandanceMultiStudents(List<DailyAttendanceDto> dailyAttendanceDto, LocalDate attendanceDate) {
		
		Response response = new Response();
		
		List<DailyAttendanceModel> attendanceList = new ArrayList<>();
		LocalDateTime today = LocalDateTime.now();
		for (DailyAttendanceDto attendance : dailyAttendanceDto) {
			
			List<String> requestMissedFieldList = fieldValidation.checkValidationSetAttendanceToSingleStudent(attendance);

			if (!requestMissedFieldList.isEmpty()) {
				response.setStatus(WebServiceUtil.WARNING);	
				response.setData(requestMissedFieldList);		
				return response;
			}
			
			SchoolHolidaysModel schoolHoliday = schoolHolidaysDao.getHolidayByHolidayDate(attendance.getAttendanceDate());
			
			if(schoolHoliday != null && Boolean.FALSE.equals(schoolHoliday.getIsHolidayCancelled())) {
				return attendance.getAttendanceDate() + WebServiceUtil.DO_NOT_MARK_ATTENDANCE;
			}
			
			DailyAttendanceModel dailyAttendance;
			dailyAttendance = dailyAttendanceDao.getStudentIdAndAttendanceDate(attendance.getStudentId(),attendance.getAttendanceDate());
			if(dailyAttendance == null) {
				dailyAttendance = dailyAttendanceDao.createNewAttendance(attendance.getStudentId());
				dailyAttendance.setCreateTeacher(attendance.getTeacherId());
				dailyAttendance.setCreateDate(today);
				if (attendanceDate != null) {
					dailyAttendance.setAttendanceDate(attendanceDate);
				} else {
					dailyAttendance.setAttendanceDate(attendance.getAttendanceDate());
				}
			}
			else {
				dailyAttendance.setUpdateTeacher(attendance.getTeacherId());
				dailyAttendance.setUpdateTime(today);
			}
			
			dailyAttendance.setAttendanceStatus(attendance.getAttendanceStatus());

			if (attendance.getAttendanceStatus() == WebServiceUtil.ABSENT) {
				dailyAttendance.setLongApprovedSickLeaveFlag(attendance.getLongApprovedSickLeaveFlag());
				dailyAttendance
						.setApprovedExtraCurricularActivitiesFlag(attendance.getApprovedExtraCurricularActivitiesFlag());
			} else {
				dailyAttendance.setLongApprovedSickLeaveFlag(WebServiceUtil.NO);
				dailyAttendance.setApprovedExtraCurricularActivitiesFlag(WebServiceUtil.NO);
			}

			attendanceList.add(dailyAttendance);
			
		}
			
		dailyAttendanceDao.setAttandanceMultiStudents(attendanceList);

		
		if(attendanceDate == null) {
	    quartlyAttendanceReportService.monthEndCheck(dailyAttendanceDto.get(0).getAttendanceDate());
		}
		else {
			 quartlyAttendanceReportService.monthEndCheck(attendanceDate);
		}

		return "Attendance Marked" ;
	}

	@Override
	public List<DailyAttendanceDto> getStudentAttendanceByToday(LocalDate attendanceDate) {
		if (attendanceDate == null) {
			LocalDate today = LocalDate.now();
			return dailyAttendanceDao.getStudentAttendance(today);
		} else {
			return dailyAttendanceDao.getStudentAttendance(attendanceDate);
		}
	}

	@Override
	public List<DailyAttendanceDto> getStudentAttendanceNotTakeByToday(LocalDate attendanceDate) {
		
		if (attendanceDate == null) {
			LocalDate today = LocalDate.now();
			return dailyAttendanceDao.getStudentAttendanceNotTakeByToday(today);
		} else {
			return dailyAttendanceDao.getStudentAttendanceNotTakeByToday(attendanceDate);

		}
	}

	@Override
	public List<ExceedingDaysLeaveDto> getStudentleaveForExtraActivities(int month, int year) {
		
		
		
	
		return dailyAttendanceDao.getStudentleaveForExtraActivitiesd(month,year,WebServiceUtil.EXTRA_CURRICULAR_ACTIVITIES,3);
	}

	@Override
	public List<ExceedingDaysLeaveDto> getStudentleaveForSickLeave(int month, int year) {
		
		return dailyAttendanceDao.getStudentleaveForExtraActivitiesd(month,year,WebServiceUtil.SICK_LEAVE,6);
	}

	@Override
	public List<MonthlyAbsenceDto> getMonthlyAbsenceStudents(int month, int year) {

		return dailyAttendanceDao.getMonthlyAbsenceStudents(month,year);
	}


}
