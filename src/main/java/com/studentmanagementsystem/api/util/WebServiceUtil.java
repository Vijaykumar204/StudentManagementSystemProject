package com.studentmanagementsystem.api.util;

import java.util.Arrays;
import java.util.List;

public final class WebServiceUtil {
	
	public static final char HOSTEL = 'H';
	public static final char DAYSCHOLAR = 'D';
	public static final char ACTIVE ='A';
	public static final char DEACTIVE ='D';
	public static final Boolean CANCEL_HOLIDAY=true;
	public static final Boolean HOLIDAY=false;
	public static final String EXTRA_CURRICULAR_ACTIVITIES = "approvedExtraCurricularActivitiesFlag";
	public static final String SICK_LEAVE = "longApprovedSickLeaveFlag";
	
//	public static final int marchEnd[] = {1,2,3};
//	public static final int juneEnd[]= {4,5,6};
//	public static final int septemberEnd[] = {7,8,9};
//	public static final int decemberEnd [] = {10,11,12};
	
	
	public static final char PRESENT = 'P';
	public static final char ABSENT = 'A';
	public static final char YES = 'Y';
	public static final char NO = 'N';
	public static final String COMPLIANCE = "compliance";
	public static final String NON_COMPLIANCE = "non-compliance";
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
	

}
