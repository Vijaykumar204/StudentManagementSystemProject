package com.studentmanagementsystem.api.daoimpl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.studentmanagementsystem.api.dao.QuarterlyAttendanceReportDao;
import com.studentmanagementsystem.api.model.custom.CommonFilterDto;
import com.studentmanagementsystem.api.model.custom.quarterlyreport.QuarterlyAttendanceReportDto;
import com.studentmanagementsystem.api.model.entity.DailyAttendanceModel;
import com.studentmanagementsystem.api.model.entity.QuarterlyAttendanceReportModel;
import com.studentmanagementsystem.api.model.entity.StudentModel;
import com.studentmanagementsystem.api.util.WebServiceUtil;
import jakarta.persistence.EntityManager;
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
	public List<QuarterlyAttendanceReportDto> quarterlyAttendanceList(
			CommonFilterDto filterDto) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<QuarterlyAttendanceReportDto> cq  =cb.createQuery(QuarterlyAttendanceReportDto.class);
		Root<QuarterlyAttendanceReportModel> quarterlyAttendanceRoot = cq.from(QuarterlyAttendanceReportModel.class);
	//	Join<QuarterlyAttendanceReportModel,StudentModel> quarterlyAttendanceReporAndStudentJoin = quarterlyAttendanceRoot.join("studentModel");
		
		
		cq.select(cb.construct(QuarterlyAttendanceReportDto.class,
				 
				quarterlyAttendanceRoot.get("studentModel").get("studentId"),
				 cb.concat(
			                cb.concat(
			                        cb.concat(cb.coalesce(quarterlyAttendanceRoot.get("studentModel").get("firstName"), ""), " "),
			                        cb.concat(cb.coalesce(quarterlyAttendanceRoot.get("studentModel").get("middleName"), ""), " ")
			                ),
			                cb.coalesce(quarterlyAttendanceRoot.get("studentModel").get("lastName"), "")
			        ),
				quarterlyAttendanceRoot.get("studentModel").get("classOfStudy"),
				quarterlyAttendanceRoot.get("studentModel").get("dateOfBirth"),
				quarterlyAttendanceRoot.get("studentModel").get("phoneNumber"),
				quarterlyAttendanceRoot.get("studentModel").get("email"),
				quarterlyAttendanceRoot.get("quarterAndYear"),
				quarterlyAttendanceRoot.get("totalSchoolWorkingDays"),
				quarterlyAttendanceRoot.get("totalDaysOfPresent"),
				quarterlyAttendanceRoot.get("totalDaysOfAbsents"),				
				quarterlyAttendanceRoot.get("totalApprovedActivitiesPermissionDays"),
				quarterlyAttendanceRoot.get("totalApprovedSickdays"),
				quarterlyAttendanceRoot.get("attendanceComplianceStatus").get("code"),
				quarterlyAttendanceRoot.get("attendancePercentage")
				
				));
		
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		// Name , email , phone number filter
		 if(filterDto.getSearchBy()!=null && filterDto.getSearchValue()!=null) {
		        switch(filterDto.getSearchBy().toLowerCase())
		        {
		        
		        case WebServiceUtil.NAME:
		        	 Predicate fullName = cb.like(
		        			 cb.concat(
		 			                cb.concat(
		 			                        cb.concat(cb.coalesce(quarterlyAttendanceRoot.get("studentModel").get("firstName"), ""), " "),
		 			                        cb.concat(cb.coalesce(quarterlyAttendanceRoot.get("studentModel").get("middleName"), ""), " ")
		 			                ),
		 			                cb.coalesce(quarterlyAttendanceRoot.get("studentModel").get("lastName"), "")
		 			        ),
		                    "%" + filterDto.getSearchValue() + "%"
		            );
		        	
		        	predicates.add(fullName);
		        	break;
		        	
		        case WebServiceUtil.EMAIL:
		        	Predicate email =cb.like(cb.lower(quarterlyAttendanceRoot.get("studentModel").get("email")) ,"%"+ filterDto.getSearchValue().toLowerCase() + "%");
					predicates.add(email);
					break;
					
		        case WebServiceUtil.PHONE_NUMBER:
		        	Predicate phoneNumber = cb.like(quarterlyAttendanceRoot.get("studentModel").get("phoneNumber"),"%"+ filterDto.getSearchValue() + "%");
					predicates.add(phoneNumber);
					break;
		        }
		        }
		// Compliance and Non compliance status filter
		if(WebServiceUtil.COMPLIANCE.equals(filterDto.getStatus())) {
			predicates.add(cb.equal(quarterlyAttendanceRoot.get("attendanceComplianceStatus").get("code"),WebServiceUtil.COMPLIANCE));
		}
		else if (WebServiceUtil.NON_COMPLIANCE.equals(filterDto.getStatus())) {
			predicates.add(cb.equal(quarterlyAttendanceRoot.get("attendanceComplianceStatus").get("code"),WebServiceUtil.NON_COMPLIANCE));
		}
		
		 
		 // Sorting filter
    	if(filterDto.getOrderColumn()!=null && filterDto.getOrderType() !=null) {
		 switch(filterDto.getOrderColumn()) {
		 
		 case WebServiceUtil.NAME:
			 if(WebServiceUtil.ASCENDING_ORDER .equals(filterDto.getOrderType())) 
        		 cq.orderBy(cb.asc(quarterlyAttendanceRoot.get("studentModel").get("firstName")));
        	else if(WebServiceUtil.DESCENDING_ORDER .equals(filterDto.getOrderType()))
        		cq.orderBy(cb.desc(quarterlyAttendanceRoot.get("studentModel").get("firstName")));
			 
			 break;
		 case WebServiceUtil.EMAIL:
			 if(WebServiceUtil.ASCENDING_ORDER .equals(filterDto.getOrderType())) 
        		 cq.orderBy(cb.asc(quarterlyAttendanceRoot.get("studentModel").get("email")));
        	else if(WebServiceUtil.DESCENDING_ORDER .equals(filterDto.getOrderType()))
        		cq.orderBy(cb.desc(quarterlyAttendanceRoot.get("studentModel").get("email")));
			 
			 break;
		 
		 case WebServiceUtil.PHONE_NUMBER:
			 if(WebServiceUtil.ASCENDING_ORDER .equals(filterDto.getOrderType())) 
        		 cq.orderBy(cb.asc(quarterlyAttendanceRoot.get("studentModel").get("phoneNumber")));
        	else if(WebServiceUtil.DESCENDING_ORDER .equals(filterDto.getOrderType()))
        		cq.orderBy(cb.desc(quarterlyAttendanceRoot.get("studentModel").get("phoneNumber")));
			 break;
			 
		 case WebServiceUtil.DOB:
			 if(WebServiceUtil.ASCENDING_ORDER .equals(filterDto.getOrderType())) 
        		 cq.orderBy(cb.asc(quarterlyAttendanceRoot.get("studentModel").get("dateOfBirth")));
        	else if(WebServiceUtil.DESCENDING_ORDER .equals(filterDto.getOrderType()))
        		cq.orderBy(cb.desc(quarterlyAttendanceRoot.get("studentModel").get("dateOfBirth")));
			 break;
//		 case WebServiceUtil.S_NO:
//			 if(WebServiceUtil.ASCENDING_ORDER .equals(quarterlyAttendanceFilterDto.getOrderType())) 
//        		 cq.orderBy(cb.asc(quarterlyAttendanceRoot.get("")));
//        	else if(WebServiceUtil.DESCENDING_ORDER .equals(quarterlyAttendanceFilterDto.getOrderType()))
//        		cq.orderBy(cb.desc(quarterlyAttendanceRoot.get("")));
//			 break;
		default:
			if(WebServiceUtil.ASCENDING_ORDER .equals(filterDto.getOrderType())) 
       		 cq.orderBy(cb.asc(quarterlyAttendanceRoot.get(filterDto.getOrderColumn())));
      	else if(WebServiceUtil.DESCENDING_ORDER .equals(filterDto.getOrderType()))
       		cq.orderBy(cb.desc(quarterlyAttendanceRoot.get(filterDto.getOrderColumn())));	

		 }}
		
		 
		
		// quarterAndYear and classOfStudy filter
		predicates.add(cb.equal(quarterlyAttendanceRoot.get("quarterAndYear"), filterDto.getQuarterAndYear()));
		predicates.add( cb.equal(quarterlyAttendanceRoot.get("studentModel").get("classOfStudy"),filterDto.getClassOfStudy()));
		
		if (!predicates.isEmpty()) {
			cq.where(cb.and(predicates.toArray(new Predicate[0])));
		}
		
		return  entityManager.createQuery(cq)
				             .setFirstResult(filterDto.getStart())
				             .setMaxResults(filterDto.getLength())
				             .getResultList();
			
	}
	
	/*
	 *send mail to quarterly report 
	 */

	@Override
	public List<QuarterlyAttendanceReportDto> getQuarterlyAttendanceReport(String quarterAndYear,Integer classOfStudy) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<QuarterlyAttendanceReportDto> cq  =cb.createQuery(QuarterlyAttendanceReportDto.class);
		Root<QuarterlyAttendanceReportModel> quarterlyAttendanceReportRoot = cq.from(QuarterlyAttendanceReportModel.class);
		Predicate quarterandYearCondition = cb.equal(quarterlyAttendanceReportRoot.get("quarterAndYear"),quarterAndYear);
		Predicate classCondition = cb.equal(quarterlyAttendanceReportRoot.get("studentModel").get("classOfStudy"), classOfStudy);
		cq.select(cb.construct(QuarterlyAttendanceReportDto.class,
				 
				quarterlyAttendanceReportRoot.get("studentModel").get("studentId"),
				quarterlyAttendanceReportRoot.get("totalSchoolWorkingDays"),
				quarterlyAttendanceReportRoot.get("totalDaysOfPresent"),
				quarterlyAttendanceReportRoot.get("totalDaysOfAbsents"),				
				quarterlyAttendanceReportRoot.get("totalApprovedActivitiesPermissionDays"),
				quarterlyAttendanceReportRoot.get("totalApprovedSickdays"),
				quarterlyAttendanceReportRoot.get("attendanceComplianceStatus").get("description")
				
				)).where(quarterandYearCondition,classCondition)	;	
		

		
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
