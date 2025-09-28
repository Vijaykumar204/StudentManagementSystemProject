package com.studentmanagementsystem.api.daoimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.studentmanagementsystem.api.dao.MarkDao;
import com.studentmanagementsystem.api.model.custom.studentmarks.markDto;
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
	public Map<String,Object>  listStudentMarks(CommonFilterDto filterDto) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<markDto> cq = cb.createQuery(markDto.class);
		Root<MarkModel> markRoot = cq.from(MarkModel.class);

		Subquery<Integer> sq = cq.subquery(Integer.class);
		Root<MarkModel> sqMarkRoot = sq.from(MarkModel.class);
	    
		Map<String,Object> result = new HashMap<>();
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		// quarterAndYear and class od study filters
		predicates.add(cb.equal(markRoot.get("studentModel").get("classOfStudy"), filterDto.getClassOfStudy()));
		predicates.add(cb.equal(markRoot.get("quarterAndYear"), filterDto.getQuarterAndYear()));
		
		
		// Result status filter
		if (filterDto.getStatus() != null && filterDto.getTopper() == null) {

			if (filterDto.getStatus().equals(WebServiceUtil.PASS)) {
				predicates.add(cb.equal(markRoot.get("result").get("code"), WebServiceUtil.PASS));
			} else if (filterDto.getStatus().equals(WebServiceUtil.FAIL)) {
				predicates.add(cb.equal(markRoot.get("result").get("code"), WebServiceUtil.FAIL));
			}
		} else {
			if (WebServiceUtil.TOPPER.equals(filterDto.getTopper())) {
				sq.select(cb.max(sqMarkRoot.get("totalMarks"))).where(
						cb.equal(sqMarkRoot.get("quarterAndYear"), filterDto.getQuarterAndYear()),
						cb.equal(sqMarkRoot.get("studentModel").get("classOfStudy"), filterDto.getClassOfStudy()),
						cb.equal(sqMarkRoot.get("result").get("code"), WebServiceUtil.PASS));
				predicates.add(cb.equal(markRoot.get("totalMarks"), sq));
			}
		}
		
		//Name , email ,phoneNumber
		if (filterDto.getSearchBy() != null && filterDto.getSearchValue() != null && filterDto.getTopper() == null ) {

			switch (filterDto.getSearchBy().toLowerCase()) {
			case WebServiceUtil.NAME:
				Predicate fullName = cb.like(
						cb.lower(cb.concat(
								cb.concat(cb.concat(markRoot.get("studentModel").get("firstName"), " "),
										cb.concat(cb.coalesce(markRoot.get("studentModel").get("middleName"), ""),
												" ")),

								markRoot.get("studentModel").get("lastName"))),
						filterDto.getSearchValue().toLowerCase() + "%");

				predicates.add(fullName);
				break;

			case WebServiceUtil.EMAIL:
				predicates.add(cb.like(cb.lower(markRoot.get("studentModel").get("email")),
						 filterDto.getSearchValue().toLowerCase() + "%"));
				break;

			case WebServiceUtil.PHONE_NUMBER:
				predicates.add(cb.like(markRoot.get("studentModel").get("phoneNumber"),
						 filterDto.getSearchValue() + "%"));
				break;
			}
		}
       
		cq.select(cb.construct(markDto.class,
				markRoot.get("studentModel").get("studentId"),
				cb.concat(
						cb.concat(cb.concat(markRoot.get("studentModel").get("firstName"), " "),
								cb.concat(cb.coalesce(markRoot.get("studentModel").get("middleName"), ""), " ")),
						markRoot.get("studentModel").get("lastName")),
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
		
		if (!predicates.isEmpty()) {
			cq.where(cb.and(predicates.toArray(new Predicate[0])));
		}

		List<String> stuOrderColumnList = new ArrayList<>(
				Arrays.asList("firstName", "email", "phoneNumber", "dateOfBirth"));
		
		if (filterDto.getOrderColumn() != null && filterDto.getOrderType() != null) {

			if (stuOrderColumnList.contains(filterDto.getOrderColumn())) {

				if (WebServiceUtil.ASCENDING_ORDER.equals(filterDto.getOrderType()))
					cq.orderBy(cb.asc(markRoot.get("studentModel").get(filterDto.getOrderColumn())));
				else if (WebServiceUtil.DESCENDING_ORDER.equals(filterDto.getOrderType()))
					cq.orderBy(cb.desc(markRoot.get("studentModel").get(filterDto.getOrderColumn())));
			} else {
				if (WebServiceUtil.ASCENDING_ORDER.equals(filterDto.getOrderType()))
					cq.orderBy(cb.asc(markRoot.get(filterDto.getOrderColumn())));
				else if (WebServiceUtil.DESCENDING_ORDER.equals(filterDto.getOrderType()))
					cq.orderBy(cb.desc(markRoot.get(filterDto.getOrderColumn())));
			}

		}

		List<markDto> markList= entityManager.createQuery(cq)
										.setFirstResult(filterDto.getStart())
										.setMaxResults(filterDto.getLength())
										.getResultList();
		
		CriteriaQuery<Long> filterCountQuery = cb.createQuery(Long.class);
		Root<MarkModel> filterCountRoot = filterCountQuery.from(MarkModel.class);

		filterCountQuery.multiselect(cb.count(filterCountRoot)).where(cb.and(predicates.toArray(new Predicate[0])));

		Long filterCount = entityManager.createQuery(filterCountQuery).getSingleResult();
		 
		result.put("filterCount", filterCount);
		result.put("data", markList);
		
	return result;
	}
	
	/**
	 * Retrieve the overall result summary for students in a given quarter and year.
	 */

	@Override
	public List<ResultReport> resultSummaryReport(CommonFilterDto filterDto) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		CriteriaQuery<ResultReport> cq = cb.createQuery(ResultReport.class);

		Root<MarkModel> studentMarkRoot = cq.from(MarkModel.class);

		Join<MarkModel, StudentModel> studentMarksAndStudent = studentMarkRoot.join("studentModel");

		Join<StudentModel, QuarterlyAttendanceModel> studentAndQuarterlyReportJoin = studentMarksAndStudent
				.join("quarterlyAttendanceReportModel");

		Expression<Long> totalCount = cb.count(studentMarkRoot.get("studentModel").get("studentId"));

		Expression<Integer> totalPass = cb.sum(cb.<Integer>selectCase()
				.when(cb.equal(studentMarkRoot.get("result").get("code"), WebServiceUtil.PASS), 1).otherwise(0));

		Expression<Integer> totalFail = cb.sum(cb.<Integer>selectCase()
				.when(cb.equal(studentMarkRoot.get("result").get("code"), WebServiceUtil.FAIL), 1).otherwise(0));

		Expression<Integer> failDueToAttendance = cb.sum(cb.<Integer>selectCase()
				.when(cb.equal(studentAndQuarterlyReportJoin.get("attendanceComplianceStatus").get("code"),
						WebServiceUtil.NON_COMPLIANCE), 1)
				.otherwise(0));
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		// Class of study filter
		predicates.add(cb.and(
				cb.equal(studentAndQuarterlyReportJoin.get("studentModel").get("classOfStudy"),
						filterDto.getClassOfStudy()),
				cb.equal(studentMarkRoot.get("studentModel").get("classOfStudy"), filterDto.getClassOfStudy())));
		
		//Quarter filter
		if (filterDto.getQuarterAndYear() != null) {
			predicates.add(
					cb.and(cb.equal(studentAndQuarterlyReportJoin.get("quarterAndYear"), filterDto.getQuarterAndYear()),
							cb.equal(studentMarkRoot.get("quarterAndYear"), filterDto.getQuarterAndYear())));
		}
		

		cq.select(cb.construct(ResultReport.class,
				studentMarkRoot.get("quarterAndYear"),
				studentMarkRoot.get("studentModel").get("classOfStudy"),
				totalCount.as(Integer.class),
				totalPass.as(Integer.class),
				totalFail.as(Integer.class), 
				failDueToAttendance.as(Integer.class)
						)).where(predicates.toArray(new Predicate[0])).groupBy(studentMarkRoot.get("quarterAndYear"));

		return entityManager.createQuery(cq).getResultList();
	}
	
}
