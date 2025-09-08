package com.studentmanagementsystem.api.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
	
	@Column(name = "STU_First_Name",nullable=false,length =25)
	private String studentFirstName;
	
	@Column(name = "STU_Middle_Name",length=25)
	private String studentMiddleName;
	
	@Column(name ="STU_Last_Name",nullable = false,length=5)
	private String studentLastName;
	
	@OneToOne
	@JoinColumn(name ="STU_Gender",referencedColumnName = "CODE" )
	private StudentCodeModel studentGender;
	
	@Column(name ="STU_DOB",nullable = false)
//	@DateTimeFormat(pattern = "dd-MM-yyyy")   // for input (e.g., from HTML form / RequestParam)
//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate studentDateOfBirth;
	
	@Column(name = "STU_Class",nullable = false)
	private Integer studentClassOfStudy;
	
	
//	@Column(name = "STU_Residing_Status",nullable = false)
	@OneToOne
	@JoinColumn(name ="STU_Residing_Status",referencedColumnName = "CODE" )
	private StudentCodeModel studentResidingStatus;
	
	@Column(name = "STU_Email",unique = true,length =25,nullable = false)
	private String studentEmail;
	
	@Column(name = "STU_Ph_No", unique = true, nullable = false,length=10)
	private String studentPhoneNumber;
	
	@Column(name = "STU_Contact_Person_Name",length=25,nullable = false)
	private String  emergencyContactPersonName;
	
	@Column(name = "STU_Contact_Ph_No",length =10)
	private String emergencyContactPhoneNumber;
	
	@Column(name = "STU_Contact_Email",length =25,nullable = false)
	private String contactEmail;
	
	@Column(name = "STU_Street_Name",length =25)
	private String homeStreetName;
	
	@Column(name = "STU_City_Name",length = 25,nullable = false)
	private String homeCityName;
	
	@Column(name = "STU_Postal_Code",nullable = false,length=6)
	private String homePostalCode;
	
//	@Column(name = "STU_Status", nullable = false,columnDefinition ="CHAR DEFAULT 'A'")
	@OneToOne
	@JoinColumn(name ="STU_Status",referencedColumnName = "CODE" )
	private StudentCodeModel studentActiveStatus;
	
	@Column(name = "STU_Last_Effective_Date")
	private LocalDateTime lasteffectivedate;
	
	@Column(name = "STU_Create_Date_Time",nullable = false)
	private LocalDateTime studentCreateDate;
	
	@Column(name = "STU_Update_Teacher")
	private Long updateTeacher;
	
	@Column(name = "STU_Update_Date_Time")
	private LocalDateTime updateDate;
	

	
	@ManyToOne
	@JoinColumn(name ="STU_Create_Teacher" ,referencedColumnName = "TEACH_Id",nullable = false)
	private TeacherModel teacherModel;
	
	
	@OneToMany(mappedBy = "studentModel", fetch = FetchType.LAZY)
	@JsonIgnore
//	@JsonManagedReference
	List<DailyAttendanceModel> dailyAttendanceModel;
	
	@OneToMany(mappedBy = "studentModel", fetch = FetchType.LAZY)
	@JsonIgnore
    private List<MarkModel> marks;

    @OneToMany(mappedBy = "studentModel", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<QuarterlyAttendanceReportModel> quarterlyAttendanceReportModel;

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public String getFirstName() {
		return getStudentFirstName();
	}

	public String getStudentFirstName() {
		return studentFirstName;
	}


	public void setFirstName(String studentFirstName) {
		setStudentFirstName(studentFirstName);
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



	public StudentCodeModel getStudentGender() {
		return studentGender;
	}

	public void setStudentGender(StudentCodeModel studentGender) {
		this.studentGender = studentGender;
	}

	public LocalDate getStudentDateOfBirth() {
		return studentDateOfBirth;
	}

	public void setStudentDateOfBirth(LocalDate studentDateOfBirth) {
		this.studentDateOfBirth = studentDateOfBirth;
	}

	public Integer getStudentClassOfStudy() {
		return studentClassOfStudy;
	}

	public void setStudentClassOfStudy(Integer studentClassOfStudy) {
		this.studentClassOfStudy = studentClassOfStudy;
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

	public String getEmergencyContactPhoneNumber() {
		return emergencyContactPhoneNumber;
	}

	public void setEmergencyContactPhoneNumber(String emergencyContactPhoneNumber) {
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

	public String getHomePostalCode() {
		return homePostalCode;
	}

	public void setHomePostalCode(String homePostalCode) {
		this.homePostalCode = homePostalCode;
	}



	public LocalDateTime getLasteffectivedate() {
		return lasteffectivedate;
	}

	public void setLasteffectivedate(LocalDateTime lasteffectivedate) {
		this.lasteffectivedate = lasteffectivedate;
	}

	public LocalDateTime getStudentCreateDate() {
		return studentCreateDate;
	}

	public void setStudentCreateDate(LocalDateTime studentCreateDate) {
		this.studentCreateDate = studentCreateDate;
	}

	public Long getUpdateTeacher() {
		return updateTeacher;
	}

	public void setUpdateTeacher(Long updateTeacher) {
		this.updateTeacher = updateTeacher;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDateTime updateDate) {
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

	public List<MarkModel> getMarks() {
		return marks;
	}

	public void setMarks(List<MarkModel> marks) {
		this.marks = marks;
	}

	public List<QuarterlyAttendanceReportModel> getQuarterlyAttendanceReportModel() {
		return quarterlyAttendanceReportModel;
	}

	public void setQuarterlyAttendanceReportModel(List<QuarterlyAttendanceReportModel> quarterlyAttendanceReportModel) {
		this.quarterlyAttendanceReportModel = quarterlyAttendanceReportModel;
	}

	public StudentCodeModel getStudentResidingStatus() {
		return studentResidingStatus;
	}

	public void setStudentResidingStatus(StudentCodeModel studentResidingStatus) {
		this.studentResidingStatus = studentResidingStatus;
	}

	public StudentCodeModel getStudentActiveStatus() {
		return studentActiveStatus;
	}

	public void setStudentActiveStatus(StudentCodeModel studentActiveStatus) {
		this.studentActiveStatus = studentActiveStatus;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}


	
	
	

	
	
}