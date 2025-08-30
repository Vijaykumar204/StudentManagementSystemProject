package com.studentmanagementsystem.api.daoimpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;

import com.studentmanagementsystem.api.dao.StudentModelDao;
import com.studentmanagementsystem.api.model.custom.student.StudentListRequestDto;

import com.studentmanagementsystem.api.model.entity.StudentModel;

import com.studentmanagementsystem.api.repository.StudentModelRepository;
import com.studentmanagementsystem.api.repository.TeacherRepository;


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
	
	@Autowired
	private TeacherRepository teacherRepo;
	
	@Autowired
	private StudentModelRepository studentModelRepository;

	@Override
	public List<StudentListRequestDto> listAllDetailsStudent() {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<StudentListRequestDto> cq= cb.createQuery(StudentListRequestDto.class);
		Root<StudentModel> student = cq.from(StudentModel.class);

//		Predicate lastEfetiveDatecondition = cb.equal(student.get("lasteffectivedate"),null);
		
		
		cq.multiselect(
				student.get("studentId"),
				student.get("studentFirstName"),
				student.get("studentMiddleName"),
				student.get("studentLastName"),
				student.get("studentGender"),
				student.get("studentDateOfBirth"),
				student.get("studentClassOfStudy"),
				student.get("studentResidingStatus"),
				student.get("studentPhoneNumber"),
				student.get("emergencyContactPersonName"),
				student.get("emergencyContactPhoneNumber"),
				student.get("homeStreetName"),
				student.get("homeCityName"),
				student.get("homePostalCode"),
				student.get("studentActiveStatus"),
				student.get("studentEmail")
			
				);
		
	
		
		
		return entityManager.createQuery(cq).getResultList();
	}

	

	@Override
	public List<StudentListRequestDto> getAllHostelStudents(char studentActiveStatus, char hostel) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<StudentListRequestDto> cq= cb.createQuery(StudentListRequestDto.class);
		Root<StudentModel> student = cq.from(StudentModel.class);
		
	       Predicate residingStatus = cb.equal(student.get("studentResidingStatus"), hostel);
	       Predicate activeStatus = cb.equal(student.get("studentActiveStatus"),studentActiveStatus );
	       Predicate lastEffectiveDateCondition = cb.isNull(student.get("lasteffectivedate"));


	        cq.multiselect(
	        		student.get("studentId"),
	        		student.get("studentFirstName"),
	        		student.get("studentMiddleName"),
	        		student.get("studentLastName"),
	        		student.get("studentGender"),
	        		student.get("studentDateOfBirth"),
	        		student.get("studentClassOfStudy"),
	        		student.get("studentResidingStatus"),
	        		student.get("studentPhoneNumber"),
	        		student.get("emergencyContactPersonName"),
	        		student.get("emergencyContactPhoneNumber"),
	        		student.get("homeStreetName"),
	        		student.get("homeCityName"),
	        		student.get("homePostalCode"),
	        		student.get("studentActiveStatus"),
	        		student.get("studentEmail")
				
					).where(cb.and(residingStatus, activeStatus,lastEffectiveDateCondition));
		
		
		return entityManager.createQuery(cq).getResultList();
	}

	@Override
	public List<StudentListRequestDto> getStudentsBy(Long studentId, String studentEmail, String studentPhoneNumber) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<StudentListRequestDto> cq= cb.createQuery(StudentListRequestDto.class);
		Root<StudentModel> student = cq.from(StudentModel.class);
		 Predicate lastEffectiveDateCondition = cb.isNull(student.get("lasteffectivedate"));
		cq.multiselect(
				student.get("studentId"),
				student.get("studentFirstName"),
				student.get("studentMiddleName"),
				student.get("studentLastName"),
				student.get("studentGender"),
				student.get("studentDateOfBirth"),
				student.get("studentClassOfStudy"),
				student.get("studentResidingStatus"),
				student.get("studentPhoneNumber"),
				student.get("emergencyContactPersonName"),
				student.get("emergencyContactPhoneNumber"),
				student.get("homeStreetName"),
				student.get("homeCityName"),
				student.get("homePostalCode"),
				student.get("studentActiveStatus"),
				student.get("studentEmail")
			
				).where(lastEffectiveDateCondition);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if(studentId!= null) {
			predicates.add(cb.equal(student.get("studentId"), studentId));
		}
		if(studentEmail!=null) {
			predicates.add(cb.equal(student.get("studentEmail"), studentEmail));
		}
		if(studentPhoneNumber!=null) {
			predicates.add(cb.equal(student.get("studentPhoneNumber"), studentPhoneNumber));
		}
		
		if(!predicates.isEmpty()) {
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		}
		
		return entityManager.createQuery(cq).getResultList();
	}

	@Override
	public List<StudentListRequestDto> getBystudentStatus(char studentActiveStatus) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<StudentListRequestDto> cq= cb.createQuery(StudentListRequestDto.class);
		Root<StudentModel> student = cq.from(StudentModel.class);
		
		Predicate studentActiveCondition = cb.equal(student.get("studentActiveStatus"),studentActiveStatus);
//		 Predicate lastEffectiveDateCondition = cb.isNull(student.get("lasteffectivedate"));
		cq.multiselect(
				student.get("studentId"),
				student.get("studentFirstName"),
				student.get("studentMiddleName"),
				student.get("studentLastName"),
				student.get("studentGender"),
				student.get("studentDateOfBirth"),
				student.get("studentClassOfStudy"),
				student.get("studentResidingStatus"),
				student.get("studentPhoneNumber"),
				student.get("emergencyContactPersonName"),
				student.get("emergencyContactPhoneNumber"),
				student.get("homeStreetName"),
				student.get("homeCityName"),
				student.get("homePostalCode"),
				student.get("studentActiveStatus"),
				student.get("studentEmail")
			
				).where(studentActiveCondition);
		
		return entityManager.createQuery(cq).getResultList();
	}


	@Override
	public StudentModel getStudentModel(Long studentId) {
		  
		return studentModelRepository.getStudentByStudentId(studentId);
	}



	@Override
	public Optional<StudentModel> getByStudentId(Long studentId) {
	
		return studentModelRepository.findById(studentId);
	}



	@Override
	public Object saveStudent(StudentModel student) {
		
		return studentModelRepository.save(student);
	}



	@Override
	public Object activeOrDeactiveByStudentId(StudentModel student) {
		
		return studentModelRepository.save(student);
	}



}
