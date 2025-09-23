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
import com.studentmanagementsystem.api.model.custom.dailyattendance.MonthlyAbsenceDto;
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
import com.studentmanagementsystem.api.service.EmailSentService;
import com.studentmanagementsystem.api.service.QuarterlyAttendanceReportService;
import com.studentmanagementsystem.api.util.WebServiceUtil;
import com.studentmanagementsystem.api.validation.FieldValidation;

import jakarta.mail.MessagingException;

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
	
	@Autowired
	private EmailSentService emailSentService;

	@Autowired
	private  QuarterlyAttendanceReportService quarterlyAttendanceService;

	/**
	 * Mark attendance for students.
	 */
	
	@Override
	public Response markStudentAttendance(List<DailyAttendanceDto> dailyAttendanceDto,Integer classOfStudy) {
		return saveAttendance(dailyAttendanceDto, classOfStudy);
	}

	/**
	 * Mark attendance for students.
	 */

	@Override
	public Response saveAttendance(List<DailyAttendanceDto> dailyAttendanceDto,Integer classOfStudy) {

		Response response = new Response();
		LocalDateTime today = LocalDateTime.now();
		
		LocalDate date = LocalDate.now();
		
		//List to save attendance
		List<DailyAttendanceModel> dailyAttendanceModelList = new ArrayList<>();
		
		//List to collect absent student IDs
		List<Long> absentIdList = new ArrayList<>();
		
		List<Long> studentIdList = new ArrayList<>();
		
		//List to collect month to update the quarterly attendance
		List<Integer> quarterMonthList = new ArrayList<>();
		Long teacherId = null;
		Integer updateFlag =0;
		LocalDate attendanceDate=null;
		DailyAttendanceModel dailyAttendanceModel;
		
		for (DailyAttendanceDto attendance : dailyAttendanceDto) {
			
			logger.info("Before markStudentAttendance - Attempting to mark attendance StudentId : {}  for TeacherId: {}",attendance.getStudentId(), attendance.getTeacherId());
			
			//Field Validation
			List<String> requestFieldList = fieldValidation.attendanceFieldValidation(attendance);
			if (!requestFieldList.isEmpty()) {
				response.setStatus(WebServiceUtil.WARNING);	
				response.setData(requestFieldList);		
				return response;
			}
			 // Verify if the teacher ID exists
			TeacherModel teacher = teacherRepository.findTeacherByTeacherId(attendance.getTeacherId());
			if(teacher==null) {
				response.setStatus(WebServiceUtil.WARNING);	
				response.setData(WebServiceUtil.TEACHER_ID_ERROR);
				return response;
			}
			
			// Verify if studentId and attendanceDate already exist
			
			dailyAttendanceModel = dailyAttendanceRepository.findStudentIdAndAttendanceDate(attendance.getStudentId(),attendance.getAttendanceDate());
			
			//save the daily attendance
			if(dailyAttendanceModel == null) {
				
				//Verify if attendanceDate is holiday
				SchoolHolidaysModel schoolHolidayModel = schoolHolidaysRepository.getHolidayByHolidayDate(attendance.getAttendanceDate());
				if(schoolHolidayModel != null && Boolean.FALSE.equals(schoolHolidayModel.getIsHolidayCancelled())) {
					 response.setStatus(WebServiceUtil.WARNING);	
					 response.setData(attendance.getAttendanceDate() + WebServiceUtil.DO_NOT_MARK_ATTENDANCE);
				}
		
				dailyAttendanceModel = new DailyAttendanceModel();
				
		        StudentModel student = studentModelRepository.findStudentByStudentId(attendance.getStudentId());
		        dailyAttendanceModel.setStudentModel(student);
				dailyAttendanceModel.setCreateTeacher(teacher);
				dailyAttendanceModel.setCreateDate(today);
				dailyAttendanceModel.setAttendanceDate(attendance.getAttendanceDate());
				
				studentIdList.add(attendance.getStudentId());
				attendanceDate = attendance.getAttendanceDate();
				
			}
			else {
				
				dailyAttendanceModel.setUpdateTeacher(teacher);
				dailyAttendanceModel.setUpdateTime(today);
				updateFlag =1;
			}
			
			StudentCodeModel attendanceStatus = studentCodeRespository.findStudentCodeByCode(attendance.getAttendanceStatus());
			dailyAttendanceModel.setAttendanceStatus(attendanceStatus);
			
	
			
			if (WebServiceUtil.PRESENT.equals(attendance.getAttendanceStatus())) {
				dailyAttendanceModel
				.setApprovedExtraCurricularActivitiesFlag(attendance.getApprovedExtraCurricularActivitiesFlag());	
			}
			else {
				dailyAttendanceModel.setApprovedExtraCurricularActivitiesFlag(WebServiceUtil.NO);
			}
			
			if (WebServiceUtil.ABSENT.equals(attendance.getAttendanceStatus())) {
				dailyAttendanceModel.setLongApprovedSickLeaveFlag(attendance.getLongApprovedSickLeaveFlag());

			} else {
				dailyAttendanceModel.setLongApprovedSickLeaveFlag(WebServiceUtil.NO);

			}
			
			if(attendance.getAttendanceStatus().equals(WebServiceUtil.ABSENT) || dailyAttendanceModel.getApprovedExtraCurricularActivitiesFlag().equals(WebServiceUtil.YES) && date.equals(attendance.getAttendanceDate())) {
				absentIdList.add(attendance.getStudentId());
			}
			 
		     teacherId = attendance.getTeacherId();
		     int monthValue = attendance.getAttendanceDate().getMonthValue();
		     if(!quarterMonthList.contains(monthValue)) {
		    	 quarterMonthList.add(monthValue);
		     }
			dailyAttendanceModelList.add(dailyAttendanceModel);
		}
		
		
		if(!studentIdList.isEmpty()) {
		  List<Long> existsStudentId = studentModelRepository.findStudentIdByClassOfStudy(classOfStudy);
		  List<Long> notTakenList = new ArrayList<>(existsStudentId);
		  notTakenList.removeAll(studentIdList);
		  
		  if(!notTakenList.isEmpty()) {
			  for(Long studentId : notTakenList) {
				  dailyAttendanceModel = new DailyAttendanceModel();
				  StudentModel student = studentModelRepository.findStudentByStudentId(studentId);
				  
				    dailyAttendanceModel.setStudentModel(student);
					TeacherModel teacher = teacherRepository.findTeacherByTeacherId(teacherId);
					dailyAttendanceModel.setCreateTeacher(teacher);
					dailyAttendanceModel.setCreateDate(today);
					dailyAttendanceModel.setAttendanceDate(attendanceDate);
					
					StudentCodeModel attendanceStatus = studentCodeRespository.findStudentCodeByCode(WebServiceUtil.ABSENT);
					dailyAttendanceModel.setAttendanceStatus(attendanceStatus);
					dailyAttendanceModel.setLongApprovedSickLeaveFlag(WebServiceUtil.NO);
					dailyAttendanceModel.setApprovedExtraCurricularActivitiesFlag(WebServiceUtil.NO);
					dailyAttendanceModelList.add(dailyAttendanceModel);
				  
			  }
		  }
		}
		
		// save the student attendance	
		dailyAttendanceRepository.saveAll(dailyAttendanceModelList);
		dailyAttendanceRepository.flush();
		
		// update the quarterly attendance report
		quarterlyAttendanceService.findQuarterToUpadte(quarterMonthList);
		
		 //send mail to absent students 
		 if(!absentIdList.isEmpty()) {
		 try {
			emailSentService.absentAlertEmail(absentIdList,teacherId);
		} catch (MessagingException e) {
			
			e.printStackTrace();
		}}
		 
		 logger.info("After markStudentAttendance - Attendace Marked successfully");
		 
		 response.setStatus(WebServiceUtil.SUCCESS);
		 if(updateFlag==0)
			 	response.setData(WebServiceUtil.MARK_ATTENDANCE);
		 else
			response.setData(WebServiceUtil.UPDATE_ATTENDANCE);
		return response ;
	}

	/**
	 * Retrieve student attendance for a particular date to check whether given filter.
	 *
	 */
	@Override
	public Response listStudentAttendance(DailyAttendanceFilterDto dailyAttendanceFilterDto) {
		return attendanceList(dailyAttendanceFilterDto);
	}

	/**
	 * Retrieve student attendance for a particular date to check whether given filter.
	 *
	 */
	@Override
	public Response attendanceList(DailyAttendanceFilterDto dailyAttendanceFilterDto) {
		logger.info("Before getStudentAttendanceByDate - Attempting to retrive the student attendance");

		List<DailyAttendanceDto> attendanceList;
		
	   
	 //   attendanceList = dailyAttendanceDao.listAttendanceNotTaken(dailyAttendanceFilterDto);
	   
	 	attendanceList= dailyAttendanceDao.attendanceList(dailyAttendanceFilterDto);
	 
	    
	    Integer totalCount = dailyAttendanceRepository.findTotalCount(dailyAttendanceFilterDto.getClassOfStudy());
	    
	    int sno = 1;
        for (DailyAttendanceDto attendance : attendanceList) {
        	attendance.setSno(sno++);
        }
	    Response response = new Response();
	    response.setStatus(WebServiceUtil.SUCCESS);
	    response.setTotalCount(totalCount);
	    response.setFilterCount(sno-1);
	    response.setData(attendanceList);
	    
		logger.info("After getStudentAttendanceByDate - Successfully retrived student attendance");
		
	    return response;
	}
 
	/**
	 * Retrieve students'monthly attendance list for a given filter
	 *
	 */
	@Override
	public Response getMonthlyAbsenceReport(DailyAttendanceFilterDto dailyAttendanceFilterDto) {
		return monthliAttendanceList(dailyAttendanceFilterDto);
	}

	/**
	 * Retrieve students'monthly attendance list for a given filter
	 *
	 */
	@Override
	public Response monthliAttendanceList(DailyAttendanceFilterDto dailyAttendanceFilterDto) {
		logger.info("Before getMonthlyAbsenceReport - Attempting to retrive the monthly absence studenet list");
		
		List<MonthlyAbsenceDto> absentList= dailyAttendanceDao.monthlyAttendanceList(dailyAttendanceFilterDto);
		Integer totalCount = dailyAttendanceRepository.findTotalCount(dailyAttendanceFilterDto.getClassOfStudy());
		 
		    int sno = 1;
	        for (MonthlyAbsenceDto absent : absentList) {
	        	absent.setSno(sno++);
	        }
	        
		Response response = new Response();
		response.setStatus(WebServiceUtil.SUCCESS);
		response.setTotalCount(totalCount);
		response.setFilterCount(sno-1);
		response.setData( absentList);
		logger.info("After getMonthlyAbsenceReport - Successfully retrived monthly absence list");

		return response;
	}
	
	
	
}
