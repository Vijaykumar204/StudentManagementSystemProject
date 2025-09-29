package com.studentmanagementsystem.api.daoimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.studentmanagementsystem.api.dao.StudentCodeDao;
import com.studentmanagementsystem.api.model.custom.studentcode.StudentCodeDto;
import com.studentmanagementsystem.api.model.entity.StudentCodeModel;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class StudentCodeDaoImpl implements StudentCodeDao {
	
	@Autowired
	private EntityManager entityManager;

	@Override
	public List<StudentCodeDto> stuCodeList(String groupCode) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<StudentCodeDto> cq = cb.createQuery(StudentCodeDto.class);
		Root<StudentCodeModel> codeRoot = cq.from(StudentCodeModel.class);
		
		cq.select(cb.construct(StudentCodeDto.class,
				codeRoot.get("code"),
				codeRoot.get("groupCode"),
				codeRoot.get("subGroupCode"),
				codeRoot.get("description")
				));
		
				if (groupCode != null) {
					Predicate groupCodeCondition = cb.equal(codeRoot.get("groupCode"), groupCode);
					cq.where(groupCodeCondition);
				}
		
				return entityManager.createQuery(cq).getResultList();
	}
}

