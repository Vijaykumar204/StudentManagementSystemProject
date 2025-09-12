package com.studentmanagementsystem.api.validation;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;
import com.studentmanagementsystem.api.model.custom.dailyattendance.DailyAttendanceDto;
import com.studentmanagementsystem.api.model.custom.schoolholidays.SchoolHolidaysDto;
import com.studentmanagementsystem.api.model.custom.student.StudentDto;
import com.studentmanagementsystem.api.model.custom.studentmarks.StudentMarksDto;
import com.studentmanagementsystem.api.model.custom.teacher.TeacherModelListDto;
import com.studentmanagementsystem.api.util.WebServiceUtil;

@Service
public class FieldValidation {

	private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z ]+$");
	private static final Pattern PHONE_PATTERN = Pattern.compile("^[0-9]{10}$");
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@mailanator\\.in$");

	public List<String> checkValidationStudentSaveMethod(StudentDto studentDto) {
		List<String> requestMissedFieldList = new ArrayList<>();
		
		// student first name
		if (studentDto.getFirstName() == null && studentDto.getFirstName() == " " ) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "studentFirstName"));
		} else if (!NAME_PATTERN.matcher(studentDto.getFirstName()).matches()) {
			requestMissedFieldList.add(String.format(WebServiceUtil.REGEX_ERROR, "studentFirstName"));
		}
		
		// student middle name
		if ( studentDto.getMiddleName() == " " ) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "studentMiddleName"));
		}
		else if (studentDto.getMiddleName() != null && !NAME_PATTERN.matcher(studentDto.getMiddleName()).matches())
		{
			requestMissedFieldList.add(String.format(WebServiceUtil.REGEX_ERROR, "studentMiddleName"));
		}
		
		// student last name
		if (studentDto.getLastName() == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "studentLastName"));
		} else if (!NAME_PATTERN.matcher(studentDto.getLastName()).matches()) {
			requestMissedFieldList.add(String.format(WebServiceUtil.REGEX_ERROR, "studentLastName"));
		}

		// student date of birth
		if (studentDto.getDateOfBirth() == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "studentDateOfBirth"));
		}

		// student gender
		if (studentDto.getGender() == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "studentGender"));
		} else if (!studentDto.getGender().equals(WebServiceUtil.MALE)
				&& !studentDto.getGender().equals(WebServiceUtil.FEMALE)
				&& !studentDto.getGender().equals(WebServiceUtil.UNKNOWN)) {
			requestMissedFieldList.add(String.format(WebServiceUtil.REGEX_ERROR, "studentGender"));
		}

		//student class of study
		if (studentDto.getClassOfStudy() <= 6 || studentDto.getClassOfStudy() >= 10) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "studentClassOfStudy"));
		}

		// student residing status
		if (studentDto.getResidingStatus() == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "studentResidingStatus"));
		} else if (!studentDto.getResidingStatus().equals(WebServiceUtil.HOSTEL)
				&& !studentDto.getResidingStatus().equals(WebServiceUtil.DAYSCHOLAR)) {
			requestMissedFieldList.add(String.format(WebServiceUtil.REGEX_ERROR, "studentResidingStatus"));
		}

		// student phone number
		if (studentDto.getPhoneNumber() == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "studentPhoneNumber"));
		} else if (!PHONE_PATTERN.matcher(studentDto.getPhoneNumber()).matches()) {
			requestMissedFieldList.add(String.format(WebServiceUtil.REGEX_ERROR, "studentPhoneNumber"));
		}

		// student email
		if (studentDto.getEmail() == null && studentDto.getEmail() == " ") {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "studentEmail"));
		} else if (!EMAIL_PATTERN.matcher(studentDto.getEmail()).matches()) {
			requestMissedFieldList.add(String.format(WebServiceUtil.REGEX_ERROR, "studentEmail"));
		}
		
		// student parent's email
		if (studentDto.getParentsEmail() == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "parentsEmail"));
		} else if (!EMAIL_PATTERN.matcher(studentDto.getParentsEmail()).matches()) {
			requestMissedFieldList.add(String.format(WebServiceUtil.REGEX_ERROR, "contactEmail"));
		}

		//student home street name
		if (studentDto.getHomeStreetName() == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "homeStreetNamel"));
		}

		//student home city name
		if (studentDto.getHomeCityName() == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "homeCityName"));
		}
		
		// student postal code
		if (studentDto.getHomePostalCode() == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "homePostalCode"));
		}
		
		//teacher id
		if (studentDto.getTeacherId() == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "teacherId"));
		}
		
		return requestMissedFieldList;
	}

	public List<String> checkValidationActiveOrDeactiveByStudentId(String studentActiveStatus, Long studentId,
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

	public List<String> checkValidationDeclareHoliday(SchoolHolidaysDto schoolHolidaysDto) {

		List<String> requestMissedFieldList = new ArrayList<>();
		if (schoolHolidaysDto.getHolidayDate() == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "holidayDate"));
		}
		if (schoolHolidaysDto.getHolidayReason() == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "holidayReason"));
		}
		return requestMissedFieldList;
	}

	public List<String> checkValidationCancelHolidayByDate(SchoolHolidaysDto schoolHolidaysDto) {

		List<String> requestMissedFieldList = new ArrayList<>();

		if (schoolHolidaysDto.getHolidayDate() == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "HolidayDate"));
		}

		if (schoolHolidaysDto.getHolidayCancelledReason() == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "holidayCancelledReason")
					+ schoolHolidaysDto.getHolidayDate());
		}
		return requestMissedFieldList;
	}

	public List<String> checkValidationSetAttendanceToSingleStudent(DailyAttendanceDto dailyAttendanceDto) {
		List<String> requestMissedFieldList = new ArrayList<>();
		if (dailyAttendanceDto.getAttendanceDate() == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "attendanceDate"));
		}
		if (dailyAttendanceDto.getAttendanceStatus() == null
				|| (!WebServiceUtil.PRESENT.equals(dailyAttendanceDto.getAttendanceStatus())
						&& !WebServiceUtil.ABSENT.equals(dailyAttendanceDto.getAttendanceStatus()))) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "attendanceStatus"));
		}
		if (dailyAttendanceDto.getStudentId() == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "studentId"));
		}
		if (dailyAttendanceDto.getTeacherId() == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "teacherId"));
		}

		return requestMissedFieldList;
	}

	public List<String> checkValidationStudentMarkSave(StudentMarksDto mark) {
		List<String> requestMissedFieldList = new ArrayList<>();
		if (mark.getStudentId() == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "teacherId"));
		}
		if (mark.getTeacherId() == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "teacherId"));
		}
		if (mark.getQuarterAndYear() == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "teacherId"));
		}
		if (mark.getTamil() == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "tamil"));
		}
		if (mark.getEnglish() == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "english"));
		}
		if (mark.getMaths() == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "maths"));
		}
		if (mark.getScience() == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "science"));
		}
		if (mark.getSocialScience() == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "socialScience"));
		}
		return requestMissedFieldList;
	}

	public List<String> checkValidationTeacherSave(TeacherModelListDto teacherModelListDto, Long teacherId) {
		List<String> requestMissedFieldList = new ArrayList<>();
		if (teacherId == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "teacherId"));
		}

		if (teacherModelListDto.getTeacherName() == null) {

			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "teacherName"));
		} else if (!NAME_PATTERN.matcher(teacherModelListDto.getTeacherName()).matches()) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "teacherName"));
		}

		if (teacherModelListDto.getTeacherPhoneNumber() == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "teacherPhoneNumber"));
		} else if (!PHONE_PATTERN.matcher(teacherModelListDto.getTeacherPhoneNumber()).matches()) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "teacherPhoneNumber"));
		}
		if (teacherModelListDto.getTeacherDepartment() == null) {
			requestMissedFieldList.add(String.format(WebServiceUtil.NULL_ERROR, "teacherDepartment"));
		}
		return requestMissedFieldList;
	}

}
