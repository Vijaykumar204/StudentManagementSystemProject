package com.studentmanagementsystem.api.serviceimpl;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.studentmanagementsystem.api.dao.DailyAttendanceDao;
import com.studentmanagementsystem.api.model.custom.CommonFilterDto;
import com.studentmanagementsystem.api.model.custom.MessageResponse;
import com.studentmanagementsystem.api.model.custom.Response;
import com.studentmanagementsystem.api.model.custom.dailyattendance.DailyAttendanceDto;
import com.studentmanagementsystem.api.model.custom.dailyattendance.MonthlyAbsenceDto;
import com.studentmanagementsystem.api.model.entity.DailyAttendanceModel;
import com.studentmanagementsystem.api.model.entity.HolidayModel;
import com.studentmanagementsystem.api.model.entity.StudentCodeModel;
import com.studentmanagementsystem.api.model.entity.StudentModel;
import com.studentmanagementsystem.api.model.entity.TeacherModel;
import com.studentmanagementsystem.api.repository.DailyAttendanceRepository;
import com.studentmanagementsystem.api.repository.HolidayRepository;
import com.studentmanagementsystem.api.repository.StudentCodeRespository;
import com.studentmanagementsystem.api.repository.StudentRepository;
import com.studentmanagementsystem.api.repository.TeacherRepository;
import com.studentmanagementsystem.api.service.DailyAttendanceService;
import com.studentmanagementsystem.api.service.EmailSentService;
import com.studentmanagementsystem.api.service.QuarterlyAttendanceService;
import com.studentmanagementsystem.api.util.WebServiceUtil;
import com.studentmanagementsystem.api.validation.FieldValidation;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;

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
	private StudentRepository studentModelRepository;
	
	@Autowired
	private StudentCodeRespository studentCodeRespository;
	
	@Autowired
	private HolidayRepository schoolHolidaysRepository;
	
	@Autowired
	private EmailSentService emailSentService;

	@Autowired
	private  QuarterlyAttendanceService quarterlyAttendanceService;



	/**
	 * Mark attendance for students.
	 */

	@Override
	@Transactional
	public MessageResponse saveAttendance(List<DailyAttendanceDto> dailyAttendanceDto) {

		MessageResponse response = new MessageResponse();
		LocalDateTime today = LocalDateTime.now();

		LocalDate date = LocalDate.now();

		// List to save attendance
		List<DailyAttendanceModel> dailyAttendanceModelList = new ArrayList<>();

		// List to collect absent student IDs
		List<Long> absentStuIdList = new ArrayList<>();

		// List the students who have not been marked attendance
		List<Long> studentIdList = new ArrayList<>();

		// List to collect month to update the quarterly attendance
		List<Integer> quarterMonthList = new ArrayList<>();
		Long teacherId = null;
		Integer classOfStudy = null;
		// Integer updateFlag =0;
		LocalDate attendanceDate = null;
		DailyAttendanceModel dailyAttendanceModel;

		for (DailyAttendanceDto attendance : dailyAttendanceDto) {

			logger.info(
					"Before markStudentAttendance - Attempting to mark attendance StudentId : {}  for TeacherId: {}",
					attendance.getStudentId(), attendance.getTeacherId());

			// Field Validation
			List<String> requestFieldList = fieldValidation.attendanceFieldValidation(attendance);
			if (!requestFieldList.isEmpty()) {
				response.setStatus(WebServiceUtil.WARNING);
				response.setData(requestFieldList);
				return response;
			}
			// Verify if the teacher ID exists
			TeacherModel teacher = teacherRepository.findTeacherByTeacherId(attendance.getTeacherId());
			if (teacher == null) {
				response.setStatus(WebServiceUtil.WARNING);
				response.setData(WebServiceUtil.TEACHER_ID_ERROR);
				return response;
			}

			// Verify if studentId and attendanceDate already exist

			dailyAttendanceModel = dailyAttendanceRepository.findStudentIdAndAttendanceDate(attendance.getStudentId(),
					attendance.getAttendanceDate());

			// save the daily attendance
			if (dailyAttendanceModel == null) {

				// Verify if attendanceDate is holiday
				HolidayModel schoolHolidayModel = schoolHolidaysRepository
						.getHolidayByHolidayDate(attendance.getAttendanceDate());
				if (schoolHolidayModel != null && Boolean.FALSE.equals(schoolHolidayModel.getIsHolidayCancelled())) {
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
				
				//collect absent and ECA student id
				if (date.equals(attendance.getAttendanceDate()) && attendance.getAttendanceStatus().equals(WebServiceUtil.ABSENT)
						|| dailyAttendanceModel.getApprovedExtraCurricularActivitiesFlag().equals(WebServiceUtil.YES)
								 ) {
					absentStuIdList.add(attendance.getStudentId());
				}
				
				response.setData(WebServiceUtil.MARK_ATTENDANCE);
				logger.info("After markStudentAttendance - Attendace Marked successfully");

			} else {

				dailyAttendanceModel.setUpdateTeacher(teacher);
				dailyAttendanceModel.setUpdateTime(today);
				response.setData(WebServiceUtil.UPDATE_ATTENDANCE);
				logger.info("After markStudentAttendance - Attendace Updated successfully");
			}

			dailyAttendanceModel.setAttendanceStatus(
					studentCodeRespository.findStudentCodeByCode(attendance.getAttendanceStatus()));

			if (WebServiceUtil.PRESENT.equals(attendance.getAttendanceStatus())) {
				dailyAttendanceModel.setApprovedExtraCurricularActivitiesFlag(
						attendance.getApprovedExtraCurricularActivitiesFlag());
			} else {
				dailyAttendanceModel.setApprovedExtraCurricularActivitiesFlag(WebServiceUtil.NO);
			}

			if (WebServiceUtil.ABSENT.equals(attendance.getAttendanceStatus())) {
				dailyAttendanceModel.setLongApprovedSickLeaveFlag(attendance.getLongApprovedSickLeaveFlag());

			} else {
				dailyAttendanceModel.setLongApprovedSickLeaveFlag(WebServiceUtil.NO);

			}

			

			teacherId = attendance.getTeacherId();
			classOfStudy = attendance.getClassOfStudy();
			int monthValue = attendance.getAttendanceDate().getMonthValue();
			if (!quarterMonthList.contains(monthValue)) {
				quarterMonthList.add(monthValue);
			}
			dailyAttendanceModelList.add(dailyAttendanceModel);
		}

		// students who have not been marked attendance ,set default absent
		if (!studentIdList.isEmpty()) {
			List<Long> existsStudentId = studentModelRepository.findStudentIdByClassOfStudy(classOfStudy);
			List<Long> notTakenList = new ArrayList<>(existsStudentId);
			notTakenList.removeAll(studentIdList);

			if (!notTakenList.isEmpty()) {
				for (Long studentId : notTakenList) {
					dailyAttendanceModel = new DailyAttendanceModel();
					StudentModel student = studentModelRepository.findStudentByStudentId(studentId);

					dailyAttendanceModel.setStudentModel(student);
					TeacherModel teacher = teacherRepository.findTeacherByTeacherId(teacherId);
					dailyAttendanceModel.setCreateTeacher(teacher);
					dailyAttendanceModel.setCreateDate(today);
					dailyAttendanceModel.setAttendanceDate(attendanceDate);

					StudentCodeModel attendanceStatus = studentCodeRespository
							.findStudentCodeByCode(WebServiceUtil.ABSENT);
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

		// send mail to absent students
		if (!absentStuIdList.isEmpty()) {
			System.out.println("enter the email class");
			try {
				emailSentService.absentAlertEmail(absentStuIdList, teacherId);
			} catch (MessagingException e) {

				e.printStackTrace();
			}
		}
		response.setStatus(WebServiceUtil.SUCCESS);
		return response;
	}

	/**
	 * Retrieve student attendance for a particular date to check whether given filter.
	 *
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Response attendanceList(CommonFilterDto filterDto) {
		logger.info("Before getStudentAttendanceByDate - Attempting to retrive the student attendance");

		Map<String, Object> result = dailyAttendanceDao.attendanceList(filterDto);

		List<DailyAttendanceDto> attendanceList = (List<DailyAttendanceDto>) result.get("data");
		Long totalCount = dailyAttendanceRepository.findTotalCount(filterDto.getClassOfStudy());

		if (attendanceList != null) {

			if (WebServiceUtil.S_NO.equals(filterDto.getOrderColumn())
					&& WebServiceUtil.DESCENDING_ORDER.equals(filterDto.getOrderType())) {
				int sno = attendanceList.size();
				for (DailyAttendanceDto attendance : attendanceList) {
					attendance.setSno(sno--);
				}
			} else {
				int sno = 1;
				for (DailyAttendanceDto attendance : attendanceList) {
					attendance.setSno(sno++);
				}
			}
		}
		Response response = new Response();
		response.setStatus(WebServiceUtil.SUCCESS);
		response.setDraw(filterDto.getDraw());
		response.setTotalCount(totalCount);
		response.setFilterCount((Long) result.get("filterCount"));
		response.setData(attendanceList);
		logger.info("After getStudentAttendanceByDate - Successfully retrived student attendance");

		return response;
	}


	/**
	 * Retrieve students'monthly attendance list for a given filter
	 *
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Response monthlyAttendanceList(CommonFilterDto filterDto) {
		logger.info("Before getMonthlyAbsenceReport - Attempting to retrive the monthly absence studenet list");

		Map<String, Object> result = dailyAttendanceDao.monthlyAttendanceList(filterDto);

		List<MonthlyAbsenceDto> monthlyAttendanceList = (List<MonthlyAbsenceDto>) result.get("data");

		Long totalCount = dailyAttendanceRepository.findTotalCount(filterDto.getClassOfStudy());

		if (monthlyAttendanceList != null) {

			if (WebServiceUtil.S_NO.equals(filterDto.getOrderColumn())
					&& WebServiceUtil.DESCENDING_ORDER.equals(filterDto.getOrderType())) {
				int sno = monthlyAttendanceList.size();
				for (MonthlyAbsenceDto attendance : monthlyAttendanceList) {
					attendance.setSno(sno--);
				}
			} else {
				int sno = 1;
				for (MonthlyAbsenceDto attendance : monthlyAttendanceList) {
					attendance.setSno(sno++);
				}
			}
		}

		Response response = new Response();
		response.setStatus(WebServiceUtil.SUCCESS);
		response.setDraw(filterDto.getDraw());
		response.setTotalCount(totalCount);
		response.setFilterCount((Long) result.get("filterCount"));
		response.setData(monthlyAttendanceList);
		logger.info("After getMonthlyAbsenceReport - Successfully retrived monthly absence list");

		return response;
	}
}
