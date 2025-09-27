package com.studentmanagementsystem.api.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.studentmanagementsystem.api.dao.MarkDao;
import com.studentmanagementsystem.api.model.custom.studentmarks.StudentMarksDto;
import com.studentmanagementsystem.api.model.custom.CommonFilterDto;
import com.studentmanagementsystem.api.model.custom.studentmarks.ResultReport;
import com.studentmanagementsystem.api.model.entity.QuarterlyAttendanceModel;
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
	public List<StudentMarksDto> getAllStudentMarks(CommonFilterDto filterDto) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<StudentMarksDto> cq = cb.createQuery(StudentMarksDto.class);
		Root<MarkModel> markRoot = cq.from(MarkModel.class);
		
		Subquery<Integer> sq = cq.subquery(Integer.class);
	    Root<MarkModel> sqMarkRoot = sq.from(MarkModel.class);
		
	//	Predicate quarterCondition = cb.equal(markRoot.get("quarterAndYear"), filterDto.getQuarterAndYear());
		cq.select(cb.construct(StudentMarksDto.class,

				markRoot.get("studentModel").get("studentId"),
				cb.concat(
		                cb.concat(
		                        cb.concat(cb.coalesce(markRoot.get("studentModel").get("firstName"), ""), " "),
		                        cb.concat(cb.coalesce(markRoot.get("studentModel").get("middleName"), ""), " ")
		                ),
		                cb.coalesce(markRoot.get("studentModel").get("lastName"), "")
		        ),
				 markRoot.get("studentModel").get("classOfStudy"),
				 markRoot.get("studentModel").get("dateOfBirth"),
				 markRoot.get("studentModel").get("phoneNumber"),
				 markRoot.get("studentModel").get("email"),
				markRoot.get("quarterAndYear"),
				markRoot.get("tamil"), 
				markRoot.get("english"),		
			    markRoot.get("maths"),
				markRoot.get("science"), 
				markRoot.get("socialScience"),
				markRoot.get("totalMarks"),
				markRoot.get("percentage"),
				markRoot.get("result").get("code")
				

		));
		 
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		

		//Name , email ,phoneNumber
		 if(filterDto.getSearchBy()!=null && filterDto.getSearchValue()!=null) {
			  
		        switch(filterDto.getSearchBy().toLowerCase())
		        {
		        case WebServiceUtil.NAME:
		        	 Predicate fullName = cb.like(
		        			 cb.concat(
		 			                cb.concat(
		 			                        cb.concat(cb.coalesce(markRoot.get("studentModel").get("firstName"), ""), " "),
		 			                        cb.concat(cb.coalesce(markRoot.get("studentModel").get("middleName"), ""), " ")
		 			                ),
		 			                
		 			                cb.coalesce(markRoot.get("studentModel").get("lastName"), "")
		 			        ),
		                    "%" + filterDto.getSearchValue() + "%"
		            );
		        	
		        	predicates.add(fullName);
		        	break;
		        	
		        case WebServiceUtil.EMAIL:
		        	Predicate email =cb.like(cb.lower(markRoot.get("studentModel").get("email")) ,"%"+ filterDto.getSearchValue().toLowerCase() + "%");
					predicates.add(email);
					break;
					
		        case WebServiceUtil.PHONE_NUMBER:
		        	Predicate phoneNumber = cb.like(markRoot.get("studentModel").get("phoneNumber"),"%"+ filterDto.getSearchValue() + "%");
					predicates.add(phoneNumber);
					break;
		        }
		        }
        
		// Status filter
        String status = filterDto.getStatus();
        if(status!=null && filterDto.getTopper() == null) {
		
		if (status.equals(WebServiceUtil.PASS)  ) {
			predicates.add(cb.equal(markRoot.get("result").get("code"), WebServiceUtil.PASS));
		}
		else if(status.equals(WebServiceUtil.FAIL)){
			predicates.add(cb.equal(markRoot.get("result").get("code"), WebServiceUtil.FAIL));
		}
        }
        else {
        	if(WebServiceUtil.TOPPER.equals(filterDto.getTopper())) {
        		sq.select(cb.max(sqMarkRoot.get("totalMarks")))
              .where(
                  cb.equal(sqMarkRoot.get("quarterAndYear"), filterDto.getQuarterAndYear()),
                  cb.equal(sqMarkRoot.get("studentModel").get("classOfStudy"), filterDto.getClassOfStudy()),
                  cb.equal(sqMarkRoot.get("result").get("code"), WebServiceUtil.PASS)
              );
  			predicates.add(cb.equal(markRoot.get("totalMarks"), sq));
        	}
        }

        
   	 // Sorting filter
    	if(filterDto.getOrderColumn()!=null && filterDto.getOrderType() !=null) {
		 switch(filterDto.getOrderColumn()) {
		 
		 case WebServiceUtil.NAME:
			 if(WebServiceUtil.ASCENDING_ORDER .equals(filterDto.getOrderType())) 
        		 cq.orderBy(cb.asc(markRoot.get("studentModel").get("firstName")));
        	else if(WebServiceUtil.DESCENDING_ORDER .equals(filterDto.getOrderType()))
        		cq.orderBy(cb.desc(markRoot.get("studentModel").get("firstName")));
			 
			 break;
		 case WebServiceUtil.EMAIL:
			 if(WebServiceUtil.ASCENDING_ORDER .equals(filterDto.getOrderType())) 
        		 cq.orderBy(cb.asc(markRoot.get("studentModel").get("email")));
        	else if(WebServiceUtil.DESCENDING_ORDER .equals(filterDto.getOrderType()))
        		cq.orderBy(cb.desc(markRoot.get("studentModel").get("email")));
			 
			 break;
		 
		 case WebServiceUtil.PHONE_NUMBER:
			 if(WebServiceUtil.ASCENDING_ORDER .equals(filterDto.getOrderType())) 
        		 cq.orderBy(cb.asc(markRoot.get("studentModel").get("phoneNumber")));
        	else if(WebServiceUtil.DESCENDING_ORDER .equals(filterDto.getOrderType()))
        		cq.orderBy(cb.desc(markRoot.get("studentModel").get("phoneNumber")));
			 break;
			 
		 case WebServiceUtil.DOB:
			 if(WebServiceUtil.ASCENDING_ORDER .equals(filterDto.getOrderType())) 
        		 cq.orderBy(cb.asc(markRoot.get("studentModel").get("dateOfBirth")));
        	else if(WebServiceUtil.DESCENDING_ORDER .equals(filterDto.getOrderType()))
        		cq.orderBy(cb.desc(markRoot.get("studentModel").get("dateOfBirth")));
			 break;
		 case WebServiceUtil.S_NO:
			 if(WebServiceUtil.ASCENDING_ORDER .equals(filterDto.getOrderType())) 
        		 cq.orderBy(cb.asc(markRoot.get("createDate")));
        	else if(WebServiceUtil.DESCENDING_ORDER .equals(filterDto.getOrderType()))
        		cq.orderBy(cb.desc(markRoot.get("createDate")));
			 break;
		default:
			if(WebServiceUtil.ASCENDING_ORDER .equals(filterDto.getOrderType())) 
       		 cq.orderBy(cb.asc(markRoot.get(filterDto.getOrderColumn())));
      	else if(WebServiceUtil.DESCENDING_ORDER .equals(filterDto.getOrderType()))
       		cq.orderBy(cb.desc(markRoot.get(filterDto.getOrderColumn())));	

		 }}
        
		// quarterAndYear and class od study filters
		predicates.add(cb.equal(markRoot.get("quarterAndYear"), filterDto.getQuarterAndYear()));
		predicates.add(cb.equal(markRoot.get("studentModel").get("classOfStudy"), filterDto.getClassOfStudy()));
		
		if (!predicates.isEmpty()) {
			cq.where(cb.and(predicates.toArray(new Predicate[0])));
		}

		return entityManager.createQuery(cq)
							.setFirstResult(filterDto.getStart())
							.setMaxResults(filterDto.getLength())
							.getResultList();
		
//		return null;
	}
	
	/**
	 * Retrieve the overall result summary for students in a given quarter and year.
	 */

	@Override
	public List<ResultReport> resultSummaryReport(CommonFilterDto filterDto) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		CriteriaQuery<ResultReport> totalResultCountQuery = cb.createQuery(ResultReport.class);

		Root<MarkModel> studentMarkRoot = totalResultCountQuery.from(MarkModel.class);

		Join<MarkModel, StudentModel> studentMarksAndStudent = studentMarkRoot.join("studentModel");

		Join<StudentModel, QuarterlyAttendanceModel> studentAndQuarterlyReportJoin = studentMarksAndStudent
				.join("quarterlyAttendanceReportModel");

		
//		Path<String> quarter = studentMark.get("quarterAndYear");
		
		
		Expression<Long> totalCount = cb.count(studentMarkRoot.get("studentModel").get("studentId"));

		
	   Expression<Integer> totalPass = cb
				.sum(cb.<Integer>selectCase().when(cb.equal(studentMarkRoot.get("result").get("code"), WebServiceUtil.PASS), 1).otherwise(0));
		
		
		Expression<Integer> totalFail = cb
				.sum(cb.<Integer>selectCase().when(cb.equal(studentMarkRoot.get("result").get("code"), WebServiceUtil.FAIL), 1).otherwise(0));

			
		Expression<Integer> failDueToAttendance = cb
		.sum(cb.<Integer>selectCase().when(cb.equal(studentAndQuarterlyReportJoin.get("attendanceComplianceStatus").get("code"), WebServiceUtil.NON_COMPLIANCE), 1).otherwise(0));
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if(filterDto.getQuarterAndYear()!=null)
		{
		Predicate quarterAndYear = cb.and(cb.equal(studentAndQuarterlyReportJoin.get("quarterAndYear"), filterDto.getQuarterAndYear()),
				cb.equal(studentMarkRoot.get("quarterAndYear"), filterDto.getQuarterAndYear()));
		predicates.add(quarterAndYear);
		}
		
		Predicate classOfStudy = cb.and(cb.equal(studentAndQuarterlyReportJoin.get("studentModel").get("classOfStudy"), filterDto.getClassOfStudy()),
				cb.equal(studentMarkRoot.get("studentModel").get("classOfStudy"), filterDto.getClassOfStudy()));
		
		predicates.add(classOfStudy);
		
		totalResultCountQuery.select(cb.construct(ResultReport.class,
				studentMarkRoot.get("quarterAndYear"), 
				studentMarkRoot.get("studentModel").get("classOfStudy"),
				totalCount.as(Integer.class),
				totalPass.as(Integer.class), 
				totalFail.as(Integer.class), 
				failDueToAttendance.as(Integer.class
						))).where(predicates.toArray(new Predicate[0])).groupBy(studentMarkRoot.get("quarterAndYear"));

		return entityManager.createQuery(totalResultCountQuery).getResultList();
	}
	
}
