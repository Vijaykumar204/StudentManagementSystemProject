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
import com.studentmanagementsystem.api.model.custom.schoolholidays.SchoolHolidayFilterDto;
import com.studentmanagementsystem.api.model.custom.schoolholidays.SchoolHolidaysDto;
import com.studentmanagementsystem.api.service.SchoolHolidaysService;

@RestController
@RequestMapping(value = "holidays")
public class SchoolHolidaysController {

	@Autowired
	private SchoolHolidaysService schoolHolidaysService;

	/**
	 * Declare  holidays.
	 *
	 * @param schoolHolidaysDto The list of holidays to be declared( from request
	 *                          body)
	 * @return Confirmation message
	 * @author Vijiyakumar
	 */
	@PostMapping("/declare")
	ResponseEntity<?> declareHolidays(@RequestBody List<SchoolHolidaysDto> schoolHolidaysDto) {
		return new ResponseEntity<>(schoolHolidaysService.declareHolidays(schoolHolidaysDto), HttpStatus.OK);
	}

	/**
	 * Cancel declared holiday.
	 *
	 * @param schoolHolidayDto The list of holiday date and reason to be
	 *                         canceled(from request body)
	 * @return Confirmation message
	 * @author Vijiyakumar
	 */
	@PostMapping("/cancel")
	ResponseEntity<?> cancelHolidays(@RequestBody List<SchoolHolidaysDto> schoolHolidaysDto) {
		return new ResponseEntity<>(schoolHolidaysService.cancelHolidays(schoolHolidaysDto), HttpStatus.OK);
	}

	/**
	 * Retrieves the list of active or canceled declared holidays.
	 *
	 * @param isHolidayCancelled Indicates the holiday status: 
	 *                           true → canceled holidays, 
	 *                           false → active holidays, 
	 *                           null → all holidays.
	 * @param month              The month for which holidays are to be retrieved.
	 * @param year               The year for which holidays are to be retrieved.
	 * @param searchValue        (optional) The search value, e.g., holiday reason.
	 * @return Return list of active or canceled declared holidays.
	 * @author Vijiyakumar
	 */

	@GetMapping("/list")
	ResponseEntity<?> listDeclaredHolidays(@RequestBody SchoolHolidayFilterDto schoolHolidayFilterDto ) {
		return new ResponseEntity<>(schoolHolidaysService.declaredHolidaysList(schoolHolidayFilterDto), HttpStatus.OK);
	}

}
