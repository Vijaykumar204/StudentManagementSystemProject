package com.studentmanagementsystem.api.daoimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.studentmanagementsystem.api.dao.StudentMarksDao;
import com.studentmanagementsystem.api.model.custom.studentmarks.ComplianceStudentWithPassOrFail;
import com.studentmanagementsystem.api.model.custom.studentmarks.StudentMarksDto;
import com.studentmanagementsystem.api.model.entity.QuarterlyAttendanceReportModel;
import com.studentmanagementsystem.api.model.entity.StudentMarks;
import com.studentmanagementsystem.api.model.entity.StudentModel;
import com.studentmanagementsystem.api.repository.QuarterlyAttendanceModelRepository;
import com.studentmanagementsystem.api.repository.StudentMarksRepository;
import com.studentmanagementsystem.api.util.WebServiceUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class StudentMarksDaoImpl implements StudentMarksDao {
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
   private StudentMarksRepository studentMarksRepository;
	
	@Autowired
	private QuarterlyAttendanceModelRepository quarterlyAttendanceModelRepository;

	@Override
	public StudentMarks getStudentModelandquarterAndYear(Long studentId, String quarterAndYear) {

		return studentMarksRepository.findByStudentIdAndQuarterAndYear(studentId,quarterAndYear);
	}

	@Override
	public void saveStudentMarks(StudentMarks studentMark) {
		studentMarksRepository.save(studentMark);
		
	}

	@Override
	public List<ComplianceStudentWithPassOrFail> getAllComplianceStudentPassOrFail(String quarterAndYear) {
	 		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ComplianceStudentWithPassOrFail> cq=cb.createQuery(ComplianceStudentWithPassOrFail.class);
		Root<StudentMarks> studentMark = cq.from(StudentMarks.class);
		
		Join<StudentMarks, StudentModel> student = studentMark.join("studentModel");

		Join<StudentModel, QuarterlyAttendanceReportModel> quarterlyReport = student.join("quarterlyAttendanceReports");

		
//		Predicate studentIdCondition = cb.equal(studentMark.get("studentModel").get("studentId"), quarterlyReport.get("studentModel").get("studentId"));
		Predicate complianceStatus = cb.equal(quarterlyReport.get("attendanceComplianceStatus"), WebServiceUtil.COMPLIANCE);
		cq.select(cb.construct(ComplianceStudentWithPassOrFail.class,
				
				studentMark.get("studentModel").get("studentId"),
				studentMark.get("quarterAndYear"),
				studentMark.get("result")

				)).where(complianceStatus);
		
		return entityManager.createQuery(cq).getResultList();
	}

}
