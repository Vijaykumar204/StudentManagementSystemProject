package com.studentmanagementsystem.api.serviceimpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.studentmanagementsystem.api.dao.QuarterlyAttendanceReportDao;
import com.studentmanagementsystem.api.dao.StudentMarksDao;
import com.studentmanagementsystem.api.model.custom.Response;
import com.studentmanagementsystem.api.model.custom.quarterlyreport.QuarterlyAttendanceReportDto;
import com.studentmanagementsystem.api.model.custom.studentmarks.StudentMarksDto;
import com.studentmanagementsystem.api.model.entity.DailyAttendanceModel;
import com.studentmanagementsystem.api.model.entity.EmailSentHistory;
import com.studentmanagementsystem.api.model.entity.StudentModel;
import com.studentmanagementsystem.api.repository.DailyAttendanceRepository;
import com.studentmanagementsystem.api.repository.EmailSentHistoryRepository;
import com.studentmanagementsystem.api.repository.StudentModelRepository;
import com.studentmanagementsystem.api.util.WebServiceUtil;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class EmailSentService {
	
	  @Autowired
	    private JavaMailSender mailSender;
	  
	  @Autowired
	  private StudentModelRepository studentModelRepository;
	  
	  @Autowired
	  private EmailSentHistoryRepository emailSentHistoryRepository;
	  
	  @Autowired
	  private StudentMarksDao studentMarksDao;
	  
	  @Autowired
	  private DailyAttendanceRepository dailyAttendanceRepository;
	  
	 
	  @Autowired
	  private QuarterlyAttendanceReportDao quarterlyAttendanceReportDao;
//
//	public void sendEmailAlert(Long studentId, LocalDate attendanceDate, String absentAlertSubject,
//			String absentAlertMessage) {
//		
//		StudentModel student = studentModelRepository.findStudentByStudentId(studentId);
//		
//		 SimpleMailMessage message = new SimpleMailMessage();
//	       
//	        message.setTo(student.getStudentEmail());
//	        message.setSubject(absentAlertSubject);
//	        message.setText(WebServiceUtil.DEAR+student.getStudentFirstName()+"\n"+absentAlertMessage + attendanceDate);
//
//	        mailSender.send(message);
//	        
//	        
//	        LocalDateTime today = LocalDateTime.now();
//	        
//	        EmailSentHistory email = new EmailSentHistory();
//	        
//	        email.setStudentId(studentId);
//	        email.setStudentEmail(student.getStudentEmail());
//	        email.setEmailSubject(absentAlertSubject);
//	        email.setEmailMessage(WebServiceUtil.DEAR+student.getStudentFirstName()+"\n"+absentAlertMessage + attendanceDate);
//	        email.setMailSentDate(today);
//	        
//	        emailSentHistoryRepository.save(email);
//	        
//		
//	}
	

	 /*
	   * send mail to student parent's quarter academic attendance report
	   */
  public Response sendQuarterlyResultReport(String quarterAndResult) {
		
		try {
			sendQuarterlyAttendanceRport(quarterAndResult);
			sendQuarterlyMarkResult(quarterAndResult);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return null;
		
	}
	
  /*
   * send mail to student parent's quarter academic attendance report
   */	
	public void sendQuarterlyAttendanceRport(String quarterAndResult) throws MessagingException {
		
//		List<StudentMarksDto> studentMarklist = studentMarksDao.getAllStudentMarks(quarterAndResult);
		

		
		 List<QuarterlyAttendanceReportDto> quarterAttendanceList = quarterlyAttendanceReportDao.getQuarterlyAttendanceReport(quarterAndResult);
		
		
		 for(QuarterlyAttendanceReportDto quarter : quarterAttendanceList) {
				
			StudentModel student = studentModelRepository.findStudentFirstNameAndStudentMiddleNameAndStudentLastNameAndStudentEmailByStudentId(quarter.getStudentId());
			String name;
			String attendanceStatus;
			if(student.getStudentMiddleName()!=null) {
	        name =student.getStudentFirstName()+" "+student.getStudentMiddleName()+" "+student.getStudentLastName();
			}
			else {
			 name =student.getStudentFirstName()+" "+student.getStudentLastName();
			}
			
			if(quarter.getAttendanceComplianceStatus() == WebServiceUtil.COMPLIANCE){
				attendanceStatus="Compliance";
			}
			else {
				attendanceStatus="Non - compliance";
			}

			MimeMessage message = mailSender.createMimeMessage();

	        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

	        helper.setTo(student.getStudentEmail());
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
 
      // String plainTextBody = Jsoup.parse(body).text();
	        
	        EmailSentHistory email = new EmailSentHistory();
	        
	        email.setStudentId(quarter.getStudentId());
	        email.setStudentEmail(student.getStudentEmail());
	        email.setEmailSubject(subject);
	        email.setEmailMessage(body);
	        email.setMailSentDate(today);
	        
	        emailSentHistoryRepository.save(email);
	
		}

	
		

	}
	
	 /*
	   * send mail to student parent's quarter academic progress report
	   */
	public void sendQuarterlyMarkResult(String quarterAndResult) throws MessagingException {
		
	List<StudentMarksDto> studentMarklist = studentMarksDao.getAllStudentMarks(quarterAndResult);
	
	 for(StudentMarksDto mark : studentMarklist) {
			
			StudentModel student = studentModelRepository.findStudentFirstNameAndStudentMiddleNameAndStudentLastNameAndStudentEmailByStudentId(mark.getStudentId());
			String name;
			String resultStatus;
			if(student.getStudentMiddleName()!=null) {
	        name =student.getStudentFirstName()+" "+student.getStudentMiddleName()+" "+student.getStudentLastName();
			}
			else {
			 name =student.getStudentFirstName()+" "+student.getStudentLastName();
			}
			
			if(mark.getResult() == WebServiceUtil.PASS){
				resultStatus="PASS";
			}
			else {
				resultStatus="FAIL";
			}

			MimeMessage message = mailSender.createMimeMessage();

	        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
	        
	        helper.setTo(student.getStudentEmail());
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
		  	                resultStatus,
		  	                name
		  	        );
		         helper.setText(body, true);

			      mailSender.send(message);    
			        
			        
		      LocalDateTime today = LocalDateTime.now();
		 
		      // String plainTextBody = Jsoup.parse(body).text();
			        
			        EmailSentHistory email = new EmailSentHistory();
			        
			        email.setStudentId(mark.getStudentId());
			        email.setStudentEmail(student.getStudentEmail());
			        email.setEmailSubject(subject);
			        email.setEmailMessage(body);
			        email.setMailSentDate(today);
			        
			        emailSentHistoryRepository.save(email);
		    		  
		
	}

	}
	/**
	 * Scheduled task that send Absent Alert message.
	 * This method is executed automatically by a Cron job 
	 * every day at 12:00 AM.
	 *
	 * Author: Vijayakumar
	 */
	
//	@Scheduled(cron = "0 0 12 * * ?") // Every day at 12 AM
    public Response runAbsentAlertMessage() throws MessagingException{
    	Response response = new Response();
		
		LocalDate attendanceDate = LocalDate.now();
		List<DailyAttendanceModel> todayAbsentList = dailyAttendanceRepository.findAbsentStudentIds(attendanceDate,WebServiceUtil.ABSENT);
		for(DailyAttendanceModel absent : todayAbsentList) {
			

			StudentModel studentModel = studentModelRepository.findStudentFirstNameAndStudentMiddleNameAndStudentLastNameAndStudentEmailByStudentId(absent.getStudentModel().getStudentId());
	        String name = studentModel.getStudentMiddleName() != null
	                ? studentModel.getStudentFirstName() + " " + studentModel.getStudentMiddleName() + " " + studentModel.getStudentLastName()
	                : studentModel.getStudentFirstName() + " " + studentModel.getStudentLastName();
			
	        MimeMessage message = mailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
	        String subject = "";
	        String body = "";

	        if (WebServiceUtil.NO.equals(absent.getLongApprovedSickLeaveFlag()) && 
	            WebServiceUtil.NO.equals(absent.getApprovedExtraCurricularActivitiesFlag())) {

	            subject = String.format(WebServiceUtil.ABSENT_ALERT_SUBJECT, name, attendanceDate);
	            body = String.format(WebServiceUtil.ABSENT_ALERT_BODY, name, attendanceDate, studentModel.getStudentFirstName());

	        } else if (WebServiceUtil.YES.equals(absent.getLongApprovedSickLeaveFlag())) {
	            subject = String.format(WebServiceUtil.SICK_LEAVE_ALERT_SUBJECT, name);
	            body = String.format(WebServiceUtil.SICK_LEAVE_ALERT_BODY, name, attendanceDate);

	        } else if (WebServiceUtil.YES.equals(absent.getApprovedExtraCurricularActivitiesFlag())) {
	            subject = String.format(WebServiceUtil.EXTRA_CUR_ACTIVITY_ALERT_SUBJECT, name);
	            body = String.format(WebServiceUtil.EXTRA_CUR_ACTIVITY_ALERT_BODY,name,attendanceDate,name,attendanceDate);
	        }
			    helper.setTo(studentModel.getStudentEmail());
		        helper.setSubject(subject);
		        helper.setText(body, true);
		        mailSender.send(message);
		        
		        LocalDateTime today = LocalDateTime.now();		  	        
		  	        EmailSentHistory email = new EmailSentHistory();
		  	        
		  	        email.setStudentId(studentModel.getStudentId());
		  	        email.setStudentEmail(studentModel.getStudentEmail());
		  	        email.setEmailSubject(subject);
		  	        email.setEmailMessage(body);
		  	        email.setMailSentDate(today);
		  	        
		  	        emailSentHistoryRepository.save(email);
		}
		response.setStatus(WebServiceUtil.SUCCESS);
		response.setData("sended");
		return response ;
	}
	
//	
//	 /*
//	   * send mail to student parent's approved Extra-Curricular Activities Alert message
//	   */
//	
//	private void approvedExtraCurricularActivitiesAlert(Long studentId) throws MessagingException {
//		LocalDate attendanceDate = LocalDate.now();
//		StudentModel studentModel = studentModelRepository.findStudentFirstNameAndStudentMiddleNameAndStudentLastNameAndStudentEmailByStudentId(studentId);
//		String name;
//
//		if(studentModel.getStudentMiddleName()!=null) {
//        name =studentModel.getStudentFirstName()+" "+studentModel.getStudentMiddleName()+" "+studentModel.getStudentLastName();
//		}
//		else {
//		 name =studentModel.getStudentFirstName()+" "+studentModel.getStudentLastName();
//		}
//		
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
//	       
//        String subject = String.format(WebServiceUtil.EXTRA_CUR_ACTIVITY_ALERT_BODY,name);
//        helper.setTo(studentModel.getStudentEmail());
//        helper.setSubject(subject);
//        String body = String.format(WebServiceUtil.EXTRA_CUR_ACTIVITY_ALERT_BODY,name,attendanceDate,name,attendanceDate);
//        helper.setText(body, true);
//        mailSender.send(message);
//        
//        LocalDateTime today = LocalDateTime.now();
//        
//        // String plainTextBody = Jsoup.parse(body).text();
//  	        
//  	        EmailSentHistory email = new EmailSentHistory();	        
//  	        email.setStudentId(studentModel.getStudentId());
//  	        email.setStudentEmail(studentModel.getStudentEmail());
//  	        email.setEmailSubject(subject);
//  	        email.setEmailMessage(body);
//  	        email.setMailSentDate(today);
//  	        
//  	        emailSentHistoryRepository.save(email);
//		
//	}
//
//	 /*
//	   * send mail to student parent's approved sick leave alertmessage
//	   */
//	public void approvedSickLeaveAlertMessage(Long studentId) throws MessagingException {
//		
//		LocalDate attendanceDate = LocalDate.now();
//		StudentModel studentModel = studentModelRepository.findStudentFirstNameAndStudentMiddleNameAndStudentLastNameAndStudentEmailByStudentId(studentId);
//		String name;
//
//		if(studentModel.getStudentMiddleName()!=null) {
//        name =studentModel.getStudentFirstName()+" "+studentModel.getStudentMiddleName()+" "+studentModel.getStudentLastName();
//		}
//		else {
//		 name =studentModel.getStudentFirstName()+" "+studentModel.getStudentLastName();
//		}
//		
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
//	       
//        String subject = String.format(WebServiceUtil.SICK_LEAVE_ALERT_SUBJECT,name);
//        helper.setTo(studentModel.getStudentEmail());
//        helper.setSubject(subject);
//        String body = String.format(WebServiceUtil.SICK_LEAVE_ALERT_BODY,name,attendanceDate);
//        helper.setText(body, true);
//        mailSender.send(message);
//        
//        LocalDateTime today = LocalDateTime.now();
//        
//        // String plainTextBody = Jsoup.parse(body).text();
//  	        
//  	        EmailSentHistory email = new EmailSentHistory();	        
//  	        email.setStudentId(studentModel.getStudentId());
//  	        email.setStudentEmail(studentModel.getStudentEmail());
//  	        email.setEmailSubject(subject);
//  	        email.setEmailMessage(body);
//  	        email.setMailSentDate(today);
//  	        
//  	        emailSentHistoryRepository.save(email);
//  	
//	}
//			
//
//
//	 /*
//	   * send mail to student parent's absent alert message
//	   */
//
//	public void absentAlertMessage(Long studentId) throws MessagingException {
//		
//		LocalDate attendanceDate = LocalDate.now();
//		StudentModel studentModel = studentModelRepository.findStudentFirstNameAndStudentMiddleNameAndStudentLastNameAndStudentEmailByStudentId(studentId);
//		String name;
//
//		if(studentModel.getStudentMiddleName()!=null) {
//        name =studentModel.getStudentFirstName()+" "+studentModel.getStudentMiddleName()+" "+studentModel.getStudentLastName();
//		}
//		else {
//		 name =studentModel.getStudentFirstName()+" "+studentModel.getStudentLastName();
//		}
//		
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
//	       
//        String subject = String.format(WebServiceUtil.ABSENT_ALERT_SUBJECT,name,attendanceDate);
//        helper.setTo(studentModel.getStudentEmail());
//        helper.setSubject(subject);
//        String body = String.format(WebServiceUtil.ABSENT_ALERT_BODY,name,attendanceDate,studentModel.getStudentFirstName());
//        helper.setText(body, true);
//        mailSender.send(message);
//        
//        LocalDateTime today = LocalDateTime.now();
//        
//        // String plainTextBody = Jsoup.parse(body).text();
//  	        
//  	        EmailSentHistory email = new EmailSentHistory();
//  	        
//  	        email.setStudentId(studentModel.getStudentId());
//  	        email.setStudentEmail(studentModel.getStudentEmail());
//  	        email.setEmailSubject(subject);
//  	        email.setEmailMessage(body);
//  	        email.setMailSentDate(today);
//  	        
//  	        emailSentHistoryRepository.save(email);
//  	
//  		}
	
}
