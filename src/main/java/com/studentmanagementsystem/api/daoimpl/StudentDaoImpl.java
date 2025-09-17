package com.studentmanagementsystem.api.daoimpl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;

import com.studentmanagementsystem.api.dao.StudentDao;
import com.studentmanagementsystem.api.model.custom.student.StudentDto;

import com.studentmanagementsystem.api.model.entity.StudentModel;

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
	 * Filter students by  email or phone number or residingStatus or status
	 */

	@Override
	public List<StudentDto>  listStudentDetails(StudentDto studentDto) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<StudentDto> cq = cb.createQuery(StudentDto.class);
		Root<StudentModel> studentRoot = cq.from(StudentModel.class);
		
		cq.select(cb.construct(StudentDto.class, 
				
				studentRoot.get("studentId"),   
				studentRoot.get("firstName"),   
				studentRoot.get("middleName"),
				studentRoot.get("lastName"),
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
				studentRoot.get("parentsEmail")

		));
		
		List<Predicate> predicates = new ArrayList<Predicate>();
        if (studentDto.getStudentId() != null) {
		    predicates.add(cb.equal(
		    		studentRoot.get("studentId"), 
		    		studentDto.getStudentId()
		    ));
		}
		else if (studentDto.getEmail() != null && studentDto.getEmail()!=" ") {
			String emailLike = "%"+ studentDto.getEmail().toLowerCase() + "%";
			predicates.add(cb.like(cb.lower(studentRoot.get("email")), emailLike));
		}
		else if (studentDto.getPhoneNumber() != null && studentDto.getPhoneNumber() != " ") { 
			String phoneNumberLike = "%"+ studentDto.getPhoneNumber() + "%";
			predicates.add(cb.like(studentRoot.get("phoneNumber"),phoneNumberLike));
		}
		if (studentDto.getResidingStatus() != null) {
			predicates.add(cb.equal(studentRoot.get("residingStatus").get("code"), studentDto.getResidingStatus()));
		}
		if (studentDto.getStatus() != null) {
			predicates.add(cb.equal(studentRoot.get("status").get("code"), studentDto.getStatus()));
		}
		if (studentDto.getClassOfStudy() != null) {
			predicates.add(cb.equal(studentRoot.get("classOfStudy"), studentDto.getClassOfStudy()));
		}
		if (!predicates.isEmpty()) {
			cq.where(cb.and(predicates.toArray(new Predicate[0])));
		}
		
		return entityManager.createQuery(cq)
				.setFirstResult(studentDto.getSize())
				.setMaxResults(studentDto.getLength())
				.getResultList();
				
	}

}
