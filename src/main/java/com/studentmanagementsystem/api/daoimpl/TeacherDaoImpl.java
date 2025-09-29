package com.studentmanagementsystem.api.daoimpl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.studentmanagementsystem.api.dao.TeacherDao;
import com.studentmanagementsystem.api.model.custom.CommonFilterDto;
import com.studentmanagementsystem.api.model.custom.teacher.TeacherDto;

import com.studentmanagementsystem.api.model.entity.TeacherModel;
import com.studentmanagementsystem.api.util.WebServiceUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

@Repository
public class TeacherDaoImpl  implements TeacherDao{

	@Autowired
	private EntityManager entityManager;
	

	
	/**
	 * Retrieve the list of all teacher details.
	 */

	@Override
	@Transactional
	public Map<String, Object> filterTeacher(CommonFilterDto filterDto) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<TeacherDto> cq = cb.createQuery(TeacherDto.class);
		Root<TeacherModel> teacherRoot = cq.from(TeacherModel.class);

		Map<String, Object> result = new HashMap<>();

		List<Predicate> predicates = new ArrayList<>();

		// Role filter
		if (filterDto.getRole() != null && !filterDto.getRole().isBlank()) {
			predicates.add(cb.equal(teacherRoot.get("teacherRole").get("code"), filterDto.getRole()));
		}

		if (filterDto.getSearchBy() != null && filterDto.getSearchValue() != null && !filterDto.getSearchBy().isBlank() && !filterDto.getSearchValue().isBlank()) {
			switch (filterDto.getSearchBy().toLowerCase()) {

			case WebServiceUtil.NAME:
				Predicate fullName = cb.like(cb.lower(teacherRoot.get("teacherName")),
						 filterDto.getSearchValue().toLowerCase() + "%");

				predicates.add(fullName);
				break;

			case WebServiceUtil.EMAIL:
				Predicate email = cb.like(cb.lower(teacherRoot.get("teacherEmail")),
						 filterDto.getSearchValue().toLowerCase() + "%");
				predicates.add(email);
				break;

			case WebServiceUtil.PHONE_NUMBER:
				Predicate phoneNumber = cb.like(teacherRoot.get("teacherPhoneNumber"), filterDto.getSearchValue() + "%");
				predicates.add(phoneNumber);
				break;
			}
		}

		if (filterDto.getOrderColumn() != null && filterDto.getOrderType() != null && !filterDto.getOrderColumn().isBlank() && !filterDto.getOrderType().isBlank()) {

			if (WebServiceUtil.ASCENDING_ORDER.equals(filterDto.getOrderType()))
				cq.orderBy(cb.asc(teacherRoot.get(filterDto.getOrderColumn())));
			else if (WebServiceUtil.DESCENDING_ORDER.equals(filterDto.getOrderType()))
				cq.orderBy(cb.desc(teacherRoot.get(filterDto.getOrderColumn())));
		}
		
			 cq.select(cb.construct(TeacherDto.class,
					 teacherRoot.get("teacherId"),
					 teacherRoot.get("teacherName"),
					 teacherRoot.get("teacherEmail"),
					 teacherRoot.get("teacherPhoneNumber"),
					 teacherRoot.get("teacherRole").get("code"),
					 teacherRoot.get("teacherDepartment")
					 
					 ));
		 
			 if(!predicates.isEmpty()) {
				cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
				}
			 
			 
	    	 List<TeacherDto> teacherList = entityManager.createQuery(cq)
	    			 						.setFirstResult(filterDto.getStart())
	    			 						.setMaxResults(filterDto.getLength())
	    			 						.getResultList(); 
			 
	    	 CriteriaQuery<Long> filterCountQuery= cb.createQuery(Long.class);
			 Root<TeacherModel> filterCountRoot  =filterCountQuery.from(TeacherModel.class);
			 filterCountQuery.multiselect(cb.count(filterCountRoot)).where(cb.and(predicates.toArray(new Predicate[0])));
			 
			 Long filterCount = entityManager.createQuery(filterCountQuery).getSingleResult();
			 
			 result.put("filterCount", filterCount);
			 result.put("data",teacherList);
		
		return result;
	}
	
}
