package com.studentmanagementsystem.api.util;

import java.util.Arrays;
import java.util.List;


public final class WebServiceUtil {
	
//******************** Teacher*********************************
	
	public static final String ADMIN_TEACHER ="admin";
	public static final String TEACHER = "teacher";
	
//********************Student*********************************	
	public static final String MALE = "MALE";
	public static final String FEMALE = "FEMALE";
	public static final String UNKNOWN = "UNKNOWN";
	public static final String HOSTEL = "HOSTEL";
	public static final String DAYSCHOLAR = "DAYSCHOLAR";
	public static final String ACTIVE ="ACTIVE";
	public static final String DEACTIVE ="DEACTIVE";
	
//******************** Student Message *********************************
	
	public static final String STUDENT_NOT_FOUND ="Student ID not found ";
	public static final String NO_CHANGES = "No need to change already have %s status";
	public static final String ACTIVETED_MESSAGE="Student activated successfully";
	public static final String DEACTIVETED_MESSAGE="Student deactivated successfully";
	public static final String STUDENT_EXISTS ="Student name and DOB already exists";
	
//******************** SchoolHoliday Message *********************************
	
	public static final Boolean CANCEL_HOLIDAY=true;
	public static final Boolean HOLIDAY=false;
	public static final String EXTRA_CURRICULAR_ACTIVITIES = "approvedExtraCurricularActivitiesFlag";
	public static final String SICK_LEAVE = "longApprovedSickLeaveFlag";
	public static final String SUCCESS_HOLIDAY_DECLARE="Holidays declared successfully";
	public static final String SUCCESS_HOLIDAY_UPDATE = "Holidays updated successfully";
	public static final String SUCCESS_CANCEL_HOLIDAY = "Holidays cancelled successfully";
	
//******************** DailyAttendance *********************************
	

	public static final String PRESENT = "PRESENT";
	public static final String ABSENT = "ABSENT";	
	public static final String YES = "Y";
	public static final String NO = "N";
	public static final String COMPLIANCE = "COMPLIANCE";
	public static final String NON_COMPLIANCE = "NON_COMPLIANCE";
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
	public static final Integer YEAR = 2025;

//******************** QuarterlyAttendanceReport Message *********************************
	
//******************** StudentMarks *********************************
	
	public static final String PASS = "PASS";
	public static final String FAIL = "FAIL";
	
//******************** StudentMarks Message  *********************************	
	
	

//******************** Date Pattern *********************************	
	
	public static final String APP_DATE_FORMAT = "yyyy-MM-dd";
	
//******************** Email Message *********************************	
	
//	public static final String DEAR = "Dear ";
////	public static final String ABSENT_ALERT_SUBJECT = " Attendance alert";
//	
//	public static final String 	SICK_LEVAE_SUBJECT  = "Sick leave alert";
//	
//	public static final String 	EXTRA_CUR_ACTIVITIES_LEAVE_SUBJECT  = "Extra curricular activity leave alert";
//	
//	public static final String ABSENT_ALERT_MESSAGE = "Attendance marked Absent on";
//	
//	public static final String SICK_LEVAE_MESSAGE = "Sick leave request accepted,Attendance marked Absent on ";
//	
//	public static final String EXTRA_CUR_ACTIVITIES_LEAVE_MESSAGE = " Extra curricular activity leave request accepted,Attendance marked Absent on ";
	 

	
	public static final String QUARTERLY_ATTENDANCE_REPORT_SUBJECT ="Academic Attendance Report for  ";
	
	public static final String QUARTERLY_ATTENDANCE_REPORT_BODY="""
            <html>
            <body style="font-family: Arial, sans-serif; line-height: 1.6;">
            
                <p>Dear Sir/Madam,</p>
                <p>    I am writing to share the academic attendance report of your child, 
                   <b>%s</b>, for the quarter <b>%s</b>.</p>
                
                <h3 > Attendance Report</h3>
                
                <table border="1" cellspacing="0" cellpadding="8" style="border-collapse: collapse; width: 60%%;">
                    <tr style="background-color: #f2f2f2;">
                        <th align="left">Metric</th>
                        <th align="left">Value</th>
                    </tr>
                    <tr>
                        <td><b>Total Working Days</b></td>
                        <td>%d</td>
                    </tr>
                    <tr>
                        <td><b>Days Present</b></td>
                        <td>%d</td>
                    </tr>
                    <tr>
                        <td><b>Days Absent</b></td>
                        <td>%d</td>
                    </tr>
                    <tr>
                        <td><b>Compliance Status</b></td>
                        <td>%s</td>
                    </tr>
                </table>
                
                <p>Thank you for your continuous support in nurturing <b>%s</b>’s education.</p>
                
                <br>
                <p>Warm regards,<br>
                <b>Class Teacher</b><br>
                Hum School, Chennai</p>
                
            </body>
            </html>
            """ ;
	
	public static final String QUARTERLY_MARK_REPORT_SUBJECT ="Academic progress Report for  ";
	
			
	public static final String QUARTERLY_MARK_REPORT_BOBY="""
	        <p>Dear Sir/Madam,</p>
	        <p>   I am writing to share the academic progress report of your child, <b>%s</b>, for the quarter <b>%s</b>.</p>
	        
	        <h3> Performance Summary</h3>
	         <table border="1" cellspacing="0" cellpadding="8" style="border-collapse: collapse; width: 60%%;">
                    <tr style="background-color: #f2f2f2;">
                        <th align="left">Description</th>
                        <th align="left">Mark</th>
                    </tr>
                    <tr>
                        <td><b>Tamil</b></td>
                        <td>%d</td>
                    </tr>
                    <tr>
                        <td><b>English</b></td>
                        <td>%d</td>
                    </tr>
                    <tr>
                        <td><b>Maths</b></td>
                        <td>%d</td>
                    </tr>
                    <tr>
                        <td><b>Science</b></td>
                        <td>%d</td>
                    </tr>
                    <tr>
                        <td><b>Social Science</b></td>
                        <td>%d</td>
                    </tr>
                    <tr>
                        <td><b>Total mark obtained</b></td>
                        <td>%d</td>
                    </tr>
                    <tr>
                        <td><b>Result</b></td>
                        <td>%s</td>
                    </tr>
                    
                </table>
	        
	        <p>Thank you for your continuous support in nurturing %s’s education.</p>
	        
	        <br>
	        <p>Warm regards,<br>
	        Class Teacher<br>
	         Hum School, Chennai</p>
	        """;	
	
	public static final String ABSENT_ALERT_SUBJECT = "Attendance Alert – Absence of %s on %s";
	
	public static final String ABSENT_ALERT_BODY = """
            <p>Dear Sir/Madam,</p>

            <p>This is to inform you that your child, <b>%s</b>, was marked as <b>Absent</b> on <b>%s</b>.</p>

            <p>We kindly request you to ensure that %s attends classes regularly, as consistent attendance is important for academic performance and overall development.</p>

            <p>If the absence was due to illness or any other valid reason, please provide a leave note or inform us accordingly.</p>

            <br>
            <p>Thank you for your support and cooperation.</p>

            <p>
            Warm regards,<br>
            <b>Class Teacher</b><br>
            Hum School, Chennai
            </p>
            """;
	
	public static final String SICK_LEAVE_ALERT_SUBJECT ="Sick Leave Approval for %s";
	public static final String SICK_LEAVE_ALERT_BODY =  """
			<p>Dear Sir/Madam,</p>
            <p> This is to inform you that the sick leave request submitted for your ward, %s (Class %d), 
            on %s has been approved.</p>
            <p>  Please ensure your child takes proper rest and resumes school once fully recovered. 
             If additional leave is required, kindly notify us in advance.</p>
             <p>
            Warm regards,<br>
            <b>Class Teacher</b><br>
            Hum School, Chennai
            </p> """;
	
	public static final String EXTRA_CUR_ACTIVITY_ALERT_SUBJECT="Extra-Curricular Activity Leave Notification for %s";
	public static final String EXTRA_CUR_ACTIVITY_ALERT_BODY = """ 
			
		<p>	Dear Sir/Madam,</p>
			
			<p>This is to inform you that your ward, %s, has been granted leave on %s to participate in an extra-curricular activity </p>
			
			<h5>Details:</h5>
			<p>- Student Name: %s </p>
			
			<p>- Leave Date: %s </p>
			<p>- Leave Type: Extra-Curricular Activity </p>
			<p>- Status: Approved </p>
			
			<p> Please ensure that your child participates responsibly and returns to school on the following day. If there are any special requirements or questions, feel free to contact us.</p>
			    <p>
            Warm regards,<br>
            <b>Class Teacher</b><br>
            Hum School, Chennai
            </p>
			   """;
	
	//******************** Validation Message *********************************		
	
	public static final String NULL_ERROR = "%s is required";
	
	public static final String CLASS_OF_STUDY_ERROR = "Class of study must be between 6 and 10";
	public static final String REGEX_ERROR = "Invalid %s format";
	
//******************** Response Status *********************************
	
	public static final String WARNING = "Warning";
	
	public static final String SUCCESS ="Success";
	public static final String SAVE ="%s saved successfully";
	public static final String UPDATE = "%s updated successfully";
	public static final String MARK_ATTENDANCE = "Attendance marked successfully";
	public static final String FAILURE = "Failure";
	public static final String DECLARE_MARK="Successfully declared mark";
	public static final String STUDENT_ATTENDANCE_STATUS =" student attendance is non compliance,so does not declare the mark";
	public static final String TEACHER_ID_ERROR = "Invalid Teacher ID. Please enter a valid one";
	
	public static final String INVALID_CODE = "%s invalid";

	
	
	//******************** code error *********************************

	public static final String INVAILD = " %s invalid";
	

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
