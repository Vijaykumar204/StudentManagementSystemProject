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
//	@Override
//	public List<StudentDto> listAllDetailsStudent() {
//		
//		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//		CriteriaQuery<StudentDto> studentListQuery= cb.createQuery(StudentDto.class);
//		Root<StudentModel> studentListRoot = studentListQuery.from(StudentModel.class);
//				
//		studentListQuery.multiselect(
//				studentListRoot.get("studentId"),
//				studentListRoot.get("firstName"),
//				studentListRoot.get("middleName"),
//				studentListRoot.get("lastName"),
//				studentListRoot.get("gender").get("description"),
//				studentListRoot.get("dateOfBirth"),
//				studentListRoot.get("classOfStudy"),
//				studentListRoot.get("residingStatus").get("description"),
//				studentListRoot.get("phoneNumber"),
//				studentListRoot.get("parentsName"),
////				studentListRoot.get("emergencyContactPhoneNumber"),
//				studentListRoot.get("homeStreetName"),
//				studentListRoot.get("homeCityName"),
//				studentListRoot.get("homePostalCode"),
//				studentListRoot.get("status").get("description"),
//				studentListRoot.get("email"),
//				studentListRoot.get("parentsEmail")
//			
//				);
//		
//		return entityManager.createQuery(studentListQuery).getResultList();
//	}
	
	/**
	 * Retrive all dayscholar students
	 * And
	 * Retrive all hostel students
	 */
//	@Override
//	public List<StudentDto> getAllHostelStudents(String status, String hostel) {
//		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//		CriteriaQuery<StudentDto> studentListQuery= cb.createQuery(StudentDto.class);
//		Root<StudentModel> studentListRoot = studentListQuery.from(StudentModel.class);
//		
//	       Predicate residingStatus = cb.equal(studentListRoot.get("residingStatus").get("code"), hostel);
//	       Predicate activeStatus = cb.equal(studentListRoot.get("status").get("code"),status );
//	       Predicate lastEffectiveDateCondition = cb.isNull(studentListRoot.get("lasteffectivedate"));
//
//	       studentListQuery.multiselect(
//	    		   studentListRoot.get("studentId"),
//					studentListRoot.get("firstName"),
//					studentListRoot.get("middleName"),
//					studentListRoot.get("lastName"),
//					studentListRoot.get("gender").get("description"),
//					studentListRoot.get("dateOfBirth"),
//					studentListRoot.get("classOfStudy"),
//					studentListRoot.get("residingStatus").get("description"),
//					studentListRoot.get("phoneNumber"),
//					studentListRoot.get("parentsName"),
////					studentListRoot.get("emergencyContactPhoneNumber"),
//					studentListRoot.get("homeStreetName"),
//					studentListRoot.get("homeCityName"),
//					studentListRoot.get("homePostalCode"),
//					studentListRoot.get("status").get("description"),
//					studentListRoot.get("email"),
//					studentListRoot.get("parentsEmail")
//				
//					).where(cb.and(residingStatus, activeStatus,lastEffectiveDateCondition));
//		
//		
//		return entityManager.createQuery(studentListQuery).getResultList();
//	}

	/**
	 * Filter students by id or email or phone number or residingStatus or status
	 */
	
	@Override
	public List<StudentDto> getStudentsBy(Long studentId, String email, String phoneNumber,String residingStatus,String status, Integer classOfStudy) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<StudentDto> studentListQuery= cb.createQuery(StudentDto.class);
		Root<StudentModel> studentListRoot = studentListQuery.from(StudentModel.class);
    
	//	Predicate lastEffectiveDateCondition = cb.isNull(studentListRoot.get("lasteffectivedate"));
		 studentListQuery.multiselect(
				 studentListRoot.get("studentId"),
					studentListRoot.get("firstName"),
					studentListRoot.get("middleName"),
					studentListRoot.get("lastName"),
					studentListRoot.get("gender").get("description"),
					studentListRoot.get("dateOfBirth"),
					studentListRoot.get("classOfStudy"),
					studentListRoot.get("residingStatus").get("description"),
					studentListRoot.get("phoneNumber"),
					studentListRoot.get("parentsName"),
//					studentListRoot.get("emergencyContactPhoneNumber"),
					studentListRoot.get("homeStreetName"),
					studentListRoot.get("homeCityName"),
					studentListRoot.get("homePostalCode"),
					studentListRoot.get("status").get("description"),
					studentListRoot.get("email"),
					studentListRoot.get("parentsEmail")
			
				);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		if(studentId != null) {
		    predicates.add(cb.equal(studentListRoot.get("studentId"), studentId));
		}
		if(email != null) {
		    predicates.add(cb.equal(studentListRoot.get("email"), email));
		}
		if(phoneNumber != null) {
		    predicates.add(cb.equal(studentListRoot.get("phoneNumber"), phoneNumber));
		}
		if(residingStatus != null) {
		    predicates.add(cb.equal(studentListRoot.get("residingStatus").get("code"), residingStatus));
		}
		if(status != null) {
		    predicates.add(cb.equal(studentListRoot.get("status").get("code"), status));
		}
		if(classOfStudy!= null) {
			 predicates.add(cb.equal(studentListRoot.get("classOfStudy"), classOfStudy));
		}
		if(!predicates.isEmpty()) {
			studentListQuery.where(cb.and(predicates.toArray(new Predicate[0])));
		}
		
		return entityManager.createQuery(studentListQuery).getResultList();
	}

	/**
	 * Retrieve the list of students based on active or deactive status.
	 */
	
//	@Override
//	public List<StudentDto> getByStudentStatus(String status) {
//		
//		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//		CriteriaQuery<StudentDto> studentListQuery= cb.createQuery(StudentDto.class);
//		Root<StudentModel> studentListRoot = studentListQuery.from(StudentModel.class);
//		
//		Predicate studentActiveCondition = cb.equal(studentListRoot.get("status"),status);
////		 Predicate lastEffectiveDateCondition = cb.isNull(studentListRoot.get("lasteffectivedate"));
//		studentListQuery.multiselect(
//				studentListRoot.get("studentId"),
//				studentListRoot.get("firstName"),
//				studentListRoot.get("middleName"),
//				studentListRoot.get("lastName"),
//				studentListRoot.get("gender").get("description"),
//				studentListRoot.get("dateOfBirth"),
//				studentListRoot.get("classOfStudy"),
//				studentListRoot.get("residingStatus").get("description"),
//				studentListRoot.get("phoneNumber"),
//				studentListRoot.get("parentsName"),
////				studentListRoot.get("emergencyContactPhoneNumber"),
//				studentListRoot.get("homeStreetName"),
//				studentListRoot.get("homeCityName"),
//				studentListRoot.get("homePostalCode"),
//				studentListRoot.get("status").get("description"),
//				studentListRoot.get("email"),
//				studentListRoot.get("parentsEmail")
//			
//				).where(studentActiveCondition);
//		
//		return entityManager.createQuery(studentListQuery).getResultList();
//	}





}
