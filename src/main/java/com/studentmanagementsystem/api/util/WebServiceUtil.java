package com.studentmanagementsystem.api.util;

import java.util.Arrays;
import java.util.List;

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
	
	

}
