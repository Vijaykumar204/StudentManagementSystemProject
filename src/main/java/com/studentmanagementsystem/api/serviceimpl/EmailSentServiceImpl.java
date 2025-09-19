package com.studentmanagementsystem.api.serviceimpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.studentmanagementsystem.api.dao.QuarterlyAttendanceReportDao;
import com.studentmanagementsystem.api.model.custom.Response;
import com.studentmanagementsystem.api.model.custom.quarterlyreport.QuarterlyAttendanceReportDto;
import com.studentmanagementsystem.api.model.custom.studentmarks.StudentMarksDto;
import com.studentmanagementsystem.api.model.entity.DailyAttendanceModel;
import com.studentmanagementsystem.api.model.entity.EmailSentHistory;
import com.studentmanagementsystem.api.model.entity.MarkModel;
import com.studentmanagementsystem.api.model.entity.StudentModel;
import com.studentmanagementsystem.api.model.entity.TeacherModel;
import com.studentmanagementsystem.api.repository.DailyAttendanceRepository;
import com.studentmanagementsystem.api.repository.EmailSentHistoryRepository;
import com.studentmanagementsystem.api.repository.StudentMarksRepository;
import com.studentmanagementsystem.api.repository.StudentModelRepository;
import com.studentmanagementsystem.api.repository.TeacherRepository;
import com.studentmanagementsystem.api.service.EmailSentService;
import com.studentmanagementsystem.api.util.WebServiceUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class EmailSentServiceImpl implements EmailSentService {
	
	private static final Logger logger = LoggerFactory.getLogger(EmailSentServiceImpl.class);

	
	  @Autowired
	    private JavaMailSender mailSender;
	  
	  @Autowired
	  private StudentModelRepository studentModelRepository;
	  
	  @Autowired
	  private EmailSentHistoryRepository emailSentHistoryRepository;
	  
	  
	  @Autowired
	  private DailyAttendanceRepository dailyAttendanceRepository;
	  
	 
	  @Autowired
	  private QuarterlyAttendanceReportDao quarterlyAttendanceReportDao;
	  
	  @Autowired
	  private StudentMarksRepository studentMarksRepository;
	  
	  @Autowired
	  private TeacherRepository teacherRepository;

	
	
  /*
   * send mail to student parent's quarter academic attendance report
   */	
	public Response sendQuarterlyAttendanceReport(String quarterAndResult,Integer classOfStudy,Long teacherId) throws MessagingException {
		
		logger.info("Before sendQuarterlyAttendanceReport - Attempting sent the quarterly attendance report for TeacherId: {}", teacherId);
		
		Response response = new Response();
		
//		List<StudentMarksDto> studentMarklist = studentMarksDao.getAllStudentMarks(quarterAndResult);

		 List<QuarterlyAttendanceReportDto> quarterAttendanceList = quarterlyAttendanceReportDao.getQuarterlyAttendanceReport(quarterAndResult,classOfStudy);
		
		
		 for(QuarterlyAttendanceReportDto quarter : quarterAttendanceList) {
				
			StudentModel student = studentModelRepository.findStudentByStudentId(quarter.getStudentId());
			String name;
			String attendanceStatus;
			if(student.getMiddleName()!=null) {
	        name =student.getFirstName()+" "+student.getMiddleName()+" "+student.getLastName();
			}
			else {
			 name =student.getFirstName()+" "+student.getLastName();
			}
			
			if(quarter.getAttendanceComplianceStatus() == WebServiceUtil.COMPLIANCE){
				attendanceStatus="Compliance";
			}
			else {
				attendanceStatus="Non - compliance";
			}

			MimeMessage message = mailSender.createMimeMessage();

	        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

	        helper.setTo(student.getEmail());
	        String subject= WebServiceUtil.QUARTERLY_ATTENDANCE_REPORT_SUBJECT+ name +" " +quarterAndResult;
	    		  helper.setSubject(subject);
	

	        String body = String.format(WebServiceUtil.QUARTERLY_ATTENDANCE_REPORT_BODY,
	                name,
	                quarterAndResult,
	                quarter.getTotalSchoolWorkingDays(),
	                quarter.getTotalDaysOfPresent(),
	                quarter.getTotalDaysOfAbsents(),
	                attendanceStatus,
	                name
	        );
	       helper.setText(body, true);

	        mailSender.send(message);    
	        
	        
      LocalDateTime today = LocalDateTime.now();
      
      TeacherModel teacher = teacherRepository.findTeacherByTeacherId(teacherId);
 
      // String plainTextBody = Jsoup.parse(body).text();
	        
	        EmailSentHistory email = new EmailSentHistory();        
	        email.setStudentId(student);
	        email.setStudentEmail(student.getEmail());
	        email.setEmailSubject(subject);
	        email.setEmailMessage(body);
	        email.setMailSentDate(today);   
	        email.setTeacherId(teacher);
	        emailSentHistoryRepository.save(email);
	
		}
		 logger.info("Before sendQuarterlyAttendanceReport - Successfully sended");
		 
		 response.setStatus(WebServiceUtil.SUCCESS);
		 response.setData(WebServiceUtil.MAIL_SEND);
		 
		return response;
	}
	
	 /*
	   * send mail to student parent's quarter academic progress report
	   */
	public Response sendQuarterlyMarkResult(String quarterAndResult,Integer classOfStudy,Long teacherId) throws MessagingException {
		
		logger.info("Before sendQuarterlyMarkResult - Attempting sent the quarterly mark report for TeacherId: {}", teacherId);
		Response response =new  Response();
		List<MarkModel> studentMarklist = studentMarksRepository.findMarkByQuarterAndYear(quarterAndResult,classOfStudy);
		for(MarkModel mark : studentMarklist) {
			StudentModel student = studentModelRepository.findStudentByStudentId(mark.getStudentModel().getStudentId());
			String name;
			if(student.getMiddleName()!=null) {
	        name =student.getFirstName()+" "+student.getMiddleName()+" "+student.getLastName();
			}
			else {
			 name =student.getFirstName()+" "+student.getLastName();
			}
			
//			if(mark.getResult() == WebServiceUtil.PASS){
//				resultStatus="PASS";
//			}
//			else {
//				resultStatus="FAIL";
//			}

			MimeMessage message = mailSender.createMimeMessage();

	        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
	        
	        helper.setTo(student.getParentsEmail());
		      String subject= WebServiceUtil.QUARTERLY_MARK_REPORT_SUBJECT+ name +" " +quarterAndResult;
		    		  helper.setSubject(subject);
		    		  
		         String body = String.format(WebServiceUtil.QUARTERLY_MARK_REPORT_BOBY,
		  	                name,
		  	                quarterAndResult,
		  	                mark.getTamil(),
		  	                mark.getEnglish(),
		  	                mark.getMaths(),
		  	                mark.getScience(),
		  	                mark.getSocialScience(),
		  	                mark.getTotalMarks(),
		  	                mark.getResult().getCode(),
		  	                name
		  	        );
		         helper.setText(body, true);

			      mailSender.send(message);    
			        
			        
		      LocalDateTime today = LocalDateTime.now();
		      TeacherModel teacher = teacherRepository.findTeacherByTeacherId(teacherId);
		      // String plainTextBody = Jsoup.parse(body).text();
			        
			        EmailSentHistory email = new EmailSentHistory();
			        
			        email.setStudentId(student);
			        email.setStudentEmail(student.getParentsEmail());
			        email.setEmailSubject(subject);
			        email.setEmailMessage(body);
			        email.setMailSentDate(today);
			       email.setTeacherId(teacher);
			        
			        emailSentHistoryRepository.save(email);	
	}
	 logger.info("Before sendQuarterlyMarkResult - Successfully sended");
	 
	 response.setStatus(WebServiceUtil.SUCCESS);
	 response.setData(WebServiceUtil.MAIL_SEND);
	return response;

	}
	
	/**
	 * Scheduled task that send Absent Alert message.
	 * This method is executed automatically by a Cron job 
	 * every day at 12:00 AM.
	 *
	 * Author: Vijayakumar
	 */	
//	@Scheduled(cron = "0 0 12 * * ?") // Every day at 12 AM
    public Response runAbsentAlertMessage(Long teacherId) throws MessagingException{
    	logger.info("Before runAbsentAlertMessage - Attempting sent absence alert for TeacherId: {}", teacherId);
    	Response response = new Response();
		
		LocalDate attendanceDate = LocalDate.now();
		List<DailyAttendanceModel> todayAbsentList = dailyAttendanceRepository.findAbsentStudentIds(attendanceDate,WebServiceUtil.ABSENT);
		for(DailyAttendanceModel absent : todayAbsentList) {
			

			StudentModel student = studentModelRepository.findStudentByStudentId(absent.getStudentModel().getStudentId());
	        String name = student.getMiddleName() != null
	                ? student.getFirstName() + " " + student.getMiddleName() + " " + student.getLastName()
	                : student.getFirstName() + " " + student.getLastName();
			
	        MimeMessage message = mailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
	        String subject = "";
	        String body = "";

	        if (WebServiceUtil.NO.equals(absent.getLongApprovedSickLeaveFlag()) && 
	            WebServiceUtil.NO.equals(absent.getApprovedExtraCurricularActivitiesFlag())) {

	            subject = String.format(WebServiceUtil.ABSENT_ALERT_SUBJECT, name, attendanceDate);
	            body = String.format(WebServiceUtil.ABSENT_ALERT_BODY, name, attendanceDate, student.getFirstName());

	        } else if (WebServiceUtil.YES.equals(absent.getLongApprovedSickLeaveFlag())) {
	            subject = String.format(WebServiceUtil.SICK_LEAVE_ALERT_SUBJECT, name);
	            body = String.format(WebServiceUtil.SICK_LEAVE_ALERT_BODY, name, attendanceDate);

	        } else if (WebServiceUtil.YES.equals(absent.getApprovedExtraCurricularActivitiesFlag())) {
	            subject = String.format(WebServiceUtil.EXTRA_CUR_ACTIVITY_ALERT_SUBJECT, name);
	            body = String.format(WebServiceUtil.EXTRA_CUR_ACTIVITY_ALERT_BODY,name,attendanceDate,name,attendanceDate);
	        }
			    helper.setTo(student.getParentsEmail());
		        helper.setSubject(subject);
		        helper.setText(body, true);
		        mailSender.send(message);
		        
		        LocalDateTime today = LocalDateTime.now();	
		        TeacherModel teacher = teacherRepository.findTeacherByTeacherId(teacherId);
		  	        EmailSentHistory email = new EmailSentHistory();
		  	        
		  	        email.setStudentId(student);
		  	        email.setStudentEmail(student.getParentsEmail()  );
		  	        email.setEmailSubject(subject);
		  	        email.setEmailMessage(body);
		  	        email.setMailSentDate(today);
		  	        email.setTeacherId(teacher);
		  	        emailSentHistoryRepository.save(email);
		}
		response.setStatus(WebServiceUtil.SUCCESS);
		response.setData("sended");
		logger.info("After runAbsentAlertMessage - Successfully sended");
		return response ;
		
	}
    
    /**
	 * Send Absent Alert message.
	 * This method is executed automatically by a Cron job 
	 * every day at 12:00 AM.
	 *
	 * Author: Vijayakumar
	 */	
	@Override
	public void absentAlertEmail(List<Long> absentIdList,Long teacherId) throws MessagingException {
		logger.info("Before absentAlertEmail - Attempting sent absence alert mail");
		LocalDate attendanceDate = LocalDate.now();
		for(Long studentId : absentIdList) {
			DailyAttendanceModel absent =	dailyAttendanceRepository.findAbsentStatus(attendanceDate,studentId);

			StudentModel student = studentModelRepository.findStudentByStudentId(studentId);
	        String name = student.getMiddleName() != null
	                ? student.getFirstName() + " " + student.getMiddleName() + " " + student.getLastName()
	                : student.getFirstName() + " " + student.getLastName();
			
	        MimeMessage message = mailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
	        String subject = "";
	        String body = "";

	        if (WebServiceUtil.NO.equals(absent.getLongApprovedSickLeaveFlag()) && 
	            WebServiceUtil.NO.equals(absent.getApprovedExtraCurricularActivitiesFlag())) {

	            subject = String.format(WebServiceUtil.ABSENT_ALERT_SUBJECT, name, attendanceDate);
	            body = String.format(WebServiceUtil.ABSENT_ALERT_BODY, name, attendanceDate, student.getFirstName());

	        } else if (WebServiceUtil.YES.equals(absent.getLongApprovedSickLeaveFlag())) {
	            subject = String.format(WebServiceUtil.SICK_LEAVE_ALERT_SUBJECT, name);
	            body = String.format(WebServiceUtil.SICK_LEAVE_ALERT_BODY, name, attendanceDate);

	        } else if (WebServiceUtil.YES.equals(absent.getApprovedExtraCurricularActivitiesFlag())) {
	            subject = String.format(WebServiceUtil.EXTRA_CUR_ACTIVITY_ALERT_SUBJECT, name);
	            body = String.format(WebServiceUtil.EXTRA_CUR_ACTIVITY_ALERT_BODY,name,attendanceDate,name,attendanceDate);
	        }
			    helper.setTo(student.getParentsEmail());
		        helper.setSubject(subject);
		        helper.setText(body, true);
		        mailSender.send(message);
		        
		        LocalDateTime today = LocalDateTime.now();	
		        TeacherModel teacher = teacherRepository.findTeacherByTeacherId(teacherId);
		  	        EmailSentHistory email = new EmailSentHistory();
		  	        
		  	        email.setStudentId(student);
		  	        email.setStudentEmail(student.getParentsEmail()  );
		  	        email.setEmailSubject(subject);
		  	        email.setEmailMessage(body);
		  	        email.setMailSentDate(today);
		  	        email.setTeacherId(teacher);
		  	        emailSentHistoryRepository.save(email);
		}
		
		logger.info("After absentAlertEmail - Successfully sended");
	}
	

	
}
