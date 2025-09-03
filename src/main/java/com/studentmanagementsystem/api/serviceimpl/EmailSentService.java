package com.studentmanagementsystem.api.serviceimpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.studentmanagementsystem.api.dao.QuarterlyAttendanceReportDao;
import com.studentmanagementsystem.api.dao.StudentMarksDao;
import com.studentmanagementsystem.api.model.custom.quarterlyreport.QuarterlyAttendanceReportDto;
import com.studentmanagementsystem.api.model.custom.studentmarks.StudentMarksDto;
import com.studentmanagementsystem.api.model.entity.EmailSentHistory;
import com.studentmanagementsystem.api.model.entity.QuarterlyAttendanceReportModel;
import com.studentmanagementsystem.api.model.entity.MarkModel;
import com.studentmanagementsystem.api.model.entity.StudentModel;
import com.studentmanagementsystem.api.repository.EmailSentHistoryRepository;
import com.studentmanagementsystem.api.repository.QuarterlyAttendanceModelRepository;
import com.studentmanagementsystem.api.repository.StudentMarksRepository;
import com.studentmanagementsystem.api.repository.StudentModelRepository;
import com.studentmanagementsystem.api.util.WebServiceUtil;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
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
	  private StudentMarksRepository studentMarksRepository;
	  
	  @Autowired
	  private QuarterlyAttendanceModelRepository quarterlyAttendanceModelRepository;
	  
	  
	  @Autowired
	  private QuarterlyAttendanceReportDao quarterlyAttendanceReportDao;

	public void sendEmailAlert(Long studentId, LocalDate attendanceDate, String absentAlertSubject,
			String absentAlertMessage) {
		
		StudentModel student = studentModelRepository.getStudentByStudentId(studentId);
		
		 SimpleMailMessage message = new SimpleMailMessage();
	       
	        message.setTo(student.getStudentEmail());
	        message.setSubject(absentAlertSubject);
	        message.setText(WebServiceUtil.DEAR+student.getStudentFirstName()+"\n"+absentAlertMessage + attendanceDate);

	        mailSender.send(message);
	        
	        
	        LocalDateTime today = LocalDateTime.now();
	        
	        EmailSentHistory email = new EmailSentHistory();
	        
	        email.setStudentId(studentId);
	        email.setStudentEmail(student.getStudentEmail());
	        email.setEmailSubject(absentAlertSubject);
	        email.setEmailMessage(WebServiceUtil.DEAR+student.getStudentFirstName()+"\n"+absentAlertMessage + attendanceDate);
	        email.setMailSentDate(today);
	        
	        emailSentHistoryRepository.save(email);
	        
		
	}

	public Object sendQuarterlyResultReport(String quarterAndResult) throws MessagingException {
//		List<StudentMarks> studentMark = studentMarksRepository.findByQuarterAndYear(quarterAndResult);
		
		List<StudentMarksDto> studentMarklist = studentMarksDao.getAllStudentMarks(quarterAndResult);
		
//		List<QuarterlyAttendanceReportModel> quarterlyAttendanceReport = quarterlyAttendanceModelRepository.findByQuarterAndYear(quarterAndResult);
		
		List<QuarterlyAttendanceReportDto> quarterList = quarterlyAttendanceReportDao.getQuarterlyAttendanceReport(quarterAndResult);
		
		
		for(StudentMarksDto mark : studentMarklist) {
			QuarterlyAttendanceReportDto report = quarterList.stream()
		                .filter(r -> r.getStudentId().equals(mark.getStudentId()))
		                .findFirst()
		                .orElse(null);
				
				if (report == null) continue;
			
			StudentModel student = studentModelRepository.findStudentEmailByStudentId(mark.getStudentId());
			
	        MimeMessage message = mailSender.createMimeMessage();

	        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

	        helper.setTo(student.getStudentEmail());
	        helper.setSubject("Attendance report and " +WebServiceUtil.QUARTERLY_RESULT_REPORT_SUBJECT);

	        // Build HTML table
	        StringBuilder htmlContent = new StringBuilder();
	        htmlContent.append("<h3>").append(WebServiceUtil.QUARTERLY_RESULT_REPORT_SUBJECT + mark.getQuarterAndYear()).append("</h3>");
	        htmlContent.append("<table border='1' cellspacing='0' cellpadding='5'>");
	        
	        htmlContent.append("<table border='1' cellspacing='0' cellpadding='5'>");
	        htmlContent.append("<tr><th>Describtion</th><th>Count</th></tr>");


	        htmlContent.append("<tr>");
	        htmlContent.append("<td>").append("Total working days").append("</td>");
	        htmlContent.append("<td>").append(report.getTotalSchoolWorkingDays()).append("</td>");
	        htmlContent.append("</tr>");
	            
	        htmlContent.append("<tr>");
	        htmlContent.append("<td>").append("Total present").append("</td>");
	        htmlContent.append("<td>").append(report.getTotalDaysOfPresent()).append("</td>");
	        htmlContent.append("</tr>");
	            
	        htmlContent.append("<tr>");
	        htmlContent.append("<td>").append("Total absent").append("</td>");
	        htmlContent.append("<td>").append(report.getTotalDaysOfAbsents()).append("</td>");
	        htmlContent.append("</tr>");
	            
	        htmlContent.append("<tr>");
	        htmlContent.append("<td>").append("Total approved sick leave").append("</td>");
	        htmlContent.append("<td>").append(report.getTotalApprovedSickdays()).append("</td>");
	        htmlContent.append("</tr>");
	            
	        htmlContent.append("<tr>");
	        htmlContent.append("<td>").append("Total approved extra activitives leave").append("</td>");
	        htmlContent.append("<td>").append(report.getTotalApprovedActivitiesPermissionDays()).append("</td>");
	        htmlContent.append("</tr>");
	            
	        htmlContent.append("<tr>");
	        htmlContent.append("<td>").append("Attendance Status").append("</td>");
	        htmlContent.append("<td>").append(report.getAttendanceComplianceStatus()).append("</td>");
	        htmlContent.append("</tr>");
	            
	            

	        htmlContent.append("</table>");
	        
	        
	        htmlContent.append("<tr><th>Subject</th><th>Mark</th></tr>");

	    
	            htmlContent.append("<tr>");
	            htmlContent.append("<td>").append("Tamil").append("</td>");
	            htmlContent.append("<td>").append(mark.getTamil()).append("</td>");
	            htmlContent.append("</tr>");
	            
	            htmlContent.append("<tr>");
	            htmlContent.append("<td>").append("English").append("</td>");
	            htmlContent.append("<td>").append(mark.getEnglish()).append("</td>");
	            htmlContent.append("</tr>");
	            
	            htmlContent.append("<tr>");
	            htmlContent.append("<td>").append("Maths").append("</td>");
	            htmlContent.append("<td>").append(mark.getMaths()).append("</td>");
	            htmlContent.append("</tr>");
	            
	            htmlContent.append("<tr>");
	            htmlContent.append("<td>").append("Science").append("</td>");
	            htmlContent.append("<td>").append(mark.getScience()).append("</td>");
	            htmlContent.append("</tr>");
	            
	            htmlContent.append("<tr>");
	            htmlContent.append("<td>").append("SocialScience").append("</td>");
	            htmlContent.append("<td>").append(mark.getSocialScience()).append("</td>");
	            htmlContent.append("</tr>");
	            
	            htmlContent.append("<tr>");
	            htmlContent.append("<td>").append("Total").append("</td>");
	            htmlContent.append("<td>").append(mark.getTotalMarks()).append("</td>");
	            htmlContent.append("</tr>");
	            
	            htmlContent.append("<tr>");
	            htmlContent.append("<td>").append("Result").append("</td>");
	            htmlContent.append("<td>").append(mark.getResult()).append("</td>");
	            htmlContent.append("</tr>");
	     

	        htmlContent.append("</table>");
	        

	        helper.setText(htmlContent.toString(), true); // true → enables HTML

	        mailSender.send(message);
			}
			
		
		return "Sended...";
	}

}





