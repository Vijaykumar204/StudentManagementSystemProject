package com.studentmanagementsystem.api.model.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "student")
public class StudentModel {
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "STU_Id")
	private Long studentId;
	
	@Column(name = "STU_FirstName",nullable=false)
	private String studentFirstName;
	
	@Column(name = "STU_MiddleName")
	private String studentMiddleName;
	
	@Column(name ="STU_LastName")
	private String studentLastName;
	
	@Column(name = "STU_Gender")
	private String studentGender;
	
	@Column(name ="STU_DOB" )
//	@DateTimeFormat(pattern = "dd-MM-yyyy")   // for input (e.g., from HTML form / RequestParam)
//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate studentDateOfBirth;
	
	@Column(name = "STU_Class")
	private String studentClassOfStudy;
	
	@Column(name = "STU_ResidingStatus")
	private char studentResidingStatus;
	
	@Column(name = "STU_Email",unique = true)
	private String studentEmail;
	
	@Column(name = "STU_Ph_No", unique = true, nullable = false)
	private String studentPhoneNumber;
	
	@Column(name = "STU_ContactPersonName")
	private String  emergencyContactPersonName;
	
	@Column(name = "STU_ContactPh_No", unique = true, nullable = false)
	private Long emergencyContactPhoneNumber;
	
	@Column(name = "STU_StreetName")
	private String homeStreetName;
	
	@Column(name = "STU_CityName")
	private String homeCityName;
	
	@Column(name = "STU_PostalCode")
	private Long homePostalCode;
	
	@Column(name = "STU_Status", nullable = false)
	private char studentActiveStatus = 'A';
	
	@Column(name = "STU_LastEffectiveate")
	private LocalDate lasteffectivedate;
	
	@Column(name = "STU_CreateDate")
	private LocalDate studentCreateDate;
	
	@Column(name = "STU_UpdateTeacher")
	private Long updateTeacher;
	
	@Column(name = "STU_UpdateDate")
	private LocalDate updateDate;
	

	
	@ManyToOne
	@JoinColumn(name ="STU_CreateTeacher" ,referencedColumnName = "TEACH_Id")
	private TeacherModel teacherModel;
	
	
	@OneToMany(mappedBy = "studentModel")
	@JsonManagedReference
	List<DailyAttendanceModel> dailyAttendanceModel;
	
	@OneToOne	 
	private  QuarterlyAttendanceReportModel quarterlyAttendanceReportModel;
	



	public Long getStudentId() {
		return studentId;
	}



	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}



	public String getStudentFirstName() {
		return studentFirstName;
	}



	public void setStudentFirstName(String studentFirstName) {
		this.studentFirstName = studentFirstName;
	}



	public String getStudentMiddleName() {
		return studentMiddleName;
	}



	public void setStudentMiddleName(String studentMiddleName) {
		this.studentMiddleName = studentMiddleName;
	}



	public String getStudentLastName() {
		return studentLastName;
	}



	public void setStudentLastName(String studentLastName) {
		this.studentLastName = studentLastName;
	}



	public String getStudentGender() {
		return studentGender;
	}



	public void setStudentGender(String studentGender) {
		this.studentGender = studentGender;
	}



	public LocalDate getStudentDateOfBirth() {
		return studentDateOfBirth;
	}



	public void setStudentDateOfBirth(LocalDate studentDateOfBirth) {
		this.studentDateOfBirth = studentDateOfBirth;
	}



	public String getStudentClassOfStudy() {
		return studentClassOfStudy;
	}



	public void setStudentClassOfStudy(String studentClassOfStudy) {
		this.studentClassOfStudy = studentClassOfStudy;
	}



	public char getStudentResidingStatus() {
		return studentResidingStatus;
	}



	public void setStudentResidingStatus(char studentResidingStatus) {
		this.studentResidingStatus = studentResidingStatus;
	}



	public String getStudentEmail() {
		return studentEmail;
	}



	public void setStudentEmail(String studentEmail) {
		this.studentEmail = studentEmail;
	}



	public String getStudentPhoneNumber() {
		return studentPhoneNumber;
	}



	public void setStudentPhoneNumber(String studentPhoneNumber) {
		this.studentPhoneNumber = studentPhoneNumber;
	}



	public String getEmergencyContactPersonName() {
		return emergencyContactPersonName;
	}



	public void setEmergencyContactPersonName(String emergencyContactPersonName) {
		this.emergencyContactPersonName = emergencyContactPersonName;
	}



	public Long getEmergencyContactPhoneNumber() {
		return emergencyContactPhoneNumber;
	}



	public void setEmergencyContactPhoneNumber(Long emergencyContactPhoneNumber) {
		this.emergencyContactPhoneNumber = emergencyContactPhoneNumber;
	}



	public String getHomeStreetName() {
		return homeStreetName;
	}



	public void setHomeStreetName(String homeStreetName) {
		this.homeStreetName = homeStreetName;
	}



	public String getHomeCityName() {
		return homeCityName;
	}



	public void setHomeCityName(String homeCityName) {
		this.homeCityName = homeCityName;
	}



	public Long getHomePostalCode() {
		return homePostalCode;
	}



	public void setHomePostalCode(Long homePostalCode) {
		this.homePostalCode = homePostalCode;
	}



	public char getStudentActiveStatus() {
		return studentActiveStatus;
	}



	public void setStudentActiveStatus(char studentActiveStatus) {
		this.studentActiveStatus = studentActiveStatus;
	}



	public LocalDate getLasteffectivedate() {
		return lasteffectivedate;
	}



	public void setLasteffectivedate(LocalDate lasteffectivedate) {
		this.lasteffectivedate = lasteffectivedate;
	}



	public LocalDate getStudentCreateDate() {
		return studentCreateDate;
	}



	public void setStudentCreateDate(LocalDate studentCreateDate) {
		this.studentCreateDate = studentCreateDate;
	}



	public Long getUpdateTeacher() {
		return updateTeacher;
	}



	public void setUpdateTeacher(Long updateTeacher) {
		this.updateTeacher = updateTeacher;
	}



	public LocalDate getUpdateDate() {
		return updateDate;
	}



	public void setUpdateDate(LocalDate updateDate) {
		this.updateDate = updateDate;
	}



	public TeacherModel getTeacherModel() {
		return teacherModel;
	}



	public void setTeacherModel(TeacherModel teacherModel) {
		this.teacherModel = teacherModel;
	}



	public List<DailyAttendanceModel> getDailyAttendanceModel() {
		return dailyAttendanceModel;
	}



	public void setDailyAttendanceModel(List<DailyAttendanceModel> dailyAttendanceModel) {
		this.dailyAttendanceModel = dailyAttendanceModel;
	}



	public QuarterlyAttendanceReportModel getQuarterlyAttendanceReportModel() {
		return quarterlyAttendanceReportModel;
	}



	public void setQuarterlyAttendanceReportModel(QuarterlyAttendanceReportModel quarterlyAttendanceReportModel) {
		this.quarterlyAttendanceReportModel = quarterlyAttendanceReportModel;
	}
	
	
	
	//Getter and Setter
	
}