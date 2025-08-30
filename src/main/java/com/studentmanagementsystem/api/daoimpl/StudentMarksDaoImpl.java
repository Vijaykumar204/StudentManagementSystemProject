package com.studentmanagementsystem.api.daoimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.studentmanagementsystem.api.dao.StudentMarksDao;
import com.studentmanagementsystem.api.model.custom.studentmarks.ClassTopperDto;
import com.studentmanagementsystem.api.model.custom.studentmarks.ComplianceStudentWithPassOrFail;
import com.studentmanagementsystem.api.model.custom.studentmarks.StudentMarksDto;
import com.studentmanagementsystem.api.model.custom.studentmarks.TotalResultCountdto;
import com.studentmanagementsystem.api.model.entity.QuarterlyAttendanceReportModel;
import com.studentmanagementsystem.api.model.entity.StudentMarks;
import com.studentmanagementsystem.api.model.entity.StudentModel;
import com.studentmanagementsystem.api.repository.QuarterlyAttendanceModelRepository;
import com.studentmanagementsystem.api.repository.StudentMarksRepository;
import com.studentmanagementsystem.api.util.WebServiceUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

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

		return studentMarksRepository.findByStudentIdAndQuarterAndYear(studentId, quarterAndYear);
	}

	@Override
	public void saveStudentMarks(StudentMarks studentMark) {
		studentMarksRepository.save(studentMark);

	}

	@Override
	public List<ComplianceStudentWithPassOrFail> getAllComplianceStudentPassOrFail(String quarterAndYear) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ComplianceStudentWithPassOrFail> cq = cb.createQuery(ComplianceStudentWithPassOrFail.class);

//	    Root<StudentMarks> sm = cq.from(StudentMarks.class);

		// Join with QuarterlyAttendanceReport
//	    Join<StudentMarks, QuarterlyAttendanceReportModel> qar = sm.join("studnetModel"); 
		// 👆 field name inside StudentMarks entity (check if it's mapped like
		// @ManyToOne private QuarterlyAttendanceReportModel quarterlyAttendanceReport;)

		Root<StudentMarks> studentMark = cq.from(StudentMarks.class);

		Join<StudentMarks, StudentModel> student = studentMark.join("studentModel");

		Join<StudentModel, QuarterlyAttendanceReportModel> quarterlyReport = student
				.join("quarterlyAttendanceReportModel");

		// Conditions
		Predicate qarQuarterCondition = cb.equal(quarterlyReport.get("quarterAndYear"), quarterAndYear);
		Predicate smQuarterCondition = cb.equal(studentMark.get("quarterAndYear"), quarterAndYear);
		Predicate complianceCondition = cb.equal(quarterlyReport.get("attendanceComplianceStatus"),
				WebServiceUtil.COMPLIANCE);

		cq.select(cb.construct(ComplianceStudentWithPassOrFail.class, studentMark.get("studentModel").get("studentId"),
				studentMark.get("quarterAndYear"), studentMark.get("result")))
				.where(cb.and(qarQuarterCondition, smQuarterCondition, complianceCondition));

		return entityManager.createQuery(cq).getResultList();
	}

	@Override
	public List<StudentMarksDto> getAllStudentMarks(String quarterAndYear) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<StudentMarksDto> cq = cb.createQuery(StudentMarksDto.class);
		Root<StudentMarks> studentMarks = cq.from(StudentMarks.class);
		Predicate quarterCondition = cb.equal(studentMarks.get("quarterAndYear"), quarterAndYear);
		cq.multiselect(

				studentMarks.get("studentModel").get("studentId"), studentMarks.get("quarterAndYear"),
				studentMarks.get("tamil"), studentMarks.get("english"), studentMarks.get("maths"),
				studentMarks.get("science"), studentMarks.get("socialScience"), studentMarks.get("totalMarks"),
				studentMarks.get("result")

		).where(quarterCondition);

		return entityManager.createQuery(cq).getResultList();
	}

	@Override
	public List<TotalResultCountdto> getToatalResultCount(String quarterAndYear) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		CriteriaQuery<TotalResultCountdto> cq = cb.createQuery(TotalResultCountdto.class);

		Root<StudentMarks> studentMark = cq.from(StudentMarks.class);

		Join<StudentMarks, StudentModel> student = studentMark.join("studentModel");

		Join<StudentModel, QuarterlyAttendanceReportModel> quarterlyReport = student
				.join("quarterlyAttendanceReportModel");

		
//		Path<String> quarter = studentMark.get("quarterAndYear");
		
		
		Expression<Long> totalCount = cb.count(studentMark.get("studentModel").get("studentId"));

		
		Expression<Integer> totalPass = cb
				.sum(cb.<Integer>selectCase().when(cb.equal(studentMark.get("result"), "P"), 1).otherwise(0));

		
		Expression<Integer> totalFail = cb
				.sum(cb.<Integer>selectCase().when(cb.equal(studentMark.get("result"), "F"), 1).otherwise(0));

		
		Expression<Integer> failDueToMark = cb.sum(cb.<Integer>selectCase()
				.when(cb.and(cb.equal(studentMark.get("result"), "F"),
						cb.equal(quarterlyReport.get("attendanceComplianceStatus"), "C"),
						cb.isTrue(studentMark.get("failedForMark"))), 1)
				.otherwise(0));

	
		Expression<Integer> failDueToAttendance = cb
				.sum(cb.<Integer>selectCase().when(cb.and(cb.equal(studentMark.get("result"), "F"),
						cb.equal(quarterlyReport.get("attendanceComplianceStatus"), "NC")), 1).otherwise(0));

		
		Predicate predicate = cb.and(cb.equal(quarterlyReport.get("quarterAndYear"), quarterAndYear),
				cb.equal(studentMark.get("quarterAndYear"), quarterAndYear));
		
		cq.select(cb.construct(TotalResultCountdto.class,
				studentMark.get("quarterAndYear"), 
				totalCount.as(Integer.class),
				totalPass.as(Integer.class), 
				totalFail.as(Integer.class), 
				failDueToMark.as(Integer.class),
				failDueToAttendance.as(Integer.class
						))).where(predicate).groupBy(studentMark.get("quarterAndYear"));

		return entityManager.createQuery(cq).getResultList();
	}

	@Override
	public ClassTopperDto getClassTopper(String quarterAndYear) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		
		 CriteriaQuery<ClassTopperDto> cq = cb.createQuery(ClassTopperDto.class);
		    Root<StudentMarks> studentMark = cq.from(StudentMarks.class);
		    Join<StudentMarks, StudentModel> student = studentMark.join("studentModel");

		    // Subquery to find MAX(totalMarks) for given quarter and PRESENT result
		    Subquery<Integer> subquery = cq.subquery(Integer.class);
		    Root<StudentMarks> subRoot = subquery.from(StudentMarks.class);
		     subquery.select(cb.max(subRoot.get("totalMarks")))
		            .where(
		                cb.equal(subRoot.get("quarterAndYear"), quarterAndYear),
		                cb.equal(subRoot.get("result"), WebServiceUtil.PRESENT)
		            );

		    // Main query: fetch student whose marks = MAX(totalMarks)
		    cq.select(cb.construct(
		            ClassTopperDto.class,
		            student.get("studentId"),
		            studentMark.get("quarterAndYear"),
		            student.get("studentFirstName"),
		            student.get("studentMiddleName"),
		            student.get("studentLastName"),
		            studentMark.get("totalMarks")
		    ))
		    .where(
		        cb.equal(studentMark.get("quarterAndYear"), quarterAndYear),
		        cb.equal(studentMark.get("result"), WebServiceUtil.PRESENT),
		        cb.equal(studentMark.get("totalMarks"), subquery) // ✅ match with max
		    );

		    return entityManager.createQuery(cq).getSingleResult();
	}

}
