package com.studentmanagementsystem.api.validation;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;
import com.studentmanagementsystem.api.model.custom.dailyattendance.DailyAttendanceDto;
import com.studentmanagementsystem.api.model.custom.schoolholidays.SchoolHolidaysDto;
import com.studentmanagementsystem.api.model.custom.student.StudentDto;
import com.studentmanagementsystem.api.model.custom.studentmarks.markDto;
import com.studentmanagementsystem.api.model.custom.teacher.TeacherDto;
import com.studentmanagementsystem.api.util.WebServiceUtil;

@Service
public class FieldValidation {

	private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z ]+$");
	private static final Pattern PHONE_PATTERN = Pattern.compile("^[0-9]{10}$");
	private static final Pattern EMAIL_PATTERN =Pattern.compile("^[a-z0-9]+@[a-z0-9]+[.](com|in)$");

	public List<String> studentFieldValidation(StudentDto studentDto) {
		List<String> requestMissedFieldList = new ArrayList<>();

		// student first name
		if (studentDto.getFirstName() == null || studentDto.getFirstName().isBlank()) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "studentFirstName"));
		} else if (!NAME_PATTERN.matcher(studentDto.getFirstName()).matches()) {
			requestMissedFieldList.add(String.format(WebServiceUtil.REGEX_ERROR, "studentFirstName"));
		}

		// student middle name
		  if (studentDto.getMiddleName() != null && studentDto.getMiddleName().isBlank()) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "studentMiddleName"));
		   }
		  else if (studentDto.getMiddleName() != null && !NAME_PATTERN.matcher(studentDto.getMiddleName()).matches()) {
				requestMissedFieldList.add(String.format(WebServiceUtil.REGEX_ERROR, "studentMiddleName"));
			}

		// student last name
		if (studentDto.getLastName() == null || studentDto.getLastName().isBlank()) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "studentLastName"));
		} else if (!NAME_PATTERN.matcher(studentDto.getLastName()).matches()) {
			requestMissedFieldList.add(String.format(WebServiceUtil.REGEX_ERROR, "studentLastName"));
		}

		// student date of birth
		if (studentDto.getDateOfBirth() == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "studentDateOfBirth"));
		}

		// student gender
		if (studentDto.getGender() == null || studentDto.getGender().isBlank()) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "studentGender"));
		} else if (!studentDto.getGender().equals(WebServiceUtil.MALE)
				&& !studentDto.getGender().equals(WebServiceUtil.FEMALE)
				&& !studentDto.getGender().equals(WebServiceUtil.UNKNOWN)) {
			requestMissedFieldList.add(String.format(WebServiceUtil.INVALID_CODE, "studentGender"));
		}

		// student class of study
		if (studentDto.getClassOfStudy() >= 0 && studentDto.getClassOfStudy() <= 5
				|| studentDto.getClassOfStudy() >= 11) {
			requestMissedFieldList.add(WebServiceUtil.CLASS_OF_STUDY_ERROR);
		} else if (studentDto.getClassOfStudy() == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "studentClassOfStudy"));
		}

		// student residing status
		if (studentDto.getResidingStatus() == null || studentDto.getResidingStatus().isBlank()) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "studentResidingStatus"));
		} else if (!studentDto.getResidingStatus().equals(WebServiceUtil.HOSTEL)
				&& !studentDto.getResidingStatus().equals(WebServiceUtil.DAYSCHOLAR)) {
			requestMissedFieldList.add(String.format(WebServiceUtil.INVALID_CODE, "studentResidingStatus"));
		}

		// student phone number
		if (studentDto.getPhoneNumber() == null || studentDto.getPhoneNumber().isBlank()) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "studentPhoneNumber"));
		} else if (!PHONE_PATTERN.matcher(studentDto.getPhoneNumber()).matches()) {
			requestMissedFieldList.add(String.format(WebServiceUtil.REGEX_ERROR, "studentPhoneNumber"));
		}

		// student email
		if (studentDto.getEmail() == null || studentDto.getEmail().isBlank()) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "studentEmail"));
		} else if (!EMAIL_PATTERN.matcher(studentDto.getEmail()).matches()) {
			requestMissedFieldList.add(String.format(WebServiceUtil.REGEX_ERROR, "studentEmail"));
		}

		// student parent's email
		if (studentDto.getParentsEmail() == null || studentDto.getParentsEmail().isBlank()) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "parentsEmail"));
		} else if (!EMAIL_PATTERN.matcher(studentDto.getParentsEmail()).matches()) {
			requestMissedFieldList.add(String.format(WebServiceUtil.REGEX_ERROR, "parentsEmail"));
		}

		// student home street name
		if (studentDto.getHomeStreetName() == null || studentDto.getHomeStreetName().isBlank()) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "homeStreetNamel"));
		}

		// student home city name
		if (studentDto.getHomeCityName() == null || studentDto.getHomeCityName().isBlank()) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "homeCityName"));
		}

		// student postal code
		if (studentDto.getHomePostalCode() == null || studentDto.getHomePostalCode().isBlank()) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "homePostalCode"));
		}

		if (studentDto.getStatus() == null || studentDto.getStatus().isBlank()) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "status"));
		} else if (!studentDto.getStatus().equals(WebServiceUtil.ACTIVE)
				&& !studentDto.getStatus().equals(WebServiceUtil.DEACTIVE)) {
			requestMissedFieldList.add(String.format(WebServiceUtil.INVALID_CODE, "status"));
		}

		// teacher id
		if (studentDto.getTeacherId() == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "teacherId"));
		}

		return requestMissedFieldList;
	}


	public List<String> statusFieldValidation(String studentActiveStatus, Long studentId,
			Long teacherId) {
		List<String> requestMissedFieldList = new ArrayList<>();
		if (!studentActiveStatus.equals(WebServiceUtil.ACTIVE) && !studentActiveStatus.equals(WebServiceUtil.DEACTIVE)) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "studentActiveStatus"));
		}

		if (studentId == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "studentId"));
		}
		if (teacherId == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, " teacherId"));
		}
		return requestMissedFieldList;
	}

	// Validation for holiday declare
	public List<String> holidayValidation(SchoolHolidaysDto schoolHolidaysDto) {

		List<String> missedFieldList = new ArrayList<>();

		if (schoolHolidaysDto.getHolidayDate() == null) {
			missedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "holidayDate"));
		}

		if (schoolHolidaysDto.getHolidayReason() == null || schoolHolidaysDto.getHolidayReason().isBlank()) {
			missedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "holidayReason"));
		}
		if (schoolHolidaysDto.getTeacherId() == null) {
			missedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "teacherId"));
		}

		return missedFieldList;
	}

	// Validation for cancel holidays
	public List<String> cancelHolidayValidation(SchoolHolidaysDto schoolHolidaysDto) {

		List<String> requestMissedFieldList = new ArrayList<>();

		if (schoolHolidaysDto.getHolidayDate() == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "holidayDate"));
		}

		if (schoolHolidaysDto.getHolidayCancelledReason() == null
				|| schoolHolidaysDto.getHolidayCancelledReason().isBlank()) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "holidayCancelledReason")
					+ schoolHolidaysDto.getHolidayDate());
		}
		return requestMissedFieldList;
	}
	
	//Validation for Attendance 
	public List<String> attendanceFieldValidation(DailyAttendanceDto dailyAttendanceDto) {
		List<String> requestMissedFieldList = new ArrayList<>();
		if (dailyAttendanceDto.getAttendanceDate() == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "attendanceDate"));
		}
		if (dailyAttendanceDto.getAttendanceStatus() == null || dailyAttendanceDto.getAttendanceStatus().isBlank()) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "attendanceStatus"));
		} else if ((!WebServiceUtil.PRESENT.equals(dailyAttendanceDto.getAttendanceStatus())
				&& !WebServiceUtil.ABSENT.equals(dailyAttendanceDto.getAttendanceStatus()))) {
			requestMissedFieldList.add(String.format(WebServiceUtil.INVALID_CODE, "attendanceStatus"));

		}
		if (dailyAttendanceDto.getStudentId() == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "studentId"));
		}
		if (dailyAttendanceDto.getTeacherId() == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "teacherId"));
		}

		if (dailyAttendanceDto.getClassOfStudy() == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "classOfStudy"));
		}
		
		if(dailyAttendanceDto.getAttendanceStatus().equalsIgnoreCase(WebServiceUtil.PRESENT) && WebServiceUtil.YES.equalsIgnoreCase(dailyAttendanceDto.getLongApprovedSickLeaveFlag())){
			requestMissedFieldList.add(WebServiceUtil.PRESENT_AND_SICK_LEAVE_ERROR);
		}
		
		if(dailyAttendanceDto.getAttendanceStatus().equalsIgnoreCase(WebServiceUtil.ABSENT) && WebServiceUtil.YES.equalsIgnoreCase(dailyAttendanceDto.getApprovedExtraCurricularActivitiesFlag() )){
			requestMissedFieldList.add(WebServiceUtil.ABSENT_AND_ECA_ERROR);
		}

		return requestMissedFieldList;
	}

	public List<String> checkValidationStudentMarkSave(markDto mark) {
		List<String> requestMissedFieldList = new ArrayList<>();
		if (mark.getStudentId() == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "studentId"));
		}
		if (mark.getTeacherId() == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "teacherId"));
		}

		if (mark.getQuarterAndYear() == null || mark.getQuarterAndYear().isBlank()) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "quarterAndYear"));
		} else if (!mark.getQuarterAndYear().equals(WebServiceUtil.QUART_MARCH_AND_YEAR)
				&& !mark.getQuarterAndYear().equals(WebServiceUtil.QUART_JUNE_AND_YEAR)
				&& !mark.getQuarterAndYear().equals(WebServiceUtil.QUART_SEP_AND_YEAR)
				&& !mark.getQuarterAndYear().equals(WebServiceUtil.QUART_DEC_AND_YEAR)) {
			requestMissedFieldList.add(String.format(WebServiceUtil.INVALID_CODE, "quarterAndYear"));
		}

		if (mark.getTamil() == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.SUBJECT_MARK_ERROR, "tamil",mark.getStudentId()));
		}
		if (mark.getEnglish() == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.SUBJECT_MARK_ERROR, "english",mark.getStudentId()));
		}
		if (mark.getMaths() == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.SUBJECT_MARK_ERROR, "maths",mark.getStudentId()));
		}
		if (mark.getScience() == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.SUBJECT_MARK_ERROR, "science",mark.getStudentId()));
		}
		if (mark.getSocialScience() == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.SUBJECT_MARK_ERROR, "socialScience",mark.getStudentId()));
		}
		return requestMissedFieldList;
	}
	
	// teacher Module validation
	public List<String> checkValidationTeacherSave(TeacherDto teacherModelListDto, Long teacherId) {
		List<String> requestMissedFieldList = new ArrayList<>();
		if (teacherModelListDto.getCreateTeacher() == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "createTeacher"));
		}

		if (teacherModelListDto.getName() == null || teacherModelListDto.getName().isBlank()) {

			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "name"));
		} else if (!NAME_PATTERN.matcher(teacherModelListDto.getName()).matches()) {
			requestMissedFieldList.add(String.format(WebServiceUtil.REGEX_ERROR, "name"));
		}
		if (teacherModelListDto.getEmail() == null || teacherModelListDto.getEmail().isBlank()) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "email"));
		} else if (!EMAIL_PATTERN.matcher(teacherModelListDto.getEmail()).matches()) {
			requestMissedFieldList.add(String.format(WebServiceUtil.REGEX_ERROR, "email"));
		}

		if (teacherModelListDto.getPhoneNumber() == null || teacherModelListDto.getPhoneNumber().isBlank()) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "PhoneNumber"));
		} else if (!PHONE_PATTERN.matcher(teacherModelListDto.getPhoneNumber()).matches()) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "phoneNumber"));
		}
		if (teacherModelListDto.getDepartment() == null || teacherModelListDto.getDepartment().isBlank()) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "department"));
		}
		if(!WebServiceUtil.ADMIN_TEACHER.equals(teacherModelListDto.getRole()) && !WebServiceUtil.TEACHER.equals(teacherModelListDto.getRole())) {
			requestMissedFieldList.add(String.format(WebServiceUtil.STUDENT_CODE_ERROR, "role"));
		}
		return requestMissedFieldList;
	}

}
