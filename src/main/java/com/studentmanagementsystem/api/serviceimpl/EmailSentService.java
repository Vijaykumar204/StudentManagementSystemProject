package com.studentmanagementsystem.api.serviceimpl;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.studentmanagementsystem.api.model.entity.EmailSentHistory;
import com.studentmanagementsystem.api.model.entity.StudentModel;
import com.studentmanagementsystem.api.repository.EmailSentHistoryRepository;
import com.studentmanagementsystem.api.repository.StudentModelRepository;
import com.studentmanagementsystem.api.util.WebServiceUtil;

@Service
public class EmailSentService {
	
	  @Autowired
	    private JavaMailSender mailSender;
	  
	  @Autowired
	  private StudentModelRepository studentModelRepository;
	  
	  @Autowired
	  private EmailSentHistoryRepository emailSentHistoryRepository;

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

}
