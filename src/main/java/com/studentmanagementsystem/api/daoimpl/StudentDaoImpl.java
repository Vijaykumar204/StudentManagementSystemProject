package com.studentmanagementsystem.api.daoimpl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;

import com.studentmanagementsystem.api.dao.StudentDao;
import com.studentmanagementsystem.api.model.custom.student.StudentDto;
import com.studentmanagementsystem.api.model.custom.student.StudentFilterDto;
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
	public List<StudentDto>  listStudentDetails(StudentFilterDto filterDto) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<StudentDto> cq = cb.createQuery(StudentDto.class);
		Root<StudentModel> studentRoot = cq.from(StudentModel.class);
		
		cq.select(cb.construct(StudentDto.class, 
				
				studentRoot.get("studentId"),   
				 cb.concat(
			                cb.concat(
			                        cb.concat(cb.coalesce(studentRoot.get("firstName"), ""), " "),
			                        cb.concat(cb.coalesce(studentRoot.get("middleName"), ""), " ")
			                ),
			                cb.coalesce(studentRoot.get("lastName"), "")
			        ),
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
		
        if(filterDto.getSearchBy()!=null && filterDto.getSearchValue()!=null) {
        switch(filterDto.getSearchBy().toLowerCase())
        {
        
        case WebServiceUtil.NAME:
        	 Predicate fullName = cb.like(
        			 cb.concat(
 			                cb.concat(
 			                        cb.concat(cb.coalesce(studentRoot.get("firstName"), ""), " "),
 			                        cb.concat(cb.coalesce(studentRoot.get("middleName"), ""), " ")
 			                ),
 			                cb.coalesce(studentRoot.get("lastName"), "")
 			        ),
                    "%" + filterDto.getSearchValue() + "%"
            );
        	
        	predicates.add(fullName);
        	break;
        	
        case WebServiceUtil.EMAIL:
        	Predicate email =cb.like(cb.lower(studentRoot.get("email")) ,"%"+ filterDto.getSearchValue().toLowerCase() + "%");
			predicates.add(email);
			break;
			
        case WebServiceUtil.PHONENUMBER:
        	Predicate phoneNumber = cb.like(studentRoot.get("phoneNumber"),"%"+ filterDto.getSearchValue() + "%");
			predicates.add(phoneNumber);
			break;
        }
        }
        
        if(filterDto.getSortingBy()!=null && filterDto.getSortingOrder() !=null) {
        	System.out.println("order");
        	if(WebServiceUtil.ASCENDING_ORDER.equals(filterDto.getSortingOrder())) 
        		 cq.orderBy(cb.asc(studentRoot.get(filterDto.getSortingBy())));
        	else if(WebServiceUtil.DESCENDING_ORDER.equals(filterDto.getSortingOrder()))
        		cq.orderBy(cb.desc(studentRoot.get(filterDto.getSortingBy())));	
        }
        
		if (filterDto.getResidingStatus() != null) {
			predicates.add(cb.equal(studentRoot.get("residingStatus").get("code"), filterDto.getResidingStatus()));
		}
		if (filterDto.getStatus() != null) {
			predicates.add(cb.equal(studentRoot.get("status").get("code"), filterDto.getStatus()));
		}
		if (filterDto.getClassOfStudy() != null) {
			predicates.add(cb.equal(studentRoot.get("classOfStudy"), filterDto.getClassOfStudy()));
		}
		if (!predicates.isEmpty()) {
			cq.where(cb.and(predicates.toArray(new Predicate[0])));
		}
		
		return entityManager.createQuery(cq)
				.setFirstResult(filterDto.getStart())
				.setMaxResults(filterDto.getLength())
				.getResultList();
				
	}

}
