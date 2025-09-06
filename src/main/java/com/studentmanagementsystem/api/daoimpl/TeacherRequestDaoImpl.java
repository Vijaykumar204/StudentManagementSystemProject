package com.studentmanagementsystem.api.daoimpl;


import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.studentmanagementsystem.api.dao.TeacherRequestDao;
import com.studentmanagementsystem.api.model.custom.teacher.TeacherModelListDto;

import com.studentmanagementsystem.api.model.entity.TeacherModel;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class TeacherRequestDaoImpl  implements TeacherRequestDao{

	@Autowired
	private EntityManager entityManager;
	

	
	/**
	 * Retrieve the list of all teacher details.
	 */
	
	@Override
	public List<TeacherModelListDto> listAllTeachers() {
		
		CriteriaBuilder cb= entityManager.getCriteriaBuilder();
		CriteriaQuery<TeacherModelListDto> teacherLisyQuery=cb.createQuery(TeacherModelListDto.class);
		Root<TeacherModel> teacherRoot = teacherLisyQuery.from(TeacherModel.class);
		
		teacherLisyQuery.select(cb.construct(TeacherModelListDto.class,
				teacherRoot.get("teacherId"),
				teacherRoot.get("teacherName"),
				teacherRoot.get("teacherRole").get("description"),
				teacherRoot.get("teacherDepartment"),
				teacherRoot.get("teacherPhoneNumber")
				));
		return entityManager.createQuery(teacherLisyQuery).getResultList();
	}


	
	
	/**
	 * Filter teacher by ID, email, or phone number.
	 */

	@Override
	public List<TeacherModelListDto> filterTeacher(Long teacherId, String teacherName, String teacherPhoneNumber) {
		 CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		 CriteriaQuery<TeacherModelListDto> teacherFilter= cb.createQuery(TeacherModelListDto.class);
		 Root<TeacherModel> teacherRoot =teacherFilter.from(TeacherModel.class);
		 teacherFilter.select(cb.construct(TeacherModelListDto.class,
				 teacherRoot.get("teacherId"),
				 teacherRoot.get("teacherName"),
				 teacherRoot.get("teacherRole").get("description"),
				 teacherRoot.get("teacherDepartment"),
				 teacherRoot.get("teacherPhoneNumber")
				 ));
		 List<Predicate> predicates = new ArrayList<>();
		 if(teacherId!=null) {
				predicates.add(cb.equal(teacherRoot.get("teacherId"),teacherId));
		 }
		 
		 if(teacherName!=null) {
			 predicates.add(cb.equal(teacherRoot.get("teacherName"),teacherName));
		}
		
		if(teacherPhoneNumber!=null) {
			 predicates.add(cb.equal(teacherRoot.get("teacherPhoneNumber"),teacherPhoneNumber));
		}
		
		if(!predicates.isEmpty()) {
			teacherFilter.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		}
		 return entityManager.createQuery(teacherFilter).getResultList();

	}


}
