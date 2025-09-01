package com.studentmanagementsystem.api.daoimpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.studentmanagementsystem.api.dao.TeacherRequestDao;
import com.studentmanagementsystem.api.model.custom.teacher.TeacherModelListDto;
import com.studentmanagementsystem.api.model.custom.teacher.TeacherSaveRequestDto;
import com.studentmanagementsystem.api.model.entity.TeacherModel;
import com.studentmanagementsystem.api.repository.TeacherRepository;

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
	
	@Autowired
	private TeacherRepository teacherRepository;
	
	@Override
	public List<TeacherModelListDto> listAllTeachers() {
		
		CriteriaBuilder cb= entityManager.getCriteriaBuilder();
		CriteriaQuery<TeacherModelListDto> cq=cb.createQuery(TeacherModelListDto.class);
		Root<TeacherModel> root = cq.from(TeacherModel.class);
		
		cq.select(cb.construct(TeacherModelListDto.class,
				root.get("teacherId"),
				root.get("teacherName"),
				root.get("teacherRole"),
				root.get("teacherDepartment"),
				root.get("teacherPhoneNumber")
				));
		return entityManager.createQuery(cq).getResultList();
	}

	@Override
	public Object saveTeacher(TeacherModel teacher) {
		

		   return teacherRepository.save(teacher);
	}
	
	
	// Filter Teacher

	@Override
	public List<TeacherModelListDto> filterTeacher(Long teacherId, String teacherName, String teacherPhoneNumber) {
		 CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		 CriteriaQuery<TeacherModelListDto> cq= cb.createQuery(TeacherModelListDto.class);
		 Root<TeacherModel> root =cq.from(TeacherModel.class);
		 cq.select(cb.construct(TeacherModelListDto.class,
					root.get("teacherId"),
					root.get("teacherName"),
					root.get("teacherRole"),
					root.get("teacherDepartment"),
					root.get("teacherPhoneNumber")
				 ));
		 List<Predicate> predicates = new ArrayList<>();
		 if(teacherId!=null) {
				predicates.add(cb.equal(root.get("teacherId"),teacherId));
		 }
		 
		 if(teacherName!=null) {
			 predicates.add(cb.equal(root.get("teacherName"),teacherName));
		}
		
		if(teacherPhoneNumber!=null) {
			 predicates.add(cb.equal(root.get("teacherPhoneNumber"),teacherPhoneNumber));
		}
		
		if(!predicates.isEmpty()) {
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		}
		 return entityManager.createQuery(cq).getResultList();

	}

	@Override
	public TeacherModel getTeacherByTeacherId(Long teacherId) {
		
		return teacherRepository.getTeacherByTeacherId(teacherId);
	}



}
