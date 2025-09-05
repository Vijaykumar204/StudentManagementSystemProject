package com.studentmanagementsystem.api.restcontroller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studentmanagementsystem.api.model.custom.schoolholidays.SchoolHolidaysDto;
import com.studentmanagementsystem.api.service.SchoolHolidaysService;

@RestController
@RequestMapping(value = "holidays")
public class SchoolHolidaysController {
	
	@Autowired
	private SchoolHolidaysService schoolHolidaysService;
	
	/**
	 * Declare multiple holidays.
	 *
	 * Author: Vijayakumar
	 * @param schoolHolidaysDto The list of holidays to be declared( from request body)
	 * @return  Confirmation message 
	 */

	
	@PostMapping("/multipledeclare")
	ResponseEntity<?> declareMultipleHolidays(@RequestBody List<SchoolHolidaysDto> schoolHolidaysDto){
		return new ResponseEntity<>(schoolHolidaysService.declareMultipleHolidays(schoolHolidaysDto),HttpStatus.OK);
	}
	
	/**
	 * Declare a  holiday.
	 *
	 * Author: Vijayakumar
	 * @param schoolHolidayDto The holiday details to be declared (from request body)
	 * @return  Confirmation message 
	 */

	@PostMapping("/declare")
	ResponseEntity<?> declareHoliday(@RequestBody SchoolHolidaysDto schoolHolidaysDto){
		return new ResponseEntity<>(schoolHolidaysService.declareHoliday(schoolHolidaysDto),HttpStatus.OK);
	}

	/**
	 * Cancel a declared holiday.
	 *
	 * Author: Vijayakumar
	 * @param schoolHolidayDto The holiday date and reason to be canceled(from request body)
	 * @return Confirmation message 
	 */

	
	
	@PostMapping("/cancel")
	ResponseEntity<?> cancelHolidayByDate(@RequestBody SchoolHolidaysDto schoolHolidaysDto){
		return new ResponseEntity<>(schoolHolidaysService.cancelHolidayByDate(schoolHolidaysDto),HttpStatus.OK);
	}
	
	/**
	 * Cancel multilple declared holiday.
	 *
	 * Author: Vijayakumar
	 * @param schoolHolidayDto The list of holiday date and reason to be canceled(from request body)
	 * @return Confirmation message 
	 */
	
	
	@PostMapping("multiplecancel")
	ResponseEntity<?> cancelMultipleHoliday(@RequestBody List<SchoolHolidaysDto> schoolHolidaysDto){
		return new ResponseEntity<>(schoolHolidaysService.cancelMultipleHoliday(schoolHolidaysDto),HttpStatus.OK);
	}
	
	/**
	 *  Retrieve the list of declared holidays.
	 *
	 * Author: Vijayakumar

	 * @return List of declared holidays
	 */
	
	@GetMapping
	ResponseEntity<?> getHolidays(){
		return new ResponseEntity<>(schoolHolidaysService.getHolidays(),HttpStatus.OK);
	}
	
	/**
	 *  Retrieve the list of cancel holidays.
	 *
	 * Author: Vijayakumar

	 * @return List of cancel holidays
	 */
	
	@GetMapping("/cancelHolidays")
	ResponseEntity<?> getCancelHolidays(){
		return new ResponseEntity<>(schoolHolidaysService.getCancelHolidays(),HttpStatus.OK);
	}

	
	

}
