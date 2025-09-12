package com.studentmanagementsystem.api.serviceimpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.studentmanagementsystem.api.dao.DailyAttendanceDao;
import com.studentmanagementsystem.api.model.custom.Response;
import com.studentmanagementsystem.api.model.custom.dailyattendance.DailyAttendanceDto;
import com.studentmanagementsystem.api.model.custom.dailyattendance.DailyAttendanceFilterDto;
import com.studentmanagementsystem.api.model.custom.dailyattendance.response.DailyAttendanceListResponse;
import com.studentmanagementsystem.api.model.custom.dailyattendance.response.MonthlyAbsenceListResponse;
import com.studentmanagementsystem.api.model.entity.DailyAttendanceModel;
import com.studentmanagementsystem.api.model.entity.SchoolHolidaysModel;
import com.studentmanagementsystem.api.model.entity.StudentCodeModel;
import com.studentmanagementsystem.api.model.entity.StudentModel;
import com.studentmanagementsystem.api.model.entity.TeacherModel;
import com.studentmanagementsystem.api.repository.DailyAttendanceRepository;
import com.studentmanagementsystem.api.repository.SchoolHolidaysRepository;
import com.studentmanagementsystem.api.repository.StudentCodeRespository;
import com.studentmanagementsystem.api.repository.StudentModelRepository;
import com.studentmanagementsystem.api.repository.TeacherRepository;
import com.studentmanagementsystem.api.service.DailyAttendanceService;
import com.studentmanagementsystem.api.util.WebServiceUtil;
import com.studentmanagementsystem.api.validation.FieldValidation;

@Service
public class DailyAttendanceServiceImpl implements DailyAttendanceService {
	
	private static final Logger logger = LoggerFactory.getLogger(DailyAttendanceServiceImpl.class);


	@Autowired
	private DailyAttendanceDao dailyAttendanceDao;
	 
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
	
	@Autowired
	private SchoolHolidaysRepository schoolHolidaysRepository;


	/**
	 * Mark attendance for students.
	 */

	@Override
	public Response setAttandanceMultiStudents(List<DailyAttendanceDto> dailyAttendanceDto, LocalDate attendanceDate) {

		Response response = new Response();
		
		List<DailyAttendanceModel> dailyAttendanceModelList = new ArrayList<>();
		LocalDateTime today = LocalDateTime.now();
		for (DailyAttendanceDto attendance : dailyAttendanceDto) {
			logger.info("Before setAttandanceMultiStudents - Attempting to mark attendance StudentId : {}  for TeacherId: {}",attendance.getStudentId(), attendance.getTeacherId());
			List<String> requestMissedFieldList = fieldValidation.checkValidationSetAttendanceToSingleStudent(attendance);

			if (!requestMissedFieldList.isEmpty()) {
				response.setStatus(WebServiceUtil.WARNING);	
				response.setData(requestMissedFieldList);		
				return response;
			}
			
			SchoolHolidaysModel schoolHolidayModel = schoolHolidaysRepository.getHolidayByHolidayDate(attendance.getAttendanceDate());
			
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
				TeacherModel teacher = teacherRepository.findTeacherByTeacherId(attendance.getTeacherId());
				if(teacher==null) {
					response.setStatus(WebServiceUtil.WARNING);	
					response.setData(WebServiceUtil.TEACHER_ID_ERROR);
					return response;
				}
				
//				dailyAttendanceModel.setCreateTeacher(teacher.getTeacherId());
				dailyAttendanceModel.setTeacherModel(teacher);
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
		
		 response.setStatus(WebServiceUtil.SUCCESS);	
		 response.setData(WebServiceUtil.MARK_ATTENDANCE);
		 
			logger.info("After setAttandanceMultiStudents - Attendace Maeked successfully");

		return response ;
	}

	/**
	 * Retrieve student attendance for a particular date to check whether given filter.
	 *
	 */
	@Override
	public DailyAttendanceListResponse getStudentAttendanceByDate(DailyAttendanceFilterDto dailyAttendanceFilterDto) {
		logger.info("Before getStudentAttendanceByDate - Attempting to retrive the student attendance");

	    DailyAttendanceListResponse response = new DailyAttendanceListResponse();
	    response.setStatus(WebServiceUtil.SUCCESS);

	    if (Boolean.TRUE.equals(dailyAttendanceFilterDto.getAttendanceMark())) {
	        response.setData(dailyAttendanceDao.getStudentAttendanceTaken(dailyAttendanceFilterDto));
	    } else {
	        response.setData(dailyAttendanceDao.getStudentAttendanceNotTaken(dailyAttendanceFilterDto));
	    }
		logger.info("After getStudentAttendanceByDate - Successfully retrived student attendance");
	    return response;
	}
 
	/**
	 * Retrieve students' attendance records for a given month and year, based on the type of absence:
	 * 
	 *   Extra-curricular activity leave → more than 3 days in the given month</li>
	 *   Sick leave → more than 6 days in the given month</li>
	 *   Monthly absence → students absent during the given month</li>
	 */
	@Override
	public MonthlyAbsenceListResponse getMonthlyAbsenceReport(DailyAttendanceFilterDto dailyAttendanceFilterDto) {
		logger.info("Before getMonthlyAbsenceReport - Attempting to retrive the monthly absence studenet list");
		MonthlyAbsenceListResponse response =  new MonthlyAbsenceListResponse();
		response.setStatus(WebServiceUtil.SUCCESS);
		response.setData( dailyAttendanceDao.getMonthlyAbsenceStudents(dailyAttendanceFilterDto));
		logger.info("After getMonthlyAbsenceReport - Successfully retrived monthly absence list");

		return response;
	}
	
	
	
}
