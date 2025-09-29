package com.studentmanagementsystem.api.daoimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.studentmanagementsystem.api.dao.StudentDao;
import com.studentmanagementsystem.api.model.custom.CommonFilterDto;
import com.studentmanagementsystem.api.model.custom.student.StudentDto;
import com.studentmanagementsystem.api.model.entity.StudentModel;
import com.studentmanagementsystem.api.util.WebServiceUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class StudentDaoImpl implements StudentDao {

	@Autowired
	private EntityManager entityManager;

	/**
	 * Retrive list of student details
	 */

	@Override
	public Map<String, Object>  listStudentDetails(CommonFilterDto filterDto) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<StudentDto> cq = cb.createQuery(StudentDto.class);
		Root<StudentModel> studentRoot = cq.from(StudentModel.class);

		Map<String, Object> result = new HashMap<>();
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		//Class of study filter
		if (filterDto.getClassOfStudy() != null) {
			predicates.add(cb.equal(studentRoot.get("classOfStudy"), filterDto.getClassOfStudy()));
		}
		
        //Status filter
		if (filterDto.getStatus() != null && !filterDto.getStatus().isBlank()) {
			predicates.add(cb.equal(studentRoot.get("status").get("code"), filterDto.getStatus()));
		}
		
       
		if (filterDto.getGender() != null && !filterDto.getGender().isBlank()) {
			predicates.add(cb.equal(studentRoot.get("gender").get("code"), filterDto.getGender()));
		}
		
		 //Residing status filter
		if (filterDto.getResidingStatus() != null && !filterDto.getResidingStatus().isBlank()) {
			predicates.add(cb.equal(studentRoot.get("residingStatus").get("code"), filterDto.getResidingStatus()));
		}
		
		// Name , email , phone number filter
		if (filterDto.getSearchBy() != null && filterDto.getSearchValue() != null && !filterDto.getSearchBy().isBlank()
				&& !filterDto.getSearchValue().isBlank()) {
			switch (filterDto.getSearchBy().toLowerCase()) {

			case WebServiceUtil.NAME:

				predicates.add(cb.like(cb.lower(cb.concat(
						cb.concat(cb.concat(studentRoot.get("firstName"), " "),
								cb.concat(cb.coalesce(studentRoot.get("middleName"), ""), " ")),
						studentRoot.get("lastName"))), filterDto.getSearchValue().toLowerCase() + "%"));
				break;

			case WebServiceUtil.EMAIL:
				predicates.add(
						cb.like(cb.lower(studentRoot.get("email")), filterDto.getSearchValue().toLowerCase() + "%"));
				break;

			case WebServiceUtil.PHONE_NUMBER:
				predicates.add(cb.like(studentRoot.get("phoneNumber"), filterDto.getSearchValue() + "%"));
				break;

			case WebServiceUtil.ID:
				predicates.add(cb.equal(studentRoot.get("studentId"), filterDto.getSearchValue()));
				break;

			}
		}
        
			cq.select(cb.construct(StudentDto.class, 
						
						studentRoot.get("studentId"),   
					cb.concat(
							cb.concat(cb.concat(studentRoot.get("firstName"), " "),
									cb.concat(cb.coalesce(studentRoot.get("middleName"), ""), " ")),
							studentRoot.get(
									"lastName")),
						studentRoot.get("gender").get("description"),
						studentRoot.get("dateOfBirth"),
						studentRoot.get("classOfStudy"),
						studentRoot.get("residingStatus").get("description"),
						studentRoot.get("phoneNumber"),
						studentRoot.get("parentsName"),
						studentRoot.get("homeStreetName"),
						studentRoot.get("homeCityName"), 
						studentRoot.get("homePostalCode"),
						studentRoot.get("status").get("description"),
						studentRoot.get("email"),
						studentRoot.get("parentsEmail"),
						studentRoot.get("firstName"),
						studentRoot.get("middleName"),
						studentRoot.get("lastName")
						
				));
        
        //Order filter
		if (filterDto.getOrderColumn() != null && filterDto.getOrderType() != null && !filterDto.getOrderColumn().isBlank() && !filterDto.getOrderType().isBlank() ) {
			if (WebServiceUtil.ASCENDING_ORDER.equals(filterDto.getOrderType()))
				cq.orderBy(cb.asc(studentRoot.get(filterDto.getOrderColumn())));
			else if (WebServiceUtil.DESCENDING_ORDER.equals(filterDto.getOrderType()))
				cq.orderBy(cb.desc(studentRoot.get(filterDto.getOrderColumn())));
		}
        

		if (!predicates.isEmpty()) {
			cq.where(cb.and(predicates.toArray(new Predicate[0])));
		}

		List<StudentDto> studentList =  entityManager.createQuery(cq)
										.setFirstResult(filterDto.getStart())
										.setMaxResults(filterDto.getLength())
										.getResultList();
		
		
		CriteriaQuery<Long> filterCountQuery = cb.createQuery(Long.class);
		Root<StudentModel> filterCountRoot = filterCountQuery.from(StudentModel.class);

		filterCountQuery.multiselect(cb.count(filterCountRoot)).where(cb.and(predicates.toArray(new Predicate[0])));

		Long filterCount = entityManager.createQuery(filterCountQuery).getSingleResult();
		 
		result.put("filterCount", filterCount);
		result.put("data", studentList);
		
		return result;
	}

	

}
