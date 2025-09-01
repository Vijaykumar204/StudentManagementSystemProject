package com.studentmanagementsystem.api.util;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public final class WebServiceUtil {
	
	public static final String ADMIN_TEACHER ="admin";
	public static final String TEACHER = "teacher";
	
	public static final char HOSTEL = 'H';
	public static final char DAYSCHOLAR = 'D';
	public static final char ACTIVE ='A';
	public static final char DEACTIVE ='D';
	public static final Boolean CANCEL_HOLIDAY=true;
	public static final Boolean HOLIDAY=false;
	public static final String EXTRA_CURRICULAR_ACTIVITIES = "approvedExtraCurricularActivitiesFlag";
	public static final String SICK_LEAVE = "longApprovedSickLeaveFlag";
	

	public static final char PRESENT = 'P';
	public static final char ABSENT = 'A';	
	public static final char YES = 'Y';
	public static final char NO = 'N';
	public static final String COMPLIANCE = "C";
	public static final String NON_COMPLIANCE = "NC";
	public static final String COMPLIANCE_COMMENT ="Good";
	public static final String NON_COMPLIANCE_COMMENT ="Bad";
	public static final List<Integer>MARCH_END    = Arrays.asList(1, 2, 3);
	public static final List<Integer>JUNE_END     = Arrays.asList(4, 5, 6);
	public static final List<Integer>SEP_END  = Arrays.asList(7, 8, 9);
	public static final List<Integer>DEC_END  = Arrays.asList(10, 11, 12);
	public static final String QUART_MARCH_AND_YEAR = "03/2025";
	public static final String QUART_JUNE_AND_YEAR  = "06/2025";
	public static final String QUART_SEP_AND_YEAR = "09/2025";
	public static final String QUART_DEC_AND_YEAR  = "12/2025";
	
	
	public static final char PASS = 'P';
	public static final char FAIL = 'F';
	
	
	public static final char MALE = 'M';
	public static final char FEMALE = 'F';
	public static final char UNKNOWN = 'U';
	
	public static final String APP_DATE_FORMAT = "yyyy-MM-dd";
	
	public static final String DEAR = "Dear ";
	public static final String ABSENT_ALERT_SUBJECT = " Attendance alert";
	
	public static final String 	SICK_LEVAE_SUBJECT  = "Sick leave alert";
	
	public static final String 	EXTRA_CUR_ACTIVITIES_LEAVE_SUBJECT  = "Extra curricular activity leave alert";
	
	public static final String ABSENT_ALERT_MESSAGE = "Attendance marked Absent on";
	
	public static final String SICK_LEVAE_MESSAGE = "Sick leave request accepted,Attendance marked Absent on ";
	
	public static final String EXTRA_CUR_ACTIVITIES_LEAVE_MESSAGE = " Extra curricular activity leave request accepted,Attendance marked Absent on ";
	 
	public static final String QUARTERLY_RESULT_REPORT_SUBJECT	 = "Quarterly result report";

	//
	public static final String NAME_ERROR = "Name is null";
	public static final String NAME_REGEX_ERROR = "Name  can contain only letters ";
	public static final String GENDER_ERROR = "Gender is null";
	public static final String GENDER_VALIDATION_ERROR ="Gender  should only accept the values: Male(M), Female(F), or Unknown(U)";
	public static final String STUDENT_CLASS_OF_STUDY_ERROR ="Student class os study must be 6 to 10";
	public static final String DOB_ERROR = "Date of birth is null";
	public static final String RESIDING_STATUS_VALIDATION_ERROR = "Residing status should only accept the values : Hostel(H) or Dayscholar(D)";
	public static final String RESIDING_STATUS_ERROR = "Residing status is null";
	public static final String PH_NO_ERROR = "Phone number is null";
	public static final String PH_NO_REGEX_ERROR ="Phone number can contain only number(10 digit)";
	public static final String EMAIL_ERROR ="Email is null";
	public static final String EMAIL_REGEX_ERROR = "Email format error";
	public static final String STREET_NAME_ERROR = "Street name is null";
	public static final String CITY_NAME_ERROR = "% is null";
	public static final String POSTAL_CODE_ERROR = "Postal code name is null";
	

	// School Holidays 
	public static final String HOL_DATE_ERROR = "Holiday date is null";
	public static final String HOL_REASON_ERROR = " Holiday reason is null ";
	public static final String HOL_CANCALLED_REASON_ERROR = "Holiday cancalled  reason is null";
	public static final String SUNDAY = " is Sunday,so no need to declare";
	public static final String ALREADY_HOLIDAY_CANCELLED = "Declare date already cancelled : ";
	
// Attendance
	public static final String DO_NOT_MARK_ATTENDANCE = " is declare holiday,so can't mark the Attendance";
	public static final String ATTENDANCE_DATE_ERROR = "Attendance date is null";
	public static final String ATTENDANCE_STATUS_ERROR = "Attendance Status is null,must have presnt or absent";
	public static final String STUDENT_ID_ERROR ="Student Id is null";
	public static final String TEACHER_ID_ERROR = "Teacher Id is null";
	
	
//Student Mark
	public static final String QUARTER_AND_YEAR_ERROR = "Quater and year is null";
	public static final String TEACHER_DEPARTMENT ="Teacher department is null";
	
}
