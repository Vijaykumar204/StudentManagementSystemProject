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
	private String firstName;
	
	@Column(name = "STU_Middle_Name",length=25)
	private String middleName;
	
	@Column(name ="STU_Last_Name",nullable = false,length=5)
	private String lastName;
	
	@Column(name ="STU_DOB",nullable = false)
	private LocalDate dateOfBirth;
	
	@Column(name = "STU_Class",nullable = false)
	private Integer classOfStudy;
	
	@OneToOne
	@JoinColumn(name ="STU_Gender",referencedColumnName = "CODE",nullable = false )
	private StudentCodeModel gender;

	@OneToOne
	@JoinColumn(name ="STU_Residing_Status",referencedColumnName = "CODE",nullable = false )
	private StudentCodeModel residingStatus;
	
	@Column(name = "STU_Ph_No", unique = true, nullable = false,length=10)
	private String phoneNumber;
	
	@Column(name = "STU_Email",unique = true,length =25,nullable = false)
	private String email;
	
	@Column(name = "STU_Parents_Name",length=25,nullable = false)
	private String  parentsName;
	
	@Column(name = "STU_Parents_Email",length =25,nullable = false)
	private String parentsEmail;
	
	@Column(name = "STU_Street_Name",length =25)
	private String homeStreetName;
	
	@Column(name = "STU_City_Name",length = 25,nullable = false)
	private String homeCityName;
	
	@Column(name = "STU_Postal_Code",nullable = false,length=6)
	private String homePostalCode;

	@OneToOne
	@JoinColumn(name ="STU_Status",referencedColumnName = "CODE" ,columnDefinition ="CHAR DEFAULT 'A'")
	private StudentCodeModel status;
	
	@Column(name = "STU_Last_Effective_Date")
	private LocalDateTime lasteffectivedate;
	
	@Column(name = "STU_Create_Date_Time",nullable = false)
	private LocalDateTime createDate;
	
	@Column(name = "STU_Update_Teacher")
	private Long updateTeacher;
	
	@Column(name = "STU_Update_Date_Time")
	private LocalDateTime updateDate;
	
	@ManyToOne
	@JoinColumn(name ="STU_Create_Teacher" ,referencedColumnName = "TEACH_Id",nullable = false)
	private TeacherModel teacherModel;
	
	
	@OneToMany(mappedBy = "studentModel", fetch = FetchType.LAZY)
	@JsonIgnore
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
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public StudentCodeModel getGender() {
		return gender;
	}

	public void setGender(StudentCodeModel gender) {
		this.gender = gender;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Integer getClassOfStudy() {
		return classOfStudy;
	}

	public void setClassOfStudy(Integer classOfStudy) {
		this.classOfStudy = classOfStudy;
	}

	public StudentCodeModel getResidingStatus() {
		return residingStatus;
	}

	public void setResidingStatus(StudentCodeModel residingStatus) {
		this.residingStatus = residingStatus;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getParentsName() {
		return parentsName;
	}

	public void setParentsName(String parentsName) {
		this.parentsName = parentsName;
	}

//	public String getEmergencyContactPhoneNumber() {
//		return emergencyContactPhoneNumber;
//	}
//
//	public void setEmergencyContactPhoneNumber(String emergencyContactPhoneNumber) {
//		this.emergencyContactPhoneNumber = emergencyContactPhoneNumber;
//	}

	public String getParentsEmail() {
		return parentsEmail;
	}

	public void setParentsEmail(String parentsEmail) {
		this.parentsEmail = parentsEmail;
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

	public StudentCodeModel getStatus() {
		return status;
	}

	public void setStatus(StudentCodeModel status) {
		this.status = status;
	}

	public LocalDateTime getLasteffectivedate() {
		return lasteffectivedate;
	}

	public void setLasteffectivedate(LocalDateTime lasteffectivedate) {
		this.lasteffectivedate = lasteffectivedate;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
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

	
	

	
	
}