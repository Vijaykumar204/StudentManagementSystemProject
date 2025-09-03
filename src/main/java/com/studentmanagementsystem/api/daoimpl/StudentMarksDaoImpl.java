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
import com.studentmanagementsystem.api.model.entity.MarkModel;
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



	@Override
	public MarkModel getStudentModelandquarterAndYear(Long studentId, String quarterAndYear) {

		return studentMarksRepository.findByStudentIdAndQuarterAndYear(studentId, quarterAndYear);
	}

	@Override
	public void saveStudentMarks(MarkModel studentMark) {
		studentMarksRepository.save(studentMark);

	}

	@Override
	public List<ComplianceStudentWithPassOrFail> getAllComplianceStudentPassOrFail(String quarterAndYear) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ComplianceStudentWithPassOrFail> complianceStudentQuery = cb.createQuery(ComplianceStudentWithPassOrFail.class);

//	    Root<StudentMarks> sm = cq.from(StudentMarks.class);

		// Join with QuarterlyAttendanceReport
//	    Join<StudentMarks, QuarterlyAttendanceReportModel> qar = sm.join("studnetModel"); 
		// 👆 field name inside StudentMarks entity (check if it's mapped like
		// @ManyToOne private QuarterlyAttendanceReportModel quarterlyAttendanceReport;)

		Root<MarkModel> studentMarksRoot = complianceStudentQuery.from(MarkModel.class);

		Join<MarkModel, StudentModel> studentMarksAndStudentJoin = studentMarksRoot.join("studentModel");

		Join<StudentModel, QuarterlyAttendanceReportModel> studentAndQuarterlyAttendanceReportJoin = studentMarksAndStudentJoin
				.join("quarterlyAttendanceReportModel");

		// Conditions
		Predicate qarQuarterCondition = cb.equal(studentAndQuarterlyAttendanceReportJoin.get("quarterAndYear"), quarterAndYear);
		Predicate smQuarterCondition = cb.equal(studentMarksRoot.get("quarterAndYear"), quarterAndYear);
		Predicate complianceCondition = cb.equal(studentAndQuarterlyAttendanceReportJoin.get("attendanceComplianceStatus"),
				WebServiceUtil.COMPLIANCE);

		complianceStudentQuery.select(cb.construct(ComplianceStudentWithPassOrFail.class, studentMarksRoot.get("studentModel").get("studentId"),
				studentMarksRoot.get("quarterAndYear"), studentMarksRoot.get("result")))
				.where(cb.and(qarQuarterCondition, smQuarterCondition, complianceCondition));

		return entityManager.createQuery(complianceStudentQuery).getResultList();
	}

	@Override
	public List<StudentMarksDto> getAllStudentMarks(String quarterAndYear) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<StudentMarksDto> studentMarksQuery = cb.createQuery(StudentMarksDto.class);
		Root<MarkModel> studentMarksRoot = studentMarksQuery.from(MarkModel.class);
		Predicate quarterCondition = cb.equal(studentMarksRoot.get("quarterAndYear"), quarterAndYear);
		studentMarksQuery.multiselect(

				studentMarksRoot.get("studentModel").get("studentId"),
				studentMarksRoot.get("quarterAndYear"),
				studentMarksRoot.get("tamil"), 
				studentMarksRoot.get("english"),		
			    studentMarksRoot.get("maths"),
				studentMarksRoot.get("science"), 
				studentMarksRoot.get("socialScience"),
				studentMarksRoot.get("totalMarks"),
				studentMarksRoot.get("result")

		).where(quarterCondition);

		return entityManager.createQuery(studentMarksQuery).getResultList();
	}

	@Override
	public List<TotalResultCountdto> getToatalResultCount(String quarterAndYear) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		CriteriaQuery<TotalResultCountdto> totalResultCountQuery = cb.createQuery(TotalResultCountdto.class);

		Root<MarkModel> studentMarkRoot = totalResultCountQuery.from(MarkModel.class);

		Join<MarkModel, StudentModel> studentMarksAndStudent = studentMarkRoot.join("studentModel");

		Join<StudentModel, QuarterlyAttendanceReportModel> studentAndQuarterlyReportJoin = studentMarksAndStudent
				.join("quarterlyAttendanceReportModel");

		
//		Path<String> quarter = studentMark.get("quarterAndYear");
		
		
		Expression<Long> totalCount = cb.count(studentMarkRoot.get("studentModel").get("studentId"));

		
		Expression<Integer> totalPass = cb
				.sum(cb.<Integer>selectCase().when(cb.equal(studentMarkRoot.get("result"), "P"), 1).otherwise(0));

		
		Expression<Integer> totalFail = cb
				.sum(cb.<Integer>selectCase().when(cb.equal(studentMarkRoot.get("result"), "F"), 1).otherwise(0));

		
		Expression<Integer> failDueToMark = cb.sum(cb.<Integer>selectCase()
				.when(cb.and(cb.equal(studentMarkRoot.get("result"), "F"),
						cb.equal(studentAndQuarterlyReportJoin.get("attendanceComplianceStatus"), "C"),
						cb.isTrue(studentMarkRoot.get("failedForMark"))), 1)
				.otherwise(0));

	
		Expression<Integer> failDueToAttendance = cb
				.sum(cb.<Integer>selectCase().when(cb.and(cb.equal(studentMarkRoot.get("result"), "F"),
						cb.equal(studentAndQuarterlyReportJoin.get("attendanceComplianceStatus"), "NC")), 1).otherwise(0));

		
		Predicate predicate = cb.and(cb.equal(studentAndQuarterlyReportJoin.get("quarterAndYear"), quarterAndYear),
				cb.equal(studentMarkRoot.get("quarterAndYear"), quarterAndYear));
		
		totalResultCountQuery.select(cb.construct(TotalResultCountdto.class,
				studentMarkRoot.get("quarterAndYear"), 
				totalCount.as(Integer.class),
				totalPass.as(Integer.class), 
				totalFail.as(Integer.class), 
				failDueToMark.as(Integer.class),
				failDueToAttendance.as(Integer.class
						))).where(predicate).groupBy(studentMarkRoot.get("quarterAndYear"));

		return entityManager.createQuery(totalResultCountQuery).getResultList();
	}

	@Override
	public ClassTopperDto getClassTopper(String quarterAndYear) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		
		 CriteriaQuery<ClassTopperDto> classTopperQuery = cb.createQuery(ClassTopperDto.class);
		    Root<MarkModel> studentMark = classTopperQuery.from(MarkModel.class);
		    Join<MarkModel, StudentModel> studentMarksAndStudentJoin = studentMark.join("studentModel");

		    // Subquery to find MAX(totalMarks) for given quarter and PRESENT result
		    Subquery<Integer> subquery = classTopperQuery.subquery(Integer.class);
		    Root<MarkModel> subQueryStudentMarksRoot = subquery.from(MarkModel.class);
		     subquery.select(cb.max(subQueryStudentMarksRoot.get("totalMarks")))
		            .where(
		                cb.equal(subQueryStudentMarksRoot.get("quarterAndYear"), quarterAndYear),
		                cb.equal(subQueryStudentMarksRoot.get("result"), WebServiceUtil.PRESENT)
		            );

		    // Main query: fetch student whose marks = MAX(totalMarks)
		     classTopperQuery.select(cb.construct(
		            ClassTopperDto.class,
		            studentMarksAndStudentJoin.get("studentId"),
		            studentMark.get("quarterAndYear"),
		            studentMarksAndStudentJoin.get("studentFirstName"),
		            studentMarksAndStudentJoin.get("studentMiddleName"),
		            studentMarksAndStudentJoin.get("studentLastName"),
		            studentMark.get("totalMarks")
		    ))
		    .where(
		        cb.equal(studentMark.get("quarterAndYear"), quarterAndYear),
		        cb.equal(studentMark.get("result"), WebServiceUtil.PRESENT),
		        cb.equal(studentMark.get("totalMarks"), subquery) // ✅ match with max
		    );

		    return entityManager.createQuery(classTopperQuery).getSingleResult();
	}

}
