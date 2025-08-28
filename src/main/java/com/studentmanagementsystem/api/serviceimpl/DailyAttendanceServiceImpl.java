package com.studentmanagementsystem.api.serviceimpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.studentmanagementsystem.api.dao.DailyAttendanceDao;
import com.studentmanagementsystem.api.model.custom.dailyattendance.DailyAttendanceDto;
import com.studentmanagementsystem.api.model.custom.dailyattendance.ExceedingDaysLeaveDto;
import com.studentmanagementsystem.api.model.custom.dailyattendance.MonthlyAbsenceDto;
import com.studentmanagementsystem.api.model.entity.DailyAttendanceModel;
import com.studentmanagementsystem.api.service.DailyAttendanceService;
import com.studentmanagementsystem.api.service.QuarterlyAttendanceReportService;

import com.studentmanagementsystem.api.util.WebServiceUtil;

@Service
public class DailyAttendanceServiceImpl implements DailyAttendanceService {

	@Autowired
	private DailyAttendanceDao dailyAttendanceDao;
	
	@Autowired
	private QuarterlyAttendanceReportService quartlyAttendanceReportService;

	@Override
	public Object setAttendanceToSingleStudent(DailyAttendanceDto dailyAttendanceDto) {
		//validation
		//Attendance
		//fieldName,msg 
		
	//	unique check
		// StudentFName,StudentMName,StudentLName,DOB,
		
		DailyAttendanceModel dailyAttendance;
		if (dailyAttendanceDto.getAttendanceId() == null) {
			dailyAttendance = dailyAttendanceDao.createNewAttendance(dailyAttendanceDto.getStudentId());

		} else {
			dailyAttendance = dailyAttendanceDao.findAttendanceById(dailyAttendanceDto.getAttendanceId());
		}
		
		
		dailyAttendance.setAttendanceDate(dailyAttendanceDto.getAttendanceDate());
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
		

		return "Attendance Marked";
	}

//	@Override
//	public Object setAttandanceMultiSameDate(List<DailyAttendanceDto> dailyAttendanceDto, LocalDate attendanceDate) {
//		
//		return dailyAttendanceDao.setAttandanceMultiSameDate(dailyAttendanceDto,attendanceDate);
//	}

	@Override
	public Object setAttandanceMultiStudents(List<DailyAttendanceDto> dailyAttendanceDto, LocalDate attendanceDate) {

		List<DailyAttendanceModel> attendance = new ArrayList<>();
		
		for (DailyAttendanceDto attend : dailyAttendanceDto) {
			DailyAttendanceModel dailyAttendance;

			if (attend.getAttendanceId() == null) {
				dailyAttendance = dailyAttendanceDao.createNewAttendance(attend.getStudentId());
			} else {
				dailyAttendance = dailyAttendanceDao.findAttendanceById(attend.getAttendanceId());
			}

			if (attendanceDate != null) {
				dailyAttendance.setAttendanceDate(attendanceDate);
			} else {
				dailyAttendance.setAttendanceDate(attend.getAttendanceDate());
			}

			dailyAttendance.setAttendanceStatus(attend.getAttendanceStatus());

			if (attend.getAttendanceStatus() == WebServiceUtil.ABSENT) {
				dailyAttendance.setLongApprovedSickLeaveFlag(attend.getLongApprovedSickLeaveFlag());
				dailyAttendance
						.setApprovedExtraCurricularActivitiesFlag(attend.getApprovedExtraCurricularActivitiesFlag());
			} else {
				dailyAttendance.setLongApprovedSickLeaveFlag(WebServiceUtil.NO);
				dailyAttendance.setApprovedExtraCurricularActivitiesFlag(WebServiceUtil.NO);
			}

			attendance.add(dailyAttendance);
			
		}
			
		dailyAttendanceDao.setAttandanceMultiStudents(attendance);
//		   dailyAttendanceRepository.save(dailyAttendance);
		
//		if(attendanceDate == null) {
//	    quartlyAttendanceReportService.monthEndCheck(dailyAttendance.getAttendanceDate());
//		}
//		else {
//			 quartlyAttendanceReportService.monthEndCheck(attendanceDate);
//		}

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
