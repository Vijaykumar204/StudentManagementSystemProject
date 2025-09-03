package com.studentmanagementsystem.api.util;

import java.util.Arrays;
import java.util.List;


public final class WebServiceUtil {
	
//******************** Teacher*********************************
	
	public static final String ADMIN_TEACHER ="admin";
	public static final String TEACHER = "teacher";
	
//********************Student*********************************	
	public static final Character MALE = 'M';
	public static final Character FEMALE = 'F';
	public static final Character UNKNOWN = 'U';
	public static final Character HOSTEL = 'H';
	public static final Character DAYSCHOLAR = 'D';
	public static final Character ACTIVE ='A';
	public static final Character DEACTIVE ='D';
	
//******************** Student Message *********************************
	
	public static final String STUDENT_NOT_FOUND ="Student not found with ID:";
	public static final String NO_CHANGES = "No need to change already have %s status";
	public static final String A_OR_D_STATUS="Student %s";
	public static final String STUDENT_EXISTS ="Student name and DOB already exists";
	
//******************** SchoolHoliday *********************************
	
	public static final Boolean CANCEL_HOLIDAY=true;
	public static final Boolean HOLIDAY=false;
	public static final String EXTRA_CURRICULAR_ACTIVITIES = "approvedExtraCurricularActivitiesFlag";
	public static final String SICK_LEAVE = "longApprovedSickLeaveFlag";
	
//******************** SchoolHoliday Message *********************************
	public static final String SUCCESS_HOLIDAY_DECLARE="Successfully declare holiday";
	public static final String SUCCESS_CANCEL_HOLIDAY = "Successfully cancel the  holiday";
	
//******************** DailyAttendance *********************************
	

	public static final Character PRESENT = 'P';
	public static final Character ABSENT = 'A';	
	public static final Character YES = 'Y';
	public static final Character NO = 'N';
	public static final String COMPLIANCE = "C";
	public static final String NON_COMPLIANCE = "NC";
	public static final String COMPLIANCE_COMMENT ="Good";
	public static final String NON_COMPLIANCE_COMMENT ="Bad";
	
//******************** DailyAttendance Message  *********************************
	
	public static final String SUNDAY = " is Sunday,so no need to declare";
	public static final String DO_NOT_MARK_ATTENDANCE = " is declare holiday,so can't mark the Attendance";
	public static final String ALREADY_HOLIDAY_CANCELLED = "Declare date already cancelled : ";

//******************** QuarterlyAttendanceReport *********************************
	
	public static final List<Integer>MARCH_END    = Arrays.asList(1, 2, 3);
	public static final List<Integer>JUNE_END     = Arrays.asList(4, 5, 6);
	public static final List<Integer>SEP_END  = Arrays.asList(7, 8, 9);
	public static final List<Integer>DEC_END  = Arrays.asList(10, 11, 12);
	public static final String QUART_MARCH_AND_YEAR = "03/2025";
	public static final String QUART_JUNE_AND_YEAR  = "06/2025";
	public static final String QUART_SEP_AND_YEAR = "09/2025";
	public static final String QUART_DEC_AND_YEAR  = "12/2025";

//******************** QuarterlyAttendanceReport Message *********************************
	
//******************** StudentMarks *********************************
	
	public static final Character PASS = 'P';
	public static final Character FAIL = 'F';
	
//******************** StudentMarks Message  *********************************	
	
	

//******************** Date Pattern *********************************	
	
	public static final String APP_DATE_FORMAT = "yyyy-MM-dd";
	
//******************** Email Message *********************************	
	
	public static final String DEAR = "Dear ";
	public static final String ABSENT_ALERT_SUBJECT = " Attendance alert";
	
	public static final String 	SICK_LEVAE_SUBJECT  = "Sick leave alert";
	
	public static final String 	EXTRA_CUR_ACTIVITIES_LEAVE_SUBJECT  = "Extra curricular activity leave alert";
	
	public static final String ABSENT_ALERT_MESSAGE = "Attendance marked Absent on";
	
	public static final String SICK_LEVAE_MESSAGE = "Sick leave request accepted,Attendance marked Absent on ";
	
	public static final String EXTRA_CUR_ACTIVITIES_LEAVE_MESSAGE = " Extra curricular activity leave request accepted,Attendance marked Absent on ";
	 
	public static final String QUARTERLY_RESULT_REPORT_SUBJECT	 = "Quarterly result report";
	
	public static final String NULL_ERROR = "%s is required";
	
	public static final String REGEX_ERROR = "%s format error";
	
//******************** Response Status *********************************
	
	public static final String WARNING = "Warning";
	public static final String SUCCESS ="Success";
	public static final String SAVE ="Successfully saved";

//	public static final String NAME_ERROR = "Name is null";
//	public static final String NAME_REGEX_ERROR = "Name  can contain only letters ";
//	public static final String GENDER_ERROR = "Gender is null";
//	public static final String GENDER_VALIDATION_ERROR ="Gender  should only accept the values: Male(M), Female(F), or Unknown(U)";
//	public static final String STUDENT_CLASS_OF_STUDY_ERROR ="Student class os study must be 6 to 10";
//	public static final String DOB_ERROR = "Date of birth is null";
//	public static final String RESIDING_STATUS_VALIDATION_ERROR = "Residing status should only accept the values : Hostel(H) or Dayscholar(D)";
//	public static final String RESIDING_STATUS_ERROR = "Residing status is null";
//	public static final String PH_NO_ERROR = "Phone number is null";
//	public static final String PH_NO_REGEX_ERROR ="Phone number can contain only number(10 digit)";
//	public static final String EMAIL_ERROR ="Email is null";
//	public static final String EMAIL_REGEX_ERROR = "Email format error";
//	public static final String STREET_NAME_ERROR = "Street name is null";
//	public static final String CITY_NAME_ERROR = "% is null";
//	public static final String POSTAL_CODE_ERROR = "Postal code name is null";
	


	// School Holidays 
//	public static final String HOL_DATE_ERROR = "Holiday date is null";
//	public static final String HOL_REASON_ERROR = " Holiday reason is null ";
//	public static final String HOL_CANCALLED_REASON_ERROR = "Holiday cancalled  reason is null";
//	
//	
	
// Attendance

//	public static final String ATTENDANCE_DATE_ERROR = "Attendance date is null";
//	public static final String ATTENDANCE_STATUS_ERROR = "Attendance Status is null,must have presnt or absent";
//	public static final String STUDENT_ID_ERROR ="Student Id is null";
//	public static final String TEACHER_ID_ERROR = "Teacher Id is null";
	
	
//Student Mark
//	public static final String QUARTER_AND_YEAR_ERROR = "Quater and year is null";
//	public static final String TEACHER_DEPARTMENT ="Teacher department is null";
	
}
