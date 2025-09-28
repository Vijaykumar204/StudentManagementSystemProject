package com.studentmanagementsystem.api.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "student_marks")
public class MarkModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Mark_Id")
	private Long markId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_Id",referencedColumnName = "STU_Id")
	private StudentModel studentModel;
	
	@Column(name = "Mark_Quarter_And_Year",nullable = false,length=10)
	private String quarterAndYear;
	
	@Column(name = "Tamil_Mark",nullable = false)
	private Integer tamil;
	
	@Column(name = "English_Mark",nullable = false)
	private Integer english;
	
	@Column(name = "Maths_Mark",nullable = false)
	private Integer maths;
	
	@Column(name = "Science_Mark",nullable = false)
	private Integer science;
	
	@Column(name = "Social_science_Mark",nullable = false)
	private Integer socialScience;
	
	@Column(name = "Toatal_Mark")
	private Integer totalMarks;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="Result_Status",referencedColumnName = "CODE" )
	private StudentCodeModel result;
	
	@Column(name = "Mark_Percentage",nullable = false)
	private Integer percentage;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="Mark_Create_Teacher" ,referencedColumnName = "TEACH_Id",nullable = false)
	private TeacherModel createTeacher;


	@Column(name = "Mark_Create_Date_Time")
	private LocalDateTime createDate;
	
	@ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name ="Mark_Update_Teacher" ,referencedColumnName = "TEACH_Id")
	private TeacherModel updateTeacher;
	
	@Column(name = "Mark_Update_Date_Time")
	private LocalDateTime updateTime;
	
	
	public Long getMarkId() {
		return markId;
	}

	public void setMarkId(Long markId) {
		this.markId = markId;
	}

	public String getQuarterAndYear() {
		return quarterAndYear;
	}

	public void setQuarterAndYear(String quarterAndYear) {
		this.quarterAndYear = quarterAndYear;
	}

	public Integer getTamil() {
		return tamil;
	}

	public Integer setTamil(Integer tamil) {
		return this.tamil = tamil;
	}

	public Integer getEnglish() {
		return english;
	}

	public Integer setEnglish(Integer english) {
		return this.english = english;
	}

	public Integer getMaths() {
		return maths;
	}

	public Integer setMaths(Integer maths) {
		return this.maths = maths;
	}

	public Integer getScience() {
		return science;
	}

	public Integer setScience(Integer science) {
		return this.science = science;
	}

	public Integer getSocialScience() {
		return socialScience;
	}

	public Integer setSocialScience(Integer socialScience) {
		return this.socialScience = socialScience;
	}

	public Integer getTotalMarks() {
		return totalMarks;
	}

	public void setTotalMarks(Integer totalMarks) {
		this.totalMarks = totalMarks;
	}



	public StudentCodeModel getResult() {
		return result;
	}

	public void setResult(StudentCodeModel result) {
		this.result = result;
	}



	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}


	public StudentModel getStudentModel() {
		return studentModel;
	}

	public void setStudentModel(StudentModel studentModel) {
		this.studentModel = studentModel;
	}

	public TeacherModel getCreateTeacher() {
		return createTeacher;
	}

	public void setCreateTeacher(TeacherModel createTeacher) {
		this.createTeacher = createTeacher;
	}

	public TeacherModel getUpdateTeacher() {
		return updateTeacher;
	}

	public void setUpdateTeacher(TeacherModel updateTeacher) {
		this.updateTeacher = updateTeacher;
	}

	public Integer getPercentage() {
		return percentage;
	}

	public void setPercentage(Integer percentage) {
		this.percentage = percentage;
	}


	
	
}
