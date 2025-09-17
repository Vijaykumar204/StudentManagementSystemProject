package com.studentmanagementsystem.api.restcontroller;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.io.exceptions.IOException;
import com.studentmanagementsystem.api.dao.DailyAttendanceDao;
import com.studentmanagementsystem.api.model.custom.dailyattendance.DailyAttendanceDto;
import com.studentmanagementsystem.api.model.custom.dailyattendance.DailyAttendanceFilterDto;
import com.studentmanagementsystem.api.model.custom.dailyattendance.MonthlyAbsenceDto;
import com.studentmanagementsystem.api.service.DailyAttendanceService;


@RestController
@RequestMapping(value = "attendance")
public class DailyAttendanceController {

	@Autowired
	private DailyAttendanceService dailyAttendanceService;
	

	
	@Autowired
	private DailyAttendanceDao dailyAttendanceDao;

//	/**
//	 * Mark attendance for a single student.
//	 *
//	 * @param dailyAttendanceDto The attendance details of the student (from request body)
//	 * @return Confirmation message indicating successful attendance marking
//	 * @author Vijiyakumar
//	 */
//	@PostMapping("/single")
//	
//	ResponseEntity<?> setAttendanceToSingleStudent(@RequestBody DailyAttendanceDto dailyAttendanceDto ){
//		return new ResponseEntity<>(dailyAttendanceService.setAttendanceToSingleStudent(dailyAttendanceDto),HttpStatus.OK);
//		
//	}

	/**
	 * Mark attendance for students.
	 *
	 * @param dailyAttendanceDto The list of attendance details of the student (from
	 *                           request body)
	 * @return Confirmation message indicating successful attendance marking
	 * @author Vijiyakumar
	 */
	@PostMapping
	ResponseEntity<?> markStudentAttendance(@RequestBody List<DailyAttendanceDto> dailyAttendanceDto,
			@RequestParam(required = false) LocalDate attendanceDate) {
		return new ResponseEntity<>(
				dailyAttendanceService.markStudentAttendance(dailyAttendanceDto, attendanceDate), HttpStatus.OK);
	}

	/**
	 * Retrieve student attendance for a particular date to check whether it was
	 * taken or not.
	 *
	 *@param dailyAttendanceFilterDto  
	 *  attendanceMark     The attendanceMark value: 
	 * 								 true →  attendance marked,
	 * 								 false → attendance not marked 
	 *  attendanceStatus   The attendanceStatus value :
	 * 								 PRESENT -> present students , 
	 * 								 ABSENT -> absent students , 
	 * 								 null ->all attendance list
	 *  attendanceDate     The specific date for which to retrieve attendance
	 *                     records
	 *  classOfStudy       The class of study for which attendance needs to be
	 *                     retrieved
	 *  (from Request body)                         
	 *  
	 * @return List of students with attendance status (marked or not marked) on the
	 *         given date
	 * @author Vijiyakumar
	 */
	@GetMapping("/filter")
	ResponseEntity<?> listStudentAttendance(@RequestBody DailyAttendanceFilterDto dailyAttendanceFilterDto) {
		return new ResponseEntity<>(
				dailyAttendanceService.listStudentAttendance(dailyAttendanceFilterDto),
				HttpStatus.OK);
	}


	/**
	 * Retrieve students' attendance records for a given month and year, based on the type of absence:
	 * 
	 *   Extra-curricular activity leave → more than 3 days in the given month</li>
	 *   Sick leave → more than 6 days in the given month</li>
	 *   Monthly absence → students absent during the given month</li>
	 *
	 * @param attendanceFlag  The attendance type: 
	 *                        null → Monthly absence, 
	 *                        true → Sick leave, 
	 *                        false → Extra-curricular activity leave
	 * @param month           The month for which to check leave records 
	 * @param year            The year for which to check leave records 
	 * @param classOfStudy    The class of study for which attendance records need to be retrieved
	 * @return                List of student absence records
	 * @author Vijiyakumar
	 */
	@GetMapping("/monthreport")
	ResponseEntity<?> getMonthlyAbsenceReport(@RequestBody DailyAttendanceFilterDto dailyAttendanceFilterDto) {
		return new ResponseEntity<>(
				dailyAttendanceService.getMonthlyAbsenceReport(dailyAttendanceFilterDto),
				HttpStatus.OK);
	}
	
//	  @PostMapping("/download")
//	    public ResponseEntity<byte[]> downloadMonthlyAbsenceReport(@RequestBody DailyAttendanceFilterDto dailyAttendanceFilterDto) throws IOException {
//
//	        // Get data from DB
//	        List<MonthlyAbsenceDto> reports =
//	                dailyAttendanceDao.getMonthlyAbsenceStudents(dailyAttendanceFilterDto);
//
//	        // Generate Excel
//	        ByteArrayInputStream in = excelGenerator.quarterlyAttendanceToExcel(reports);
//
//	        HttpHeaders headers = new HttpHeaders();
//	        headers.add("Content-Disposition", "attachment; filename=attendance_report.xlsx");
//
//	        return ResponseEntity.ok()
//	                .headers(headers)
//	                .contentType(MediaType.APPLICATION_OCTET_STREAM)
//	                .body(in.readAllBytes());
//	    }
//	

}
