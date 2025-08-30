package com.studentmanagementsystem.api.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "student_marks")
public class StudentMarks {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name = "Mark_Id")
	private Long markId;
	
	@Column(name = "Mark_Quarter_And_Year",nullable = false,length=10)
	private String quarterAndYear;
	
	@Column(name = "Tamil_Mark",nullable = false)
	private int tamil;
	
	@Column(name = "English_Mark",nullable = false)
	private int english;
	
	@Column(name = "Maths_Mark",nullable = false)
	private int maths;
	
	@Column(name = "Science_Mark",nullable = false)
	private int science;
	
	@Column(name = "Social_science_Mark",nullable = false)
	private int socialScience;
	
	@Column(name = "Toatal_Mark")
	private int totalMarks;
	
	@Column(name = "Result_Status")
	private char result;
	
	@Column(name = "Mark_Create_Teacher",nullable = false)
	private Long createTeacher;
	
	@Column(name = "Mark_Create_Date_Time")
	private LocalDateTime createDate;
	
	@Column(name = "Mark_Update_Teacher")
	private Long updateTeacher;
	
	@Column(name = "Mark_Update_Date_Time")
	private LocalDateTime updateTime;
	
	@Column(name = "Failed_for_Mark", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
	private Boolean failedForMark = false;
	
	@ManyToOne
	@JoinColumn(name = "student_Id",referencedColumnName = "STU_Id")
	private StudentModel studentModel;

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

	public int getTamil() {
		return tamil;
	}

	public int setTamil(int tamil) {
		return this.tamil = tamil;
	}

	public int getEnglish() {
		return english;
	}

	public int setEnglish(int english) {
		return this.english = english;
	}

	public int getMaths() {
		return maths;
	}

	public int setMaths(int maths) {
		return this.maths = maths;
	}

	public int getScience() {
		return science;
	}

	public int setScience(int science) {
		return this.science = science;
	}

	public int getSocialScience() {
		return socialScience;
	}

	public int setSocialScience(int socialScience) {
		return this.socialScience = socialScience;
	}

	public int getTotalMarks() {
		return totalMarks;
	}

	public void setTotalMarks(int totalMarks) {
		this.totalMarks = totalMarks;
	}

	public char getResult() {
		return result;
	}

	public void setResult(char result) {
		this.result = result;
	}

	public StudentModel getStudentModel() {
		return studentModel;
	}

	public void setStudentModel(StudentModel studentModel) {
		this.studentModel = studentModel;
	}

	public Long getCreateTeacher() {
		return createTeacher;
	}

	public void setCreateTeacher(Long createTeacher) {
		this.createTeacher = createTeacher;
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

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}

	public Boolean getFailedForMark() {
		return failedForMark;
	}

	public void setFailedForMark(Boolean failedForMark) {
		this.failedForMark = failedForMark;
	}
	
	
}
