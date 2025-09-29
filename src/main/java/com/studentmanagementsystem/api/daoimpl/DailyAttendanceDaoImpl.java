package com.studentmanagementsystem.api.daoimpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.studentmanagementsystem.api.dao.DailyAttendanceDao;
import com.studentmanagementsystem.api.model.custom.CommonFilterDto;
import com.studentmanagementsystem.api.model.custom.dailyattendance.DailyAttendanceDto;
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
	public Map<String,Object> attendanceList(CommonFilterDto filterDto) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<DailyAttendanceDto> cq = cb.createQuery(DailyAttendanceDto.class);
		Root<DailyAttendanceModel> dailyAttendanceRoot = cq.from(DailyAttendanceModel.class);
		
		 Map<String,Object> result = new HashMap<>();
		 List<Predicate> predicates = new ArrayList<Predicate>();
		 
		 //AttendanceDate And classOfStudy filter
		 predicates.add(cb.equal(dailyAttendanceRoot.get("studentModel").get("classOfStudy"),filterDto.getClassOfStudy()));
		 predicates.add(cb.equal(dailyAttendanceRoot.get("attendanceDate"),filterDto.getDate() ));
		 
		 //status filter
			if (filterDto.getStatus() != null && !filterDto.getStatus().isBlank()) {
				predicates
						.add(cb.equal(dailyAttendanceRoot.get("attendanceStatus").get("code"), filterDto.getStatus()));
			}
			
			// ApprovedStatus filter
			if (filterDto.getApprovedStatus() != null && !filterDto.getApprovedStatus().isBlank()) {
				if (WebServiceUtil.EXTRA_CURRICULAR_ACTIVITY_FILTER
						.equals(filterDto.getApprovedStatus().toLowerCase())) {
					predicates.add(cb.equal(dailyAttendanceRoot.get("approvedExtraCurricularActivitiesFlag"),
							WebServiceUtil.YES));
				} else if (WebServiceUtil.SICK_LEAVE_FILTER.equals(filterDto.getApprovedStatus().toLowerCase())) {
					predicates.add(cb.equal(dailyAttendanceRoot.get("longApprovedSickLeaveFlag"), WebServiceUtil.YES));
				}
			}

			// Search filters (name, email, phone)
			if (filterDto.getSearchBy() != null && filterDto.getSearchValue() != null && !filterDto.getSearchBy().isBlank() && !filterDto.getSearchValue().isBlank()) {

				switch (filterDto.getSearchBy().toLowerCase()) {
				case WebServiceUtil.NAME:
					Predicate fullName = cb.like(cb.lower(cb.concat(
							cb.concat(cb.concat(dailyAttendanceRoot.get("studentModel").get("firstName"), " "),
									cb.concat(cb.coalesce(
											dailyAttendanceRoot.get("studentModel").get("middleName"), ""),
											" ")),

							dailyAttendanceRoot.get("studentModel").get("lastName"))),
							 filterDto.getSearchValue().toLowerCase() + "%");

					predicates.add(fullName);
					break;

				case WebServiceUtil.EMAIL:
					
					predicates.add(cb.like(cb.lower(dailyAttendanceRoot.get("studentModel").get("email")),
							 filterDto.getSearchValue().toLowerCase() + "%"));
					break;

				case WebServiceUtil.PHONE_NUMBER:
					
					predicates.add( cb.like(dailyAttendanceRoot.get("studentModel").get("phoneNumber"),
							 filterDto.getSearchValue() + "%"));
					break;
				}
			}
				
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

							List<String> stuOrderColumnList = new ArrayList<>(
									Arrays.asList("firstName", "email", "phoneNumber", "dateOfBirth")
							);

							if (filterDto.getOrderColumn() != null && filterDto.getOrderType() != null && !filterDto.getOrderColumn().isBlank() && !filterDto.getOrderType().isBlank()) {

								if (stuOrderColumnList.contains(filterDto.getOrderColumn())) {

									if (WebServiceUtil.ASCENDING_ORDER.equals(filterDto.getOrderType()))
										cq.orderBy(cb.asc(dailyAttendanceRoot.get("studentModel")
												.get(filterDto.getOrderColumn())));
									else if (WebServiceUtil.DESCENDING_ORDER.equals(filterDto.getOrderType()))
										cq.orderBy(cb.desc(dailyAttendanceRoot.get("studentModel")
												.get(filterDto.getOrderColumn())));
								} else {
									if (WebServiceUtil.ASCENDING_ORDER.equals(filterDto.getOrderType()))
										cq.orderBy(cb.asc(dailyAttendanceRoot.get(filterDto.getOrderColumn())));
									else if (WebServiceUtil.DESCENDING_ORDER.equals(filterDto.getOrderType()))
										cq.orderBy(cb.desc(dailyAttendanceRoot.get(filterDto.getOrderColumn())));
								}

							}
				 
			 
		 
							if (!predicates.isEmpty()) {
								cq.where(cb.and(predicates.toArray(new Predicate[0])));
							}

							List<DailyAttendanceDto> attendanceList = entityManager.createQuery(cq)
									.setFirstResult(filterDto.getStart()).setMaxResults(filterDto.getLength())
									.getResultList();
							
							
							CriteriaQuery<Long> filterCountQuery = cb.createQuery(Long.class);
							Root<DailyAttendanceModel> filterCountRoot = filterCountQuery.from(DailyAttendanceModel.class);

							filterCountQuery.multiselect(cb.count(filterCountRoot)).where(cb.and(predicates.toArray(new Predicate[0])));

							Long filterCount = entityManager.createQuery(filterCountQuery).getSingleResult();
							 
							result.put("filterCount", filterCount);
							result.put("data", attendanceList);

			return result;
	}
			 


	/**
	 * Retrieve Monthly absence ,sick leave and extra acticity list in a given month.
	 */

	@Override
	public Map<String, Object> monthlyAttendanceList(CommonFilterDto filterDto) {

	    CriteriaBuilder cb = entityManager.getCriteriaBuilder();

	    // Total working days
		CriteriaQuery<Long> workingDaysQuery = cb.createQuery(Long.class);
		Root<DailyAttendanceModel> workingDaysRoot = workingDaysQuery.from(DailyAttendanceModel.class);

		Predicate monthCondition = cb.equal(cb.function("MONTH", Integer.class, workingDaysRoot.get("attendanceDate")),
				filterDto.getMonth());
		Predicate yearCondition = cb.equal(cb.function("YEAR", Integer.class, workingDaysRoot.get("attendanceDate")),
				filterDto.getYear());
		Predicate classCondition = cb.equal(workingDaysRoot.get("studentModel").get("classOfStudy"),
				filterDto.getClassOfStudy());

		workingDaysQuery.select(cb.countDistinct(workingDaysRoot.get("attendanceDate"))).where(monthCondition,
				yearCondition, classCondition);

		Long totalWorkingDays = entityManager.createQuery(workingDaysQuery).getSingleResult();

		// Student Attendance Query
		CriteriaQuery<Tuple> cq = cb.createTupleQuery();
		Root<DailyAttendanceModel> dailyAttendnaceRoot = cq.from(DailyAttendanceModel.class);
	//	Root<StudentModel> studentRoot = cq.from(StudentModel.class);
	//	Join<StudentModel, DailyAttendanceModel> dailyAttendnaceRoot = studentRoot.join("dailyAttendanceModel");
		
		Map<String, Object> result = new HashMap<>();
		List<Predicate> conditions = new ArrayList<>();
	    
		// Month, Year, Class filters
		conditions.add(cb.equal(dailyAttendnaceRoot.get("studentModel").get("classOfStudy"), filterDto.getClassOfStudy()));
		conditions.add(cb.equal(cb.function("MONTH", Integer.class, dailyAttendnaceRoot.get("attendanceDate")),
				filterDto.getMonth()));
		conditions.add(cb.equal(cb.function("YEAR", Integer.class, dailyAttendnaceRoot.get("attendanceDate")),
				filterDto.getYear()));
		
		// status filter
		if (filterDto.getStatus() != null && !filterDto.getStatus().isBlank()) {
			conditions.add(cb.equal(dailyAttendnaceRoot.get("attendanceStatus").get("code"), filterDto.getStatus()));
		}
	    
		// Approvedstatus filters
		if (filterDto.getApprovedStatus() != null && !filterDto.getApprovedStatus().isBlank()) {

			if (WebServiceUtil.SICK_LEAVE_FILTER.equals(filterDto.getApprovedStatus().toLowerCase())) {
				conditions.add(cb.equal(dailyAttendnaceRoot.get(WebServiceUtil.SICK_LEAVE), WebServiceUtil.YES));
			} else if (WebServiceUtil.EXTRA_CURRICULAR_ACTIVITY_FILTER
					.equals(filterDto.getApprovedStatus().toLowerCase())) {
				conditions.add(cb.equal(dailyAttendnaceRoot.get(WebServiceUtil.EXTRA_CURRICULAR_ACTIVITIES),
						WebServiceUtil.YES));
			}
		}
	    
		// Search filters (name, email, phone)
		if (filterDto.getSearchBy() != null && filterDto.getSearchValue() != null && !filterDto.getSearchBy().isBlank() && !filterDto.getSearchValue().isBlank() ) {
			switch (filterDto.getSearchBy().toLowerCase()) {
			case WebServiceUtil.NAME:
				Predicate fullName = cb.like(
						cb.lower(
								cb.concat(
										cb.concat(cb.concat(dailyAttendnaceRoot.get("studentModel").get("firstName"), " "),
												cb.concat(cb.coalesce(dailyAttendnaceRoot.get("studentModel").get("middleName"),
														""), " ")),

										dailyAttendnaceRoot.get("studentModel").get("lastName"))),
						 filterDto.getSearchValue().toLowerCase() + "%");

				conditions.add(fullName);
				break;
			case WebServiceUtil.EMAIL:
				conditions.add(cb.like(cb.lower(dailyAttendnaceRoot.get("studentModel").get("email")),
						 filterDto.getSearchValue().toLowerCase() + "%"));
				break;
			case WebServiceUtil.PHONE_NUMBER:
				conditions.add(cb.like(dailyAttendnaceRoot.get("studentModel").get("phoneNumber"), filterDto.getSearchValue() + "%"));
				break;
			}
		}
	    
		Expression<Long> attendanceCount = cb.countDistinct(dailyAttendnaceRoot.get("attendanceDate"));
	    
	  
	 
	    // Group by
		cq.groupBy(dailyAttendnaceRoot.get("studentModel").get("studentId"));

	    //  HAVING condition
		if (filterDto.getOperationBy() != null && filterDto.getOperationValue() != null) {

			Long countValue = filterDto.getOperationValue();
			Predicate havingPredicate = null;

			switch (filterDto.getOperationBy().toLowerCase()) {
			case WebServiceUtil.GREATER_THAN:
				havingPredicate = cb.greaterThan(attendanceCount, countValue);
				break;
			case WebServiceUtil.LESS_THAN:
				havingPredicate = cb.lessThan(attendanceCount, countValue);
				break;
			case WebServiceUtil.EQUAL_TO:
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

		// Sorting filter
		if (filterDto.getOrderColumn() != null && filterDto.getOrderType() != null && !filterDto.getOrderColumn().isBlank() && !filterDto.getOrderType().isBlank()) {
			if (WebServiceUtil.ASCENDING_ORDER.equals(filterDto.getOrderType()))
				cq.orderBy(cb.asc(dailyAttendnaceRoot.get("studentModel").get(filterDto.getOrderColumn())));
			else if (WebServiceUtil.DESCENDING_ORDER.equals(filterDto.getOrderType()))
				cq.orderBy(cb.desc(dailyAttendnaceRoot.get("studentModel").get(filterDto.getOrderColumn())));
		}
		
		  //  Select fields 
		   cq.multiselect(
				   dailyAttendnaceRoot.get("studentModel").get("studentId").alias("studentId"),
		            cb.concat(
		                    cb.concat(
		                            cb.concat(dailyAttendnaceRoot.get("studentModel").get("firstName"), " "),
		                            cb.concat(cb.coalesce(dailyAttendnaceRoot.get("studentModel").get("middleName"), ""), " ")
		                    ),
		                    dailyAttendnaceRoot.get("studentModel").get("lastName")
		            ).alias("name"),
		            dailyAttendnaceRoot.get("studentModel").get("classOfStudy").alias("classOfStudy"),
		            dailyAttendnaceRoot.get("studentModel").get("dateOfBirth").alias("dateOfBirth"),
		            dailyAttendnaceRoot.get("studentModel").get("email").alias("email"),
		            dailyAttendnaceRoot.get("studentModel").get("phoneNumber").alias("phoneNumber"),
		            attendanceCount.alias("attendanceCount")
		    ).where(conditions.toArray(new Predicate[0]));


	    List<Tuple> results = entityManager.createQuery(cq)
				            .setFirstResult(filterDto.getStart())
				            .setMaxResults(filterDto.getLength())
				            .getResultList();

		List<MonthlyAbsenceDto> monthlyAttendanceList = new ArrayList<>();
	    for (Tuple tuple : results) {
	        Long studentId = tuple.get("studentId", Long.class);
	        
	        //Get attendance date
	        StringBuilder query = new StringBuilder(
	            "SELECT d.attendanceDate FROM DailyAttendanceModel d " +
	            "WHERE d.studentModel.studentId = :studentId " +
	            "AND MONTH(d.attendanceDate) = :month " +
	            "AND YEAR(d.attendanceDate) = :year "
	        );

			if (WebServiceUtil.PRESENT.equals(filterDto.getStatus())) {
				query.append("AND d.attendanceStatus = 'PRESENT' ");
			} else if (WebServiceUtil.ABSENT.equals(filterDto.getStatus())) {
				query.append("AND d.attendanceStatus = 'ABSENT' ");
			}

			if (WebServiceUtil.SICK_LEAVE_FILTER.equals(filterDto.getApprovedStatus())) {
				query.append("AND d.longApprovedSickLeaveFlag = 'Y' ");
			} else if (WebServiceUtil.EXTRA_CURRICULAR_ACTIVITY_FILTER.equals(filterDto.getApprovedStatus())) {
				query.append("AND d.approvedExtraCurricularActivitiesFlag = 'Y' ");
			}

	        List<LocalDate> dates = entityManager.createQuery(query.toString(), LocalDate.class)
	                .setParameter("studentId", studentId)
	                .setParameter("month", filterDto.getMonth())
	                .setParameter("year", filterDto.getYear())
	                .getResultList();

	        monthlyAttendanceList.add(new MonthlyAbsenceDto(
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
	    
		CriteriaQuery<Long> filterCountQuery = cb.createQuery(Long.class);
		Root<DailyAttendanceModel> filterCountRoot = filterCountQuery.from(DailyAttendanceModel.class);
	//	Join<StudentModel, DailyAttendanceModel> filterCountJoin = filterCountRoot.join("dailyAttendanceModel");

		filterCountQuery.multiselect(cb.count(filterCountRoot)).where(cb.and(conditions.toArray(new Predicate[0])));

		Long filterCount = entityManager.createQuery(filterCountQuery).getSingleResult();
		 
		result.put("filterCount", filterCount);
		result.put("data", monthlyAttendanceList);
		
	    return result;
	}
}
