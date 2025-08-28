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
	
	@PostMapping("/multipledeclare")
	ResponseEntity<?> declareMultipleHolidays(@RequestBody List<SchoolHolidaysDto> schoolHolidaysDto){
		return new ResponseEntity<>(schoolHolidaysService.declareMultipleHolidays(schoolHolidaysDto),HttpStatus.OK);
	}

	@PostMapping("/declare")
	ResponseEntity<?> declareHoliday(@RequestBody SchoolHolidaysDto schoolHolidaysDto){
		return new ResponseEntity<>(schoolHolidaysService.declareHoliday(schoolHolidaysDto),HttpStatus.OK);
	}

	
	@PostMapping("/cancel")
	ResponseEntity<?> cancelHolidayByDate(@RequestBody SchoolHolidaysDto schoolHolidaysDto){
		return new ResponseEntity<>(schoolHolidaysService.cancelHolidayByDate(schoolHolidaysDto),HttpStatus.OK);
	}
	
	@PostMapping("multiplecancel")
	ResponseEntity<?> cancelMultipleHoliday(@RequestBody List<SchoolHolidaysDto> schoolHolidaysDto){
		return new ResponseEntity<>(schoolHolidaysService.cancelMultipleHoliday(schoolHolidaysDto),HttpStatus.OK);
	}
	@GetMapping
	ResponseEntity<List<SchoolHolidaysDto>> getHolidays(){
		return new ResponseEntity<List<SchoolHolidaysDto>>(schoolHolidaysService.getHolidays(),HttpStatus.OK);
	}
	
	@GetMapping("/cancelHolidays")
	ResponseEntity<List<SchoolHolidaysDto>> getCancelHolidays(){
		return new ResponseEntity<List<SchoolHolidaysDto>>(schoolHolidaysService.getCancelHolidays(),HttpStatus.OK);
	}

	
	

}
