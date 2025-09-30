package com.studentmanagementsystem.api.serviceimpl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.studentmanagementsystem.api.dao.HolidayDao;
import com.studentmanagementsystem.api.model.custom.CommonFilterDto;
import com.studentmanagementsystem.api.model.custom.MessageResponse;
import com.studentmanagementsystem.api.model.custom.Response;
import com.studentmanagementsystem.api.model.custom.schoolholidays.HolidayFilterDto;
import com.studentmanagementsystem.api.model.custom.schoolholidays.SchoolHolidaysDto;
import com.studentmanagementsystem.api.model.entity.HolidayModel;
import com.studentmanagementsystem.api.model.entity.TeacherModel;
import com.studentmanagementsystem.api.repository.HolidayRepository;
import com.studentmanagementsystem.api.repository.TeacherRepository;
import com.studentmanagementsystem.api.service.HolidayService;
import com.studentmanagementsystem.api.util.WebServiceUtil;
import com.studentmanagementsystem.api.validation.FieldValidation;

import jakarta.transaction.Transactional;
@Service
public class HolidayServiceImpl implements HolidayService {
	
	private static final Logger logger = LoggerFactory.getLogger(HolidayServiceImpl.class);
	@Autowired
	private HolidayDao holidayDao;
	
	@Autowired
	private FieldValidation fieldValidation;
	
	@Autowired
	private HolidayRepository holidayRepository;
	
	@Autowired
	private TeacherRepository teacherRepository;


	/**
     * Retrive list of declared holidays
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Response declaredHolidaysList(HolidayFilterDto filterDto) {

		logger.info("Before getHolidays - Attempting to retrieve the holiday list");

		Response response = new Response();
		Map<String, Object> result = holidayDao.declaredHolidaysList(filterDto);

		List<SchoolHolidaysDto> holidayList = (List<SchoolHolidaysDto>) result.get("data");

		Long totalCount = holidayRepository.findTotalCount();

		if (holidayList != null) {

			if (WebServiceUtil.S_NO.equals(filterDto.getOrderColumn())
					&& WebServiceUtil.DESCENDING_ORDER.equals(filterDto.getOrderType())) {
				int sno = (int) ((filterDto.getLength() <= totalCount) ? filterDto.getLength() : totalCount);
				for (SchoolHolidaysDto holiday : holidayList) {
					holiday.setSno(sno--);
				}
			} else {
				int sno = (filterDto.getStart() > 0) ? filterDto.getStart() : 1;
				for (SchoolHolidaysDto holiday : holidayList) {
					holiday.setSno(sno++);
				}
			}
		}
		response.setStatus(WebServiceUtil.SUCCESS);
		response.setDraw(filterDto.getDraw());
		response.setFilterCount((Long) result.get("filterCount"));
		response.setTotalCount(totalCount);
		response.setData(holidayList);

		logger.info("After getHolidays -Successfully list holidays");

		return response;

	}


	/**
	 * Declare  holidays.
	 */
	@Override
	@Transactional
	public MessageResponse declareHolidays(List<SchoolHolidaysDto> schoolHolidaysDtoList) {
		
		logger.info("Before declareHolidays -Attempting declare the holidays");
		
		MessageResponse response = new MessageResponse();
		List<HolidayModel> holidaysList = new ArrayList<>();
		LocalDateTime currentDateTime = LocalDateTime.now();
		
		for (SchoolHolidaysDto holidaydeclare : schoolHolidaysDtoList) {
			
		//Field validation
          List<String> requestFieldList = fieldValidation.holidayValidation(holidaydeclare);
          if (!requestFieldList.isEmpty()) {
  			response.setStatus(WebServiceUtil.WARNING);	
  			response.setData(requestFieldList);		
  			return response;
  		   }
          
            // Verify if holidayDate is Sunday
			LocalDate date = holidaydeclare.getHolidayDate();
			if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
				response.setStatus(WebServiceUtil.WARNING);	
	  			response.setData(date + WebServiceUtil.SUNDAY_ERROR); // name change
				return response;
			}
			
			// Verify whether the teacher ID exists
			TeacherModel teacher = teacherRepository.findByTeacherId(holidaydeclare.getTeacherId());	
			if(teacher==null) {
				response.setStatus(WebServiceUtil.WARNING);	
				response.setData(WebServiceUtil.TEACHER_ID_ERROR);
				return response;
			}
			//Verify whether holidayDate is already declared
			HolidayModel holiday = holidayRepository.getByHolidayDate(holidaydeclare.getHolidayDate()) ;
			//Declare holidays
			if (holiday == null) {		
				holiday = new HolidayModel();
				holiday.setHolidayDate(holidaydeclare.getHolidayDate());
				holiday.setCreateTeacher(teacher);
				holiday.setCreateDate(currentDateTime);
				response.setData(WebServiceUtil.SUCCESS_HOLIDAY_DECLARE);
				logger.info("After declareHolidays - Declared holiday successfully");
			}
			//Update holidays
			else {
				holiday.setUpdateTeacher(teacher);
				holiday.setUpdateDate(currentDateTime);
				response.setData(WebServiceUtil.SUCCESS_HOLIDAY_UPDATE);
				logger.info("After declareHolidays - Declared holiday successfully");
				
			}
			holiday.setHolidayReason(holidaydeclare.getHolidayReason());			
			holidaysList.add(holiday);
		}
		
		holidayRepository.saveAll(holidaysList);
		response.setStatus(WebServiceUtil.SUCCESS);	
		return response;	
	}
	

	
	/**
	 * Cancel declared holiday.
	 */
	@Override
	@Transactional
	public MessageResponse cancelHolidays(List<SchoolHolidaysDto> cancelHolidaysDtoList) {
		logger.info("Before cancelHolidays - Attempting to cancel the holiday");
		MessageResponse response = new MessageResponse();
		List<HolidayModel> holidaysList = new ArrayList<>();
		LocalDateTime today = LocalDateTime.now();
		
		for (SchoolHolidaysDto holidayDeclare : cancelHolidaysDtoList) {
			
			//Field validation
			List<String> requestFieldList = fieldValidation.cancelHolidayValidation(holidayDeclare);
			if (!requestFieldList.isEmpty()) {
				response.setStatus(WebServiceUtil.WARNING);	
				response.setData(requestFieldList);		
				return response;
			}
			
			// Retrieve holiday date to cancel
			HolidayModel existsHoliday = holidayRepository.getByHolidayDate(holidayDeclare.getHolidayDate());
			// Verify if holidayDate is already cancelled
			
			if (Boolean.TRUE.equals(existsHoliday.getIsHolidayCancelled())) {
				response.setStatus(WebServiceUtil.SUCCESS);	
				response.setData(WebServiceUtil.SUCCESS_CANCEL_HOLIDAY);
				return response;
			}
			
			// Verify whether the teacher ID exists
			TeacherModel teacher = teacherRepository.findTeacherIdByTeacherId(holidayDeclare.getTeacherId());
			if(teacher==null) {
				response.setStatus(WebServiceUtil.WARNING);	
				response.setData(WebServiceUtil.TEACHER_ID_ERROR);
				return response;
			}	

			existsHoliday.setIsHolidayCancelled(true);
			existsHoliday.setHolidayCancelledReason(holidayDeclare.getHolidayCancelledReason());
			existsHoliday.setUpdateTeacher(teacher);
			existsHoliday.setUpdateDate(today);	
			holidaysList.add(existsHoliday);
		}		
		holidayRepository.saveAll(holidaysList);
		response.setStatus(WebServiceUtil.SUCCESS);	
		response.setData(WebServiceUtil.SUCCESS_CANCEL_HOLIDAY);
		logger.info("Before cancelHolidays - Holiday cancelled successfully");
		return response;
	}
}
