package com.studentmanagementsystem.api.daoimpl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.studentmanagementsystem.api.dao.QuarterlyAttendanceReportDao;
import com.studentmanagementsystem.api.model.custom.quarterlyreport.ComplianceAndNonComplianceReportDto;
import com.studentmanagementsystem.api.model.custom.quarterlyreport.QuarterlyAttendanceFilterDto;
import com.studentmanagementsystem.api.model.custom.quarterlyreport.QuarterlyAttendanceReportDto;
import com.studentmanagementsystem.api.model.entity.DailyAttendanceModel;
import com.studentmanagementsystem.api.model.entity.QuarterlyAttendanceReportModel;
import com.studentmanagementsystem.api.model.entity.StudentModel;
import com.studentmanagementsystem.api.util.WebServiceUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class QuarterlyAttendanceReportDaoImp implements QuarterlyAttendanceReportDao {

	@Autowired
	private EntityManager entityManager;

	
	/**
	 * Retrieve the attendance summary report to update the quarterly attendance report.
	 */	 
	//    SELECT 
	//    d.Student_Id AS studentId,
	//    SUM(CASE WHEN d.AT_Status = 'P' THEN 1 ELSE 0 END) AS totalDaysOfPresent,
	//    SUM(CASE WHEN d.AT_Status = 'AB' THEN 1 ELSE 0 END) AS totalDaysOfAbsents,
	//    SUM(CASE WHEN d.AT_Status = 'AB' AND d.AT_Approved_SickLeave = 'Y' THEN 1 ELSE 0 END) AS totalApprovedSickdays,
	//    SUM(CASE WHEN d.AT_Status = 'AB' AND d.AT_Approved_Extra_Cur_Activities = 'Y' THEN 1 ELSE 0 END) AS totalApprovedActivitiesPermissionDays
	//	FROM daily_attendance_registration d
	//	JOIN student s ON d.Student_Id = s.STU_Id
	//	WHERE MONTH(d.AT_Date) IN (10,11,12)
	//	  AND YEAR(d.AT_Date) = 2025
	//	GROUP BY d.Student_Id;
	@Override
	public List<QuarterlyAttendanceReportDto> getAttendanceSummary(List<Integer> quarterMonth) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		CriteriaQuery<QuarterlyAttendanceReportDto> cq = cb.createQuery(QuarterlyAttendanceReportDto.class);

		Root<DailyAttendanceModel> dailyAttendanceRoot = cq.from(DailyAttendanceModel.class);

		Join<DailyAttendanceModel, StudentModel> studentJoin = dailyAttendanceRoot.join("studentModel");

		Predicate monthPredicate = cb.function(
		        "MONTH", Integer.class, dailyAttendanceRoot.get("attendanceDate")
		).in(quarterMonth);

		Predicate yearPredicate = cb.equal(
		        cb.function("YEAR", Integer.class, dailyAttendanceRoot.get("attendanceDate")),
		        WebServiceUtil.YEAR
		);

		Expression<Long> totalPresent = cb.sum(
		        cb.<Long>selectCase()
		                .when(cb.equal(dailyAttendanceRoot.get("attendanceStatus"), WebServiceUtil.PRESENT), 1L)
		                .otherwise(0L)
		);

		Expression<Long> totalAbsent = cb.sum(
		        cb.<Long>selectCase()
		                .when(cb.equal(dailyAttendanceRoot.get("attendanceStatus"), WebServiceUtil.ABSENT), 1L)
		                .otherwise(0L)
		);

		Expression<Long> totalSick = cb.sum(
		        cb.<Long>selectCase()
		                .when(cb.and(
		                        cb.equal(dailyAttendanceRoot.get("attendanceStatus"),  WebServiceUtil.ABSENT),
		                        cb.equal(dailyAttendanceRoot.get("longApprovedSickLeaveFlag"), WebServiceUtil.YES) 
		                ), 1L)
		                .otherwise(0L)
		);

		Expression<Long> totalExtra = cb.sum(
		        cb.<Long>selectCase()
		                .when(cb.and(
		                        cb.equal(dailyAttendanceRoot.get("attendanceStatus"),  WebServiceUtil.ABSENT),
		                        cb.equal(dailyAttendanceRoot.get("approvedExtraCurricularActivitiesFlag"), WebServiceUtil.YES)
		                ), 1L)
		                .otherwise(0L)
		);

		cq.multiselect(
		        studentJoin.get("studentId"),
		        totalPresent,
		        totalAbsent,
		        totalSick,
		        totalExtra
		).where(cb.and(monthPredicate, yearPredicate))
		 .groupBy(studentJoin.get("studentId"));

		return entityManager.createQuery(cq).getResultList();
	}


	/**
	 * Retrieve the list of non-compliance students for a given quarter and year.
	 * And
	 * Retrieve the list of compliance students for a given quarter and year.
	 *
	 */
	//	SELECT 
	//    s.student_id,
	//    qar.quarter_and_year,
	//    s.student_first_name,
	//    s.student_middle_name,
	//    s.student_last_name,
	//    qar.attendance_compliance_status
	//   FROM quarterly_attendance_report qar
	//   INNER JOIN student s ON qar.student_id = s.student_id
	//  WHERE qar.quarter_and_year = :quarterAndYear
	//  AND qar.attendance_compliance_status = :complianceStatus;
	@Override
	public List<ComplianceAndNonComplianceReportDto> getQuarterlyAttendanceList(
			QuarterlyAttendanceFilterDto quarterlyAttendanceFilterDto) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ComplianceAndNonComplianceReportDto> cq  =cb.createQuery(ComplianceAndNonComplianceReportDto.class);
		Root<QuarterlyAttendanceReportModel> quarterlyAttendanceReportRoot = cq.from(QuarterlyAttendanceReportModel.class);
		Join<QuarterlyAttendanceReportModel,StudentModel> quarterlyAttendanceReporAndStudentJoin = quarterlyAttendanceReportRoot.join("studentModel");
		
		cq.select(cb.construct(ComplianceAndNonComplianceReportDto.class,
				
				quarterlyAttendanceReporAndStudentJoin.get("studentId"),
				quarterlyAttendanceReportRoot.get("quarterAndYear"),
				quarterlyAttendanceReporAndStudentJoin.get("firstName"),
				quarterlyAttendanceReporAndStudentJoin.get("middleName"),
				quarterlyAttendanceReporAndStudentJoin.get("lastName"),
				quarterlyAttendanceReportRoot.get("attendanceComplianceStatus").get("description")	
				
				));
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (quarterlyAttendanceFilterDto.getStudentId() != null) {
			
			
		    predicates.add(cb.equal(
		        quarterlyAttendanceReporAndStudentJoin.get("studentId"), 
		        quarterlyAttendanceFilterDto.getStudentId()
		    ));
		}
		else if(quarterlyAttendanceFilterDto.getEmail()!=null && quarterlyAttendanceFilterDto.getEmail()!=" ") {
			String emailLike = "%"+ quarterlyAttendanceFilterDto.getEmail().toLowerCase() + "%";
			predicates.add(cb.like(cb.lower(quarterlyAttendanceReporAndStudentJoin.get("email")),emailLike));
		}
		else if(quarterlyAttendanceFilterDto.getPhoneNumber()!=null && quarterlyAttendanceFilterDto.getPhoneNumber()!=" ") {
			predicates.add(cb.like(cb.lower(quarterlyAttendanceReporAndStudentJoin.get("phoneNumber")),quarterlyAttendanceFilterDto.getPhoneNumber()));
		}
		
		if(quarterlyAttendanceFilterDto.getFilter()!=null) {
			predicates.add(cb.equal(quarterlyAttendanceReportRoot.get("attendanceComplianceStatus").get("code"),quarterlyAttendanceFilterDto.getFilter()));
		}
		predicates.add(cb.equal(quarterlyAttendanceReportRoot.get("quarterAndYear"), quarterlyAttendanceFilterDto.getQuarterAndYear()));
		predicates.add( cb.equal(quarterlyAttendanceReportRoot.get("studentModel").get("classOfStudy"),quarterlyAttendanceFilterDto.getClassOfStudy()));
		
		if (!predicates.isEmpty()) {
			cq.where(cb.and(predicates.toArray(new Predicate[0])));
		}
		
		TypedQuery<ComplianceAndNonComplianceReportDto> result = entityManager.createQuery(cq);
		result.setFirstResult(quarterlyAttendanceFilterDto.getSize());
		result.setMaxResults(quarterlyAttendanceFilterDto.getLength());
		
		
		return result.getResultList();	
	}
	
	/*
	 *send mail to quarterly report 
	 */

	@Override
	public List<QuarterlyAttendanceReportDto> getQuarterlyAttendanceReport(String quarterAndYear) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<QuarterlyAttendanceReportDto> cq  =cb.createQuery(QuarterlyAttendanceReportDto.class);
		Root<QuarterlyAttendanceReportModel> quarterlyAttendanceReportRoot = cq.from(QuarterlyAttendanceReportModel.class);
		Predicate quarterandYearCondition = cb.equal(quarterlyAttendanceReportRoot.get("quarterAndYear"),quarterAndYear);
		cq.select(cb.construct(QuarterlyAttendanceReportDto.class,
				 
				quarterlyAttendanceReportRoot.get("studentModel").get("studentId"),
				quarterlyAttendanceReportRoot.get("totalSchoolWorkingDays"),
				quarterlyAttendanceReportRoot.get("totalDaysOfPresent"),
				quarterlyAttendanceReportRoot.get("totalDaysOfAbsents"),				
				quarterlyAttendanceReportRoot.get("totalApprovedActivitiesPermissionDays"),
				quarterlyAttendanceReportRoot.get("totalApprovedSickdays"),
				quarterlyAttendanceReportRoot.get("attendanceComplianceStatus").get("description")
				
				)).where(quarterandYearCondition)	;	
		

		
		return entityManager.createQuery(cq).getResultList();
	}
	
	/**
	 * Find the total working days at particular quarter and year
	 */
	//	SELECT COUNT(DISTINCT da.AT_Date) AS totalWorkingDays
	//	FROM daily_attendance_registration da
	//	WHERE MONTH(da.AT_Date) IN (10,11,12)   -- example: your quarterMonth list
	//	  AND YEAR(da.AT_Date) = 2025; 
	@Override
	public Long getTotalWorkingDays(List<Integer> quarterMonth) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> totalWorkingDaysQuery = cb.createQuery(Long.class);

		Root<DailyAttendanceModel> root = totalWorkingDaysQuery.from(DailyAttendanceModel.class);

		Predicate monthPredicateCondition = cb.function(
		        "MONTH", Integer.class, root.get("attendanceDate")
		).in(quarterMonth);

		Predicate yearPredicateCondition = cb.equal(
		        cb.function("YEAR", Integer.class, root.get("attendanceDate")),
		        WebServiceUtil.YEAR
		);

		totalWorkingDaysQuery.select(
		        cb.countDistinct(root.get("attendanceDate"))
		).where(cb.and(monthPredicateCondition, yearPredicateCondition));
		
		return entityManager.createQuery(totalWorkingDaysQuery).getSingleResult();

	}
}
