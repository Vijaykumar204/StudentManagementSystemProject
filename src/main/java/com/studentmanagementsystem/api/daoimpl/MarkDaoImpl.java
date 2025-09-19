package com.studentmanagementsystem.api.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.studentmanagementsystem.api.dao.MarkDao;
import com.studentmanagementsystem.api.model.custom.quarterlyreport.QuarterlyAttendanceFilterDto;
import com.studentmanagementsystem.api.model.custom.studentmarks.StudentMarksDto;
import com.studentmanagementsystem.api.model.custom.studentmarks.ResultReport;
import com.studentmanagementsystem.api.model.entity.QuarterlyAttendanceReportModel;
import com.studentmanagementsystem.api.model.entity.MarkModel;
import com.studentmanagementsystem.api.model.entity.StudentModel;

import com.studentmanagementsystem.api.util.WebServiceUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;

import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

@Repository
public class MarkDaoImpl implements MarkDao {
	@Autowired
	private EntityManager entityManager;


	/**
	 * Retrieve the list of all student marks for a given quarter and year.
	 */
	
	@Override
	public List<StudentMarksDto> getAllStudentMarks(QuarterlyAttendanceFilterDto markFilterDto) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<StudentMarksDto> studentMarksQuery = cb.createQuery(StudentMarksDto.class);
		Root<MarkModel> studentMarksRoot = studentMarksQuery.from(MarkModel.class);
		
		Subquery<Integer> subquery = studentMarksQuery.subquery(Integer.class);
	    Root<MarkModel> subQueryStudentMarksRoot = subquery.from(MarkModel.class);
		
		Predicate quarterCondition = cb.equal(studentMarksRoot.get("quarterAndYear"), markFilterDto.getQuarterAndYear());
		studentMarksQuery.select(cb.construct(StudentMarksDto.class,

				studentMarksRoot.get("studentModel").get("studentId"),
				studentMarksRoot.get("quarterAndYear"),
				studentMarksRoot.get("tamil"), 
				studentMarksRoot.get("english"),		
			    studentMarksRoot.get("maths"),
				studentMarksRoot.get("science"), 
				studentMarksRoot.get("socialScience"),
				studentMarksRoot.get("totalMarks"),
				studentMarksRoot.get("result").get("code")

		)).where(quarterCondition);
		 
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		

			
        if (markFilterDto.getStudentId() != null && !"TOPPER".equals(markFilterDto.getFilter())) {
		    predicates.add(cb.equal(
		       studentMarksRoot.get("studentModel").get("studentId"),
		        markFilterDto.getStudentId()
		    ));
		}
		else if(markFilterDto.getEmail()!=null && !markFilterDto.getEmail().isBlank() && !"TOPPER".equals(markFilterDto.getFilter())) {
			String emailLike = "%"+ markFilterDto.getEmail().toLowerCase() + "%";
			predicates.add(cb.like(cb.lower( studentMarksRoot.get("studentModel").get("email")),emailLike));
		}
		else if(markFilterDto.getPhoneNumber()!=null && !markFilterDto.getPhoneNumber().isBlank() && !"TOPPER".equals(markFilterDto.getFilter())) {
			String phoneNumberLike = "%"+ markFilterDto.getPhoneNumber() + "%";
			predicates.add(cb.like(cb.lower( studentMarksRoot.get("studentModel").get("phoneNumber")),phoneNumberLike));
		}
        
        String filter = markFilterDto.getFilter();
        
        if(filter!=null) {
		
		if (filter.equals(WebServiceUtil.PASS)  ) {
			predicates.add(cb.equal(studentMarksRoot.get("result").get("code"), WebServiceUtil.PASS));
		}
		else if(filter.equals(WebServiceUtil.FAIL)){
			predicates.add(cb.equal(studentMarksRoot.get("result").get("code"), WebServiceUtil.FAIL));
		}
		else if(filter.equals("TOPPER")) {
			subquery.select(cb.max(subQueryStudentMarksRoot.get("totalMarks")))
            .where(
                cb.equal(subQueryStudentMarksRoot.get("quarterAndYear"), markFilterDto.getQuarterAndYear()),
                cb.equal(subQueryStudentMarksRoot.get("studentModel").get("classOfStudy"), markFilterDto.getClassOfStudy()),
                cb.equal(subQueryStudentMarksRoot.get("result").get("code"), WebServiceUtil.PASS)
            );
			predicates.add(cb.equal(studentMarksRoot.get("totalMarks"), subquery));
		}
        }
		
		predicates.add(cb.equal(studentMarksRoot.get("quarterAndYear"), markFilterDto.getQuarterAndYear()));
		predicates.add(cb.equal(studentMarksRoot.get("studentModel").get("classOfStudy"), markFilterDto.getClassOfStudy()));
		
		if (!predicates.isEmpty()) {
			studentMarksQuery.where(cb.and(predicates.toArray(new Predicate[0])));
		}

		return entityManager.createQuery(studentMarksQuery)
							.setFirstResult(markFilterDto.getSize())
							.setMaxResults(markFilterDto.getLength())
							.getResultList();
	}
	
	/**
	 * Retrieve the overall result summary for students in a given quarter and year.
	 */

	@Override
	public List<ResultReport> getResultReport(QuarterlyAttendanceFilterDto markFilterDto) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		CriteriaQuery<ResultReport> totalResultCountQuery = cb.createQuery(ResultReport.class);

		Root<MarkModel> studentMarkRoot = totalResultCountQuery.from(MarkModel.class);

		Join<MarkModel, StudentModel> studentMarksAndStudent = studentMarkRoot.join("studentModel");

		Join<StudentModel, QuarterlyAttendanceReportModel> studentAndQuarterlyReportJoin = studentMarksAndStudent
				.join("quarterlyAttendanceReportModel");

		
//		Path<String> quarter = studentMark.get("quarterAndYear");
		
		
		Expression<Long> totalCount = cb.count(studentMarkRoot.get("studentModel").get("studentId"));

		
	   Expression<Integer> totalPass = cb
				.sum(cb.<Integer>selectCase().when(cb.equal(studentMarkRoot.get("result").get("code"), WebServiceUtil.PASS), 1).otherwise(0));
		
		
		Expression<Integer> totalFail = cb
				.sum(cb.<Integer>selectCase().when(cb.equal(studentMarkRoot.get("result").get("code"), WebServiceUtil.FAIL), 1).otherwise(0));

			
		Expression<Integer> failDueToAttendance = cb
		.sum(cb.<Integer>selectCase().when(cb.equal(studentAndQuarterlyReportJoin.get("attendanceComplianceStatus").get("code"), WebServiceUtil.NON_COMPLIANCE), 1).otherwise(0));
		
		Predicate quarterAndYear = cb.and(cb.equal(studentAndQuarterlyReportJoin.get("quarterAndYear"), markFilterDto.getQuarterAndYear()),
				cb.equal(studentMarkRoot.get("quarterAndYear"), markFilterDto.getQuarterAndYear()));
		
		Predicate classOfStudy = cb.and(cb.equal(studentAndQuarterlyReportJoin.get("studentModel").get("classOfStudy"), markFilterDto.getClassOfStudy()),
				cb.equal(studentMarkRoot.get("studentModel").get("classOfStudy"), markFilterDto.getClassOfStudy()));
		
		totalResultCountQuery.select(cb.construct(ResultReport.class,
				studentMarkRoot.get("quarterAndYear"), 
				totalCount.as(Integer.class),
				totalPass.as(Integer.class), 
				totalFail.as(Integer.class), 
	//			failDueToMark.as(Integer.class),
				failDueToAttendance.as(Integer.class
						))).where(quarterAndYear,classOfStudy).groupBy(studentMarkRoot.get("quarterAndYear"));

		return entityManager.createQuery(totalResultCountQuery).getResultList();
	}
	
}
