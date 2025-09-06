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
import com.studentmanagementsystem.api.model.custom.dailyattendance.response.DailyAttendanceListResponse;
import com.studentmanagementsystem.api.model.custom.dailyattendance.response.ExceedingDaysLeaveListResponse;
import com.studentmanagementsystem.api.model.custom.dailyattendance.response.MonthlyAbsenceListResponse;
import com.studentmanagementsystem.api.model.entity.DailyAttendanceModel;
import com.studentmanagementsystem.api.model.entity.SchoolHolidaysModel;
import com.studentmanagementsystem.api.model.entity.StudentCodeModel;
import com.studentmanagementsystem.api.model.entity.StudentModel;
import com.studentmanagementsystem.api.model.entity.TeacherModel;
import com.studentmanagementsystem.api.repository.DailyAttendanceRepository;
import com.studentmanagementsystem.api.repository.StudentCodeRespository;
import com.studentmanagementsystem.api.repository.StudentModelRepository;
import com.studentmanagementsystem.api.repository.TeacherRepository;
import com.studentmanagementsystem.api.service.DailyAttendanceService;


import com.studentmanagementsystem.api.util.WebServiceUtil;
import com.studentmanagementsystem.api.validation.FieldValidation;

import jakarta.mail.MessagingException;

@Service
public class DailyAttendanceServiceImpl implements DailyAttendanceService {

	@Autowired
	private DailyAttendanceDao dailyAttendanceDao;
	

	
	@Autowired
	private EmailSentService emailSentService;
	
	@Autowired
	private SchoolHolidaysDao schoolHolidaysDao; 
	
	@Autowired
	private FieldValidation fieldValidation;
	
	@Autowired
	private DailyAttendanceRepository dailyAttendanceRepository;
	
	@Autowired
	private TeacherRepository teacherRepository;
	
	@Autowired
	private StudentModelRepository studentModelRepository;
	
	@Autowired
	private StudentCodeRespository studentCodeRespository;

	/**
	 * Mark attendance for a single student.
	 */
	@Override
	public Response setAttendanceToSingleStudent(DailyAttendanceDto dailyAttendanceDto) {
		
		Response response = new Response();
		List<String> requestMissedFieldList = fieldValidation.checkValidationSetAttendanceToSingleStudent(dailyAttendanceDto);

		if (!requestMissedFieldList.isEmpty()) {
			response.setStatus(WebServiceUtil.WARNING);	
			response.setData(requestMissedFieldList);		
			return response;
		}
		
	
		LocalDateTime today = LocalDateTime.now();

		SchoolHolidaysModel schoolHolidayModel = schoolHolidaysDao.getHolidayByHolidayDate(dailyAttendanceDto.getAttendanceDate());
	
		if(schoolHolidayModel != null && Boolean.FALSE.equals(schoolHolidayModel.getIsHolidayCancelled())) {
			
			 response.setStatus(WebServiceUtil.WARNING);	
			 response.setData(dailyAttendanceDto.getAttendanceDate() + WebServiceUtil.DO_NOT_MARK_ATTENDANCE);
			
		}
		
		DailyAttendanceModel dailyAttendanceModel;
//    	dailyAttendance = dailyAttendanceDao.findAttendanceById(dailyAttendanceDto.getAttendanceId());
		dailyAttendanceModel = dailyAttendanceRepository.findStudentIdAndAttendanceDate(dailyAttendanceDto.getStudentId(),dailyAttendanceDto.getAttendanceDate());
		if(dailyAttendanceModel == null) {
		//	dailyAttendance = dailyAttendanceDao.createNewAttendance(dailyAttendanceDto.getStudentId());
          dailyAttendanceModel = new DailyAttendanceModel();
          StudentModel student = studentModelRepository.findStudentByStudentId(dailyAttendanceDto.getStudentId());
          dailyAttendanceModel.setStudentModel(student);
			TeacherModel teacher = teacherRepository.findTeacherIdByTeacherId(dailyAttendanceDto.getTeacherId());
			if(teacher==null) {
				response.setStatus(WebServiceUtil.WARNING);	
				response.setData(WebServiceUtil.TEACHER_ID_ERROR);
				return response;
			}

			dailyAttendanceModel.setCreateTeacher(teacher.getTeacherId());
			dailyAttendanceModel.setCreateDate(today);
			dailyAttendanceModel.setAttendanceDate(dailyAttendanceDto.getAttendanceDate());
		}
		else {
			TeacherModel teacher = teacherRepository.findTeacherIdByTeacherId(dailyAttendanceDto.getTeacherId());
			if(teacher==null) {
				response.setStatus(WebServiceUtil.WARNING);	
				response.setData(WebServiceUtil.TEACHER_ID_ERROR);
				return response;
			}
			dailyAttendanceModel.setUpdateTeacher(teacher.getTeacherId());
			dailyAttendanceModel.setUpdateTime(today);
		}
		StudentCodeModel attendanceStatus = studentCodeRespository.findStudentCodeByCode(dailyAttendanceDto.getAttendanceStatus());
		if(attendanceStatus == null) {
			response.setStatus(WebServiceUtil.WARNING);	
			response.setData(String.format(WebServiceUtil.INVAILD, "attendanceStatus"));
		}
		dailyAttendanceModel.setAttendanceStatus(attendanceStatus);
		
		if (WebServiceUtil.ABSENT.equals(dailyAttendanceDto.getAttendanceStatus())) {
			dailyAttendanceModel.setLongApprovedSickLeaveFlag(dailyAttendanceDto.getLongApprovedSickLeaveFlag());
			dailyAttendanceModel.setApprovedExtraCurricularActivitiesFlag(
					dailyAttendanceDto.getApprovedExtraCurricularActivitiesFlag());
		} else {
			dailyAttendanceModel.setLongApprovedSickLeaveFlag(WebServiceUtil.NO);
			dailyAttendanceModel.setApprovedExtraCurricularActivitiesFlag(WebServiceUtil.NO);
		}
		    dailyAttendanceRepository.save(dailyAttendanceModel);
		    
		    
//		    quartlyAttendanceReportService.monthEndCheck(dailyAttendanceDto.getAttendanceDate());
		    
//		    if(dailyAttendanceDto.getAttendanceStatus() == WebServiceUtil.ABSENT && dailyAttendanceDto.getLongApprovedSickLeaveFlag() == WebServiceUtil.NO && dailyAttendanceDto.getApprovedExtraCurricularActivitiesFlag() == WebServiceUtil.NO) {
//		    	try {
//					emailSentService.absentAlertMessage(dailyAttendanceDto.getStudentId(),dailyAttendanceDto.getAttendanceDate());
//				} catch (MessagingException e) {					
//					e.printStackTrace();
//				}
//		    }
//		    else if(dailyAttendanceDto.getAttendanceStatus() == WebServiceUtil.ABSENT && dailyAttendanceDto.getLongApprovedSickLeaveFlag() == WebServiceUtil.YES) {
//		    	emailSentService.sendEmailAlert(dailyAttendanceDto.getStudentId(),dailyAttendanceDto.getAttendanceDate(),WebServiceUtil.SICK_LEVAE_SUBJECT,WebServiceUtil.SICK_LEVAE_MESSAGE);
//		    }
//		    else if (dailyAttendanceDto.getAttendanceStatus() == WebServiceUtil.ABSENT && dailyAttendanceDto.getApprovedExtraCurricularActivitiesFlag() == WebServiceUtil.YES) {
//		    	emailSentService.sendEmailAlert(dailyAttendanceDto.getStudentId(),dailyAttendanceDto.getAttendanceDate(),WebServiceUtil.EXTRA_CUR_ACTIVITIES_LEAVE_SUBJECT,WebServiceUtil.EXTRA_CUR_ACTIVITIES_LEAVE_MESSAGE);
//		    }
		    response.setStatus(WebServiceUtil.SUCCESS);	
			response.setData(WebServiceUtil.MARK_ATTENDANCE);

		return response;
	}

	/**
	 * Mark attendance for multliple students.
	 */

	@Override
	public Response setAttandanceMultiStudents(List<DailyAttendanceDto> dailyAttendanceDto, LocalDate attendanceDate) {
		
		Response response = new Response();
		
		List<DailyAttendanceModel> dailyAttendanceModelList = new ArrayList<>();
		LocalDateTime today = LocalDateTime.now();
		for (DailyAttendanceDto attendance : dailyAttendanceDto) {
			
			List<String> requestMissedFieldList = fieldValidation.checkValidationSetAttendanceToSingleStudent(attendance);

			if (!requestMissedFieldList.isEmpty()) {
				response.setStatus(WebServiceUtil.WARNING);	
				response.setData(requestMissedFieldList);		
				return response;
			}
			
			SchoolHolidaysModel schoolHolidayModel = schoolHolidaysDao.getHolidayByHolidayDate(attendance.getAttendanceDate());
			
			if(schoolHolidayModel != null && Boolean.FALSE.equals(schoolHolidayModel.getIsHolidayCancelled())) {
				 response.setStatus(WebServiceUtil.WARNING);	
				 response.setData(attendance.getAttendanceDate() + WebServiceUtil.DO_NOT_MARK_ATTENDANCE);
			}
			
			DailyAttendanceModel dailyAttendanceModel;
			dailyAttendanceModel = dailyAttendanceRepository.findStudentIdAndAttendanceDate(attendance.getStudentId(),attendance.getAttendanceDate());
			if(dailyAttendanceModel == null) {
				dailyAttendanceModel = new DailyAttendanceModel();
		          StudentModel student = studentModelRepository.findStudentByStudentId(attendance.getStudentId());
		          dailyAttendanceModel.setStudentModel(student);
				TeacherModel teacher = teacherRepository.findTeacherIdByTeacherId(attendance.getTeacherId());
				if(teacher==null) {
					response.setStatus(WebServiceUtil.WARNING);	
					response.setData(WebServiceUtil.TEACHER_ID_ERROR);
					return response;
				}
				
				dailyAttendanceModel.setCreateTeacher(teacher.getTeacherId());
				dailyAttendanceModel.setCreateDate(today);
				if (attendanceDate != null) {
					dailyAttendanceModel.setAttendanceDate(attendanceDate);
				} else {
					dailyAttendanceModel.setAttendanceDate(attendance.getAttendanceDate());
				}
			}
			else {
				
				TeacherModel teacher = teacherRepository.findTeacherIdByTeacherId(attendance.getTeacherId());
				if(teacher==null) {
					response.setStatus(WebServiceUtil.WARNING);	
					response.setData(WebServiceUtil.TEACHER_ID_ERROR);
					return response;
				}
				dailyAttendanceModel.setUpdateTeacher(teacher.getTeacherId());
				dailyAttendanceModel.setUpdateTime(today);
			}
			StudentCodeModel attendanceStatus = studentCodeRespository.findStudentCodeByCode(attendance.getAttendanceStatus());

			if(attendanceStatus == null) {
				response.setStatus(WebServiceUtil.WARNING);	
				response.setData(String.format(WebServiceUtil.INVAILD, "attendanceStatus"));
			}
			dailyAttendanceModel.setAttendanceStatus(attendanceStatus);

			if (WebServiceUtil.ABSENT.equals(attendance.getAttendanceStatus())) {
				dailyAttendanceModel.setLongApprovedSickLeaveFlag(attendance.getLongApprovedSickLeaveFlag());
				dailyAttendanceModel
						.setApprovedExtraCurricularActivitiesFlag(attendance.getApprovedExtraCurricularActivitiesFlag());
			} else {
				dailyAttendanceModel.setLongApprovedSickLeaveFlag(WebServiceUtil.NO);
				dailyAttendanceModel.setApprovedExtraCurricularActivitiesFlag(WebServiceUtil.NO);
			}

			dailyAttendanceModelList.add(dailyAttendanceModel);
			
		}
			

		dailyAttendanceRepository.saveAll(dailyAttendanceModelList);

		
//		if(attendanceDate == null) {
//	    quartlyAttendanceReportService.monthEndCheck(dailyAttendanceDto.get(0).getAttendanceDate());
//		}
//		else {
//			 quartlyAttendanceReportService.monthEndCheck(attendanceDate);
//		}
		
		 response.setStatus(WebServiceUtil.SUCCESS);	
		 response.setData(WebServiceUtil.MARK_ATTENDANCE);
		

		return response ;
	}
	
	/**
	 * Retrieve student attendance.
	 * By default, retrieves today's attendance. If a date is provided, retrieves attendance for that particular date.
	 */

	@Override
	public DailyAttendanceListResponse getStudentAttendanceByToday(LocalDate attendanceDate) {
		
		DailyAttendanceListResponse response = new DailyAttendanceListResponse();
		
		if (attendanceDate == null) {
			LocalDate today = LocalDate.now();
			response.setStatus(WebServiceUtil.SUCCESS);	
			response.setData(dailyAttendanceDao.getStudentAttendance(today));
			return response;
		} else {
			response.setStatus(WebServiceUtil.SUCCESS);	
			response.setData(dailyAttendanceDao.getStudentAttendance(attendanceDate));
			
			return response ;
		}
	}
	
	/**
	 * Retrieve students with no attendance marked.
	 * By default, retrieves students without attendance for today. 
	 * If a date is provided, retrieves students without attendance for that particular date.
	 */
	@Override
	public DailyAttendanceListResponse getStudentAttendanceNotTakeByToday(LocalDate attendanceDate) {
		DailyAttendanceListResponse response = new DailyAttendanceListResponse();
		if (attendanceDate == null) {
			LocalDate today = LocalDate.now();
			response.setStatus(WebServiceUtil.SUCCESS);	
			response.setData(dailyAttendanceDao.getStudentAttendanceNotTakeByToday(today));
			
			return response;
		} else {
			response.setStatus(WebServiceUtil.SUCCESS);	
			response.setData(dailyAttendanceDao.getStudentAttendanceNotTakeByToday(attendanceDate));
			return response;

		}
	}

	/**
	 * Retrieve students who have taken extra-curricular activity leave 
	 * for more than 3 days in a given month.
	 */	
	@Override
	public  ExceedingDaysLeaveListResponse getStudentleaveForExtraActivities(int month, int year) {
//		List<ExceedingDaysLeaveDto> exceedingDaysLeaveDto = dailyAttendanceDao.getStudentleaveForExtraActivitiesd(month,year,WebServiceUtil.EXTRA_CURRICULAR_ACTIVITIES,3);
		ExceedingDaysLeaveListResponse response = new ExceedingDaysLeaveListResponse();
		response.setStatus(WebServiceUtil.SUCCESS);	
		response.setData( dailyAttendanceDao.getStudentleaveForExtraActivitiesd(month,year,WebServiceUtil.EXTRA_CURRICULAR_ACTIVITIES,3));
		
		return response;
	}

	/**
	 * Retrieve students who have taken sick leave 
	 * for more than 6 days in a given month.
	 */	
	@Override
	public ExceedingDaysLeaveListResponse getStudentleaveForSickLeave(int month, int year) {
		
		
		ExceedingDaysLeaveListResponse response = new ExceedingDaysLeaveListResponse();
		response.setStatus(WebServiceUtil.SUCCESS);	
		response.setData( dailyAttendanceDao.getStudentleaveForExtraActivitiesd(month,year,WebServiceUtil.SICK_LEAVE,6));
		
		return response;
	}
	
	/**
	 * Retrieve students who were absent in a given month.
	 */
	@Override
	public MonthlyAbsenceListResponse getMonthlyAbsenceStudents(int month, int year) {
		MonthlyAbsenceListResponse response = new MonthlyAbsenceListResponse();
		response.setStatus(WebServiceUtil.SUCCESS);	
		response.setData( dailyAttendanceDao.getMonthlyAbsenceStudents(month,year));
		return response;
	}


}
