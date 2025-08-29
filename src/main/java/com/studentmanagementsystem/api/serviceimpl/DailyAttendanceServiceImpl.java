package com.studentmanagementsystem.api.serviceimpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
		LocalDateTime today = LocalDateTime.now();
		
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
		LocalDateTime today = LocalDateTime.now();
		for (DailyAttendanceDto attend : dailyAttendanceDto) {
			DailyAttendanceModel dailyAttendance;
			dailyAttendance = dailyAttendanceDao.getStudentIdAndAttendanceDate(attend.getStudentId(),attend.getAttendanceDate());
			if(dailyAttendance == null) {
				dailyAttendance = dailyAttendanceDao.createNewAttendance(attend.getStudentId());
				dailyAttendance.setCreateTeacher(attend.getTeacherId());
				dailyAttendance.setCreateDate(today);
				if (attendanceDate != null) {
					dailyAttendance.setAttendanceDate(attendanceDate);
				} else {
					dailyAttendance.setAttendanceDate(attend.getAttendanceDate());
				}
			}
			else {
				dailyAttendance.setUpdateTeacher(attend.getTeacherId());
				dailyAttendance.setUpdateTime(today);
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
