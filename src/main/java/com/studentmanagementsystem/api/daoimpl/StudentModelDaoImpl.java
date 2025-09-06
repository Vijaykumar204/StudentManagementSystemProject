package com.studentmanagementsystem.api.daoimpl;



import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;

import com.studentmanagementsystem.api.dao.StudentModelDao;
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
public class StudentModelDaoImpl  implements StudentModelDao{
	
	@Autowired
	private EntityManager entityManager;
	

	
	/**
	 * Retrieve the list of all student details.
	 */
	@Override
	public List<StudentDto> listAllDetailsStudent() {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<StudentDto> studentListQuery= cb.createQuery(StudentDto.class);
		Root<StudentModel> studentListRoot = studentListQuery.from(StudentModel.class);
				
		studentListQuery.multiselect(
				studentListRoot.get("studentId"),
				studentListRoot.get("studentFirstName"),
				studentListRoot.get("studentMiddleName"),
				studentListRoot.get("studentLastName"),
				studentListRoot.get("studentGender").get("description"),
				studentListRoot.get("studentDateOfBirth"),
				studentListRoot.get("studentClassOfStudy"),
				studentListRoot.get("studentResidingStatus").get("description"),
				studentListRoot.get("studentPhoneNumber"),
				studentListRoot.get("emergencyContactPersonName"),
				studentListRoot.get("emergencyContactPhoneNumber"),
				studentListRoot.get("homeStreetName"),
				studentListRoot.get("homeCityName"),
				studentListRoot.get("homePostalCode"),
				studentListRoot.get("studentActiveStatus").get("description"),
				studentListRoot.get("studentEmail")
			
				);
		
		return entityManager.createQuery(studentListQuery).getResultList();
	}
	
	/**
	 * Retrive all dayscholar students
	 * And
	 * Retrive all hostel students
	 */
	@Override
	public List<StudentDto> getAllHostelStudents(String studentActiveStatus, String hostel) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<StudentDto> studentListQuery= cb.createQuery(StudentDto.class);
		Root<StudentModel> studentListRoot = studentListQuery.from(StudentModel.class);
		
	       Predicate residingStatus = cb.equal(studentListRoot.get("studentResidingStatus"), hostel);
	       Predicate activeStatus = cb.equal(studentListRoot.get("studentActiveStatus"),studentActiveStatus );
	       Predicate lastEffectiveDateCondition = cb.isNull(studentListRoot.get("lasteffectivedate"));

	       studentListQuery.multiselect(
	    		   studentListRoot.get("studentId"),
	    		   studentListRoot.get("studentFirstName"),
	    		   studentListRoot.get("studentMiddleName"),
	    		   studentListRoot.get("studentLastName"),
	    		   studentListRoot.get("studentGender").get("description"),
	    		   studentListRoot.get("studentDateOfBirth"),
	    		   studentListRoot.get("studentClassOfStudy"),
	    		   studentListRoot.get("studentResidingStatus").get("description"),
	    		   studentListRoot.get("studentPhoneNumber"),
	    		   studentListRoot.get("emergencyContactPersonName"),
	    		   studentListRoot.get("emergencyContactPhoneNumber"),
	    		   studentListRoot.get("homeStreetName"),
	    		   studentListRoot.get("homeCityName"),
	    		   studentListRoot.get("homePostalCode"),
	    		   studentListRoot.get("studentActiveStatus").get("description"),
	    		   studentListRoot.get("studentEmail")
				
					).where(cb.and(residingStatus, activeStatus,lastEffectiveDateCondition));
		
		
		return entityManager.createQuery(studentListQuery).getResultList();
	}

	/**
	 * Filter students by id or email or phone number
	 */
	
	@Override
	public List<StudentDto> getStudentsBy(Long studentId, String studentEmail, String studentPhoneNumber) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<StudentDto> studentListQuery= cb.createQuery(StudentDto.class);
		Root<StudentModel> studentListRoot = studentListQuery.from(StudentModel.class);
		 Predicate lastEffectiveDateCondition = cb.isNull(studentListRoot.get("lasteffectivedate"));
		 studentListQuery.multiselect(
				 studentListRoot.get("studentId"),
				 studentListRoot.get("studentFirstName"),
				 studentListRoot.get("studentMiddleName"),
				 studentListRoot.get("studentLastName"),
				 studentListRoot.get("studentGender").get("description"),
				 studentListRoot.get("studentDateOfBirth"),
				 studentListRoot.get("studentClassOfStudy"),
				 studentListRoot.get("studentResidingStatus").get("description"),
				 studentListRoot.get("studentPhoneNumber"),
				 studentListRoot.get("emergencyContactPersonName"),
				 studentListRoot.get("emergencyContactPhoneNumber"),
				 studentListRoot.get("homeStreetName"),
				 studentListRoot.get("homeCityName"),
				 studentListRoot.get("homePostalCode"),
				 studentListRoot.get("studentActiveStatus").get("description"),
				 studentListRoot.get("studentEmail")
			
				).where(lastEffectiveDateCondition);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if(studentId!= null) {
			predicates.add(cb.equal(studentListRoot.get("studentId"), studentId));
		}
		if(studentEmail!=null) {
			predicates.add(cb.equal(studentListRoot.get("studentEmail"), studentEmail));
		}
		if(studentPhoneNumber!=null) {
			predicates.add(cb.equal(studentListRoot.get("studentPhoneNumber"), studentPhoneNumber));
		}
		
		if(!predicates.isEmpty()) {
			studentListQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		}
		
		return entityManager.createQuery(studentListQuery).getResultList();
	}

	/**
	 * Retrieve the list of students based on active or deactive status.
	 */
	
	@Override
	public List<StudentDto> getByStudentStatus(String studentActiveStatus) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<StudentDto> studentListQuery= cb.createQuery(StudentDto.class);
		Root<StudentModel> studentListRoot = studentListQuery.from(StudentModel.class);
		
		Predicate studentActiveCondition = cb.equal(studentListRoot.get("studentActiveStatus"),studentActiveStatus);
//		 Predicate lastEffectiveDateCondition = cb.isNull(studentListRoot.get("lasteffectivedate"));
		studentListQuery.multiselect(
				studentListRoot.get("studentId"),
				studentListRoot.get("studentFirstName"),
				studentListRoot.get("studentMiddleName"),
				studentListRoot.get("studentLastName"),
				studentListRoot.get("studentGender").get("description"),
				studentListRoot.get("studentDateOfBirth"),
				studentListRoot.get("studentClassOfStudy"),
				studentListRoot.get("studentResidingStatus").get("description"),
				studentListRoot.get("studentPhoneNumber"),
				studentListRoot.get("emergencyContactPersonName"),
				studentListRoot.get("emergencyContactPhoneNumber"),
				studentListRoot.get("homeStreetName"),
				studentListRoot.get("homeCityName"),
				studentListRoot.get("homePostalCode"),
				studentListRoot.get("studentActiveStatus").get("description"),
				studentListRoot.get("studentEmail")
			
				).where(studentActiveCondition);
		
		return entityManager.createQuery(studentListQuery).getResultList();
	}





}
