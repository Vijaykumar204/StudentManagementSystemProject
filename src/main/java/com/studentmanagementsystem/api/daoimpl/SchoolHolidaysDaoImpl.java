package com.studentmanagementsystem.api.daoimpl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.studentmanagementsystem.api.dao.SchoolHolidaysDao;
import com.studentmanagementsystem.api.model.custom.schoolholidays.SchoolHolidayFilterDto;
import com.studentmanagementsystem.api.model.custom.schoolholidays.SchoolHolidaysDto;
import com.studentmanagementsystem.api.model.entity.SchoolHolidaysModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class SchoolHolidaysDaoImpl implements SchoolHolidaysDao {

	@Autowired
	private EntityManager entityManager;


	/**
	 *  Retrieve the list of active holidays(iscancelHoliday = false).
	 *  And
	 *  Retrieve the list of cancel holidays(iscancelHoliday = true).
	 */
	@Override
	public List<SchoolHolidaysDto> listDeclaredHolidays(SchoolHolidayFilterDto schoolHolidayFilterDto) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<SchoolHolidaysDto> cq = cb.createQuery(SchoolHolidaysDto.class);
		Root<SchoolHolidaysModel> holidaysRoot = cq.from(SchoolHolidaysModel.class);
		

		cq.select(cb.construct(SchoolHolidaysDto.class,
				holidaysRoot.get("holidayId"), 
				holidaysRoot.get("holidayDate"),
				holidaysRoot.get("holidayReason"),
				holidaysRoot.get("isHolidayCancelled"),
				holidaysRoot.get("holidayCancelledReason"
						)));
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		if(schoolHolidayFilterDto.getIsHolidayCancelled()!=null) {
			predicates.add( cb.equal(holidaysRoot.get("isHolidayCancelled"),schoolHolidayFilterDto.getIsHolidayCancelled()));
		}
		if(schoolHolidayFilterDto.getMonth()!=null && schoolHolidayFilterDto.getYear()!=null) {
			predicates.add(cb.equal(cb.function("MONTH", Integer.class, holidaysRoot.get("holidayDate")),schoolHolidayFilterDto.getMonth() ));
			predicates.add(cb.equal(cb.function("YEAR", Integer.class, holidaysRoot.get("holidayDate")),schoolHolidayFilterDto.getYear() ));

		}
		if (!predicates.isEmpty()) {
			cq.where(cb.and(predicates.toArray(new Predicate[0])));
		}
		
		return entityManager.createQuery(cq)
				.setFirstResult(schoolHolidayFilterDto.getSize())
				.setMaxResults(schoolHolidayFilterDto.getLength())
				.getResultList();
		

	}
}
