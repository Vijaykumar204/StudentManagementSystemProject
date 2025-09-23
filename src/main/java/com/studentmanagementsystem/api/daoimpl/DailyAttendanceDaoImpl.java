package com.studentmanagementsystem.api.daoimpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.studentmanagementsystem.api.dao.DailyAttendanceDao;
import com.studentmanagementsystem.api.model.custom.dailyattendance.DailyAttendanceDto;
import com.studentmanagementsystem.api.model.custom.dailyattendance.DailyAttendanceFilterDto;
import com.studentmanagementsystem.api.model.custom.dailyattendance.MonthlyAbsenceDto;
import com.studentmanagementsystem.api.model.entity.DailyAttendanceModel;
import com.studentmanagementsystem.api.model.entity.StudentModel;
import com.studentmanagementsystem.api.util.WebServiceUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class DailyAttendanceDaoImpl implements DailyAttendanceDao {

	@Autowired
	private EntityManager entityManager;
	

	/**
	   * Retrieve student attendance for a particular date to attendance taken.
	 */
	@Override
	public List<DailyAttendanceDto> listAttendance(DailyAttendanceFilterDto dailyAttendanceFilterDto) {
		return attendanceList(dailyAttendanceFilterDto);
	}





	/**
       * Retrieve student attendance for a particular date to attendance taken.
	 */
	@Override
	public List<DailyAttendanceDto> attendanceList(DailyAttendanceFilterDto dailyAttendanceFilterDto) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<DailyAttendanceDto> cq=cb.createQuery(DailyAttendanceDto.class);
		Root<DailyAttendanceModel> dailyAttendanceRoot = cq.from(DailyAttendanceModel.class);
				
		 cq.select(cb.construct(DailyAttendanceDto.class,
				 
				 
				 dailyAttendanceRoot.get("studentModel").get("studentId"),
				 cb.concat(
			                cb.concat(
			                        cb.concat(cb.coalesce(dailyAttendanceRoot.get("studentModel").get("firstName"), ""), " "),
			                        cb.concat(cb.coalesce(dailyAttendanceRoot.get("studentModel").get("middleName"), ""), " ")
			                ),
			                cb.coalesce(dailyAttendanceRoot.get("studentModel").get("lastName"), "")
			        ),
				 dailyAttendanceRoot.get("studentModel").get("classOfStudy"),
				 dailyAttendanceRoot.get("studentModel").get("dateOfBirth"),
				 dailyAttendanceRoot.get("studentModel").get("email"),
				 dailyAttendanceRoot.get("studentModel").get("phoneNumber"),
				 dailyAttendanceRoot.get("attendanceDate"),
				 dailyAttendanceRoot.get("attendanceStatus").get("code"),
				 dailyAttendanceRoot.get("longApprovedSickLeaveFlag"),
				 dailyAttendanceRoot.get("approvedExtraCurricularActivitiesFlag")
			
				 
				
	    		
	    		 ));
		 
		 List<Predicate> predicates = new ArrayList<Predicate>();
		 
		// Search filters (name, email, phone)
		  if(dailyAttendanceFilterDto.getSearchBy()!=null && dailyAttendanceFilterDto.getSearchValue()!=null) {
			  
		        switch(dailyAttendanceFilterDto.getSearchBy().toLowerCase())
		        {
		        case WebServiceUtil.NAME:
		        	 Predicate fullName = cb.like(
		        			 cb.concat(
		 			                cb.concat(
		 			                        cb.concat(cb.coalesce(dailyAttendanceRoot.get("studentModel").get("firstName"), ""), " "),
		 			                        cb.concat(cb.coalesce(dailyAttendanceRoot.get("studentModel").get("middleName"), ""), " ")
		 			                ),
		 			                
		 			                cb.coalesce(dailyAttendanceRoot.get("studentModel").get("lastName"), "")
		 			        ),
		                    "%" + dailyAttendanceFilterDto.getSearchValue() + "%"
		            );
		        	
		        	predicates.add(fullName);
		        	break;
		        	
		        case WebServiceUtil.EMAIL:
		        	Predicate email =cb.like(cb.lower(dailyAttendanceRoot.get("studentModel").get("email")) ,"%"+ dailyAttendanceFilterDto.getSearchValue().toLowerCase() + "%");
					predicates.add(email);
					break;
					
		        case WebServiceUtil.PHONENUMBER:
		        	Predicate phoneNumber = cb.like(dailyAttendanceRoot.get("studentModel").get("phoneNumber"),"%"+ dailyAttendanceFilterDto.getSearchValue() + "%");
					predicates.add(phoneNumber);
					break;
		        }
		        }
		 
		   //status filter
		   if (WebServiceUtil.PRESENT.equals(dailyAttendanceFilterDto.getStatus())) {
			  predicates.add(cb.equal(dailyAttendanceRoot.get("attendanceStatus").get("code"), dailyAttendanceFilterDto.getStatus()));
		    } else if (WebServiceUtil.ABSENT.equals(dailyAttendanceFilterDto.getStatus())) {
		    	predicates.add(cb.equal(dailyAttendanceRoot.get("attendanceStatus").get("code"), WebServiceUtil.ABSENT));
		    }
		   
		 // ApprovedStatus filter
		 if(WebServiceUtil.EXTRA_CURRICULAR_ACTIVITY_FILTER .equals(dailyAttendanceFilterDto.getApprovedStatus()) ) {
			 predicates.add(cb.equal(dailyAttendanceRoot.get("approvedExtraCurricularActivitiesFlag"), WebServiceUtil.YES));
		 }
		 else if(WebServiceUtil.SICK_LEAVE_FILTER.equals(dailyAttendanceFilterDto.getApprovedStatus())) {
			 predicates.add(cb.equal(dailyAttendanceRoot.get("longApprovedSickLeaveFlag"), WebServiceUtil.YES));
		 }
		 
		 // Sorting filter
		 if(dailyAttendanceFilterDto.getSortingBy()!=null && dailyAttendanceFilterDto.getSortingOrder() !=null) {
	        	if(WebServiceUtil.ASCENDING_ORDER .equals(dailyAttendanceFilterDto.getSortingOrder())) 
	        		 cq.orderBy(cb.asc(dailyAttendanceRoot.get(dailyAttendanceFilterDto.getSortingBy())));
	        	else if(WebServiceUtil.DESCENDING_ORDER .equals(dailyAttendanceFilterDto.getSortingOrder()))
	        		cq.orderBy(cb.desc(dailyAttendanceRoot.get(dailyAttendanceFilterDto.getSortingBy())));	
	        }
		 
		 //AttendanceDate And classOfStudy filter
		 predicates.add(cb.equal(dailyAttendanceRoot.get("attendanceDate"),dailyAttendanceFilterDto.getDate() ));
		 predicates.add(cb.equal(dailyAttendanceRoot.get("studentModel").get("classOfStudy"),dailyAttendanceFilterDto.getClassOfStudy() ));

		 
		 if (!predicates.isEmpty()) {
			 cq.where(cb.and(predicates.toArray(new Predicate[0])));
			}

			return entityManager.createQuery(cq)
					.setFirstResult(dailyAttendanceFilterDto.getStart())
					.setMaxResults(dailyAttendanceFilterDto.getLength())
					.getResultList();
	}
	

	

	
	/**
	 * Retrieve Monthly absence ,sick leave and extra acticity list in a given month.
	 */
	
	@Override
	public List<MonthlyAbsenceDto> getMonthlyAbsenceStudents(DailyAttendanceFilterDto dailyAttendanceFilterDto) {
		return monthlyAttendanceList(dailyAttendanceFilterDto);
	}





	/**
	 * Retrieve Monthly absence ,sick leave and extra acticity list in a given month.
	 */

	@Override
	public List<MonthlyAbsenceDto> monthlyAttendanceList(DailyAttendanceFilterDto dailyAttendanceFilterDto) {

	    CriteriaBuilder cb = entityManager.getCriteriaBuilder();

	    // Total working days
	    CriteriaQuery<Long> workingDaysQuery = cb.createQuery(Long.class);
	    Root<DailyAttendanceModel> workingDaysRoot = workingDaysQuery.from(DailyAttendanceModel.class);
	    Predicate monthCondition = cb.equal(cb.function("MONTH", Integer.class, workingDaysRoot.get("attendanceDate")), dailyAttendanceFilterDto.getMonth());
	    Predicate yearCondition = cb.equal(cb.function("YEAR", Integer.class, workingDaysRoot.get("attendanceDate")), dailyAttendanceFilterDto.getYear());
	    Predicate classCondition = cb.equal(workingDaysRoot.get("studentModel").get("classOfStudy"), dailyAttendanceFilterDto.getClassOfStudy());

	    workingDaysQuery.select(cb.countDistinct(workingDaysRoot.get("attendanceDate")))
	            .where(monthCondition, yearCondition, classCondition);

	    Long totalWorkingDays = entityManager.createQuery(workingDaysQuery).getSingleResult();

	    //Student Attendance Query
	    CriteriaQuery<Tuple> cq = cb.createTupleQuery();
	    Root<StudentModel> studentRoot = cq.from(StudentModel.class);
	    Join<StudentModel, DailyAttendanceModel> dailyAttendanceJoin = studentRoot.join("dailyAttendanceModel");

	    List<Predicate> conditions = new ArrayList<>();

	    // Search filters (name, email, phone)
	    if(dailyAttendanceFilterDto.getSearchBy() != null && dailyAttendanceFilterDto.getSearchValue() != null) {
	        switch(dailyAttendanceFilterDto.getSearchBy().toLowerCase()) {
	            case WebServiceUtil.NAME:
	                Predicate fullName = cb.like(
	                        cb.concat(
	                                cb.concat(
	                                        cb.concat(cb.coalesce(studentRoot.get("firstName"), ""), " "),
	                                        cb.concat(cb.coalesce(studentRoot.get("middleName"), ""), " ")
	                                ),
	                                cb.coalesce(studentRoot.get("lastName"), "")
	                        ),
	                        "%" + dailyAttendanceFilterDto.getSearchValue() + "%"
	                );
	                conditions.add(fullName);
	                break;
	            case WebServiceUtil.EMAIL:
	                conditions.add(cb.like(cb.lower(studentRoot.get("email")), "%" + dailyAttendanceFilterDto.getSearchValue().toLowerCase() + "%"));
	                break;
	            case WebServiceUtil.PHONENUMBER:
	                conditions.add(cb.like(studentRoot.get("phoneNumber"), "%" + dailyAttendanceFilterDto.getSearchValue() + "%"));
	                break;
	        }
	    }

	    // Month, Year, Class filters
	    conditions.add(cb.equal(cb.function("MONTH", Integer.class, dailyAttendanceJoin.get("attendanceDate")), dailyAttendanceFilterDto.getMonth()));
	    conditions.add(cb.equal(cb.function("YEAR", Integer.class, dailyAttendanceJoin.get("attendanceDate")), dailyAttendanceFilterDto.getYear()));
	    conditions.add(cb.equal(studentRoot.get("classOfStudy"), dailyAttendanceFilterDto.getClassOfStudy()));

	    // Approvedstatus filters
	    if (WebServiceUtil.SICK_LEAVE_FILTER.equals(dailyAttendanceFilterDto.getApprovedStatus())) {
	        conditions.add(cb.equal(dailyAttendanceJoin.get(WebServiceUtil.SICK_LEAVE), WebServiceUtil.YES));
	    } else if (WebServiceUtil.EXTRA_CURRICULAR_ACTIVITY_FILTER.equals(dailyAttendanceFilterDto.getApprovedStatus())) {
	        conditions.add(cb.equal(dailyAttendanceJoin.get(WebServiceUtil.EXTRA_CURRICULAR_ACTIVITIES), WebServiceUtil.YES));
	    }
	    //status filter
	    if (WebServiceUtil.PRESENT.equals(dailyAttendanceFilterDto.getStatus())) {
	        conditions.add(cb.equal(dailyAttendanceJoin.get("attendanceStatus").get("code"), dailyAttendanceFilterDto.getStatus()));
	    } else if (WebServiceUtil.ABSENT.equals(dailyAttendanceFilterDto.getStatus())) {
	        conditions.add(cb.equal(dailyAttendanceJoin.get("attendanceStatus").get("code"), WebServiceUtil.ABSENT));
	    }
	    
	    // Sorting filter
		 if(dailyAttendanceFilterDto.getSortingBy()!=null && dailyAttendanceFilterDto.getSortingOrder() !=null) {
	        	if(WebServiceUtil.ASCENDING_ORDER.equals(dailyAttendanceFilterDto.getSortingOrder())) 
	        		 cq.orderBy(cb.asc(studentRoot.get(dailyAttendanceFilterDto.getSortingBy())));
	        	else if(WebServiceUtil.DESCENDING_ORDER.equals(dailyAttendanceFilterDto.getSortingOrder()))
	        		cq.orderBy(cb.desc(studentRoot.get(dailyAttendanceFilterDto.getSortingBy())));	
	        }

	    // Group by and Count ---
	    Expression<Long> attendanceCount = cb.countDistinct(dailyAttendanceJoin.get("attendanceDate"));

	    cq.groupBy(
	            studentRoot.get("studentId")
	    );

	    //  HAVING condition
	    if(dailyAttendanceFilterDto.getOperationBy() != null && dailyAttendanceFilterDto.getOperationValue() != null) {
	    	
	    	System.out.println("Entering the having");
	        Long countValue = dailyAttendanceFilterDto.getOperationValue();
	        Predicate havingPredicate = null;

	        switch(dailyAttendanceFilterDto.getOperationBy().toLowerCase()) {
	            case WebServiceUtil.GREATER_THAN:
	                havingPredicate = cb.greaterThan(attendanceCount, countValue);
	                break;
	            case WebServiceUtil.LESS_THAN:
	                havingPredicate = cb.lessThan(attendanceCount, countValue);
	                break;
	            case WebServiceUtil.EQUAL_TO:
	            	System.out.println("==");
	                havingPredicate = cb.equal(attendanceCount, countValue);
	                break;
	            case WebServiceUtil.GREATER_THAN_EQUAL_TO:
	                havingPredicate = cb.greaterThanOrEqualTo(attendanceCount, countValue);
	                break;
	            case WebServiceUtil.LESS_THAN_EQAUL_TO:
	                havingPredicate = cb.lessThanOrEqualTo(attendanceCount, countValue);
	                break;
	        }

	        if (havingPredicate != null) {
	            cq.having(havingPredicate);
	        }
	    }

	    //  Select fields 
	    cq.multiselect(
	            studentRoot.get("studentId").alias("studentId"),
	            cb.concat(
	                    cb.concat(
	                            cb.concat(cb.coalesce(studentRoot.get("firstName"), ""), " "),
	                            cb.concat(cb.coalesce(studentRoot.get("middleName"), ""), " ")
	                    ),
	                    cb.coalesce(studentRoot.get("lastName"), "")
	            ).alias("name"),
	            studentRoot.get("classOfStudy").alias("classOfStudy"),
	            studentRoot.get("dateOfBirth").alias("dateOfBirth"),
	            studentRoot.get("email").alias("email"),
	            studentRoot.get("phoneNumber").alias("phoneNumber"),
	            attendanceCount.alias("attendanceCount")
	    ).where(conditions.toArray(new Predicate[0]));

	   
	    List<Tuple> results = entityManager.createQuery(cq)
	            .setFirstResult(dailyAttendanceFilterDto.getStart())
	            .setMaxResults(dailyAttendanceFilterDto.getLength())
	            .getResultList();

	    
	    List<MonthlyAbsenceDto> monthlyAbsenceList = new ArrayList<>();
	    for (Tuple tuple : results) {
	        Long studentId = tuple.get("studentId", Long.class);
	        
	        //Get attendance date
	        StringBuilder query = new StringBuilder(
	            "SELECT d.attendanceDate FROM DailyAttendanceModel d " +
	            "WHERE d.studentModel.studentId = :studentId " +
	            "AND MONTH(d.attendanceDate) = :month " +
	            "AND YEAR(d.attendanceDate) = :year "
	        );

	        if (WebServiceUtil.PRESENT.equals(dailyAttendanceFilterDto.getStatus())) {
	            query.append("AND d.attendanceStatus = 'PRESENT' ");
	        } else if (WebServiceUtil.ABSENT.equals(dailyAttendanceFilterDto.getStatus())) {
	            query.append("AND d.attendanceStatus = 'ABSENT' ");
	        }

	        if (WebServiceUtil.SICK_LEAVE_FILTER.equals(dailyAttendanceFilterDto.getApprovedStatus())) {
	            query.append("AND d.longApprovedSickLeaveFlag = 'Y' ");
	        } else if (WebServiceUtil.EXTRA_CURRICULAR_ACTIVITY_FILTER.equals(dailyAttendanceFilterDto.getApprovedStatus())) {
	            query.append("AND d.approvedExtraCurricularActivitiesFlag = 'Y' ");
	        }

	        List<LocalDate> dates = entityManager.createQuery(query.toString(), LocalDate.class)
	                .setParameter("studentId", studentId)
	                .setParameter("month", dailyAttendanceFilterDto.getMonth())
	                .setParameter("year", dailyAttendanceFilterDto.getYear())
	                .getResultList();

	        monthlyAbsenceList.add(new MonthlyAbsenceDto(
	                studentId,
	                tuple.get("name", String.class),
	                tuple.get("classOfStudy", Integer.class),
	                tuple.get("dateOfBirth", LocalDate.class),
	                tuple.get("email", String.class),
	                tuple.get("phoneNumber", String.class),
	                totalWorkingDays,
	                tuple.get("attendanceCount", Long.class).longValue(),
	                dates
	        ));
	    }

	    return monthlyAbsenceList;
	}
}
