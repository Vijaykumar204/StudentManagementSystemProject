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
import jakarta.persistence.TypedQuery;
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
	public List<SchoolHolidaysDto> getAllHolidays(SchoolHolidayFilterDto schoolHolidayFilterDto) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<SchoolHolidaysDto> schoolHolidayQuery = cb.createQuery(SchoolHolidaysDto.class);
		Root<SchoolHolidaysModel> schoolHolidayRoot = schoolHolidayQuery.from(SchoolHolidaysModel.class);
		

		schoolHolidayQuery.multiselect(schoolHolidayRoot.get("holidayId"), 
				schoolHolidayRoot.get("holidayDate"),
				schoolHolidayRoot.get("holidayReason"),
				schoolHolidayRoot.get("isHolidayCancelled"),
				schoolHolidayRoot.get("holidayCancelledReason"
						));
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		if(schoolHolidayFilterDto.getIsHolidayCancelled()!=null) {
			predicates.add( cb.equal(schoolHolidayRoot.get("isHolidayCancelled"),schoolHolidayFilterDto.getIsHolidayCancelled()));
		}
		if(schoolHolidayFilterDto.getMonth()!=null && schoolHolidayFilterDto.getYear()!=null) {
			predicates.add(cb.equal(cb.function("MONTH", Integer.class, schoolHolidayRoot.get("holidayDate")),schoolHolidayFilterDto.getMonth() ));
			predicates.add(cb.equal(cb.function("YEAR", Integer.class, schoolHolidayRoot.get("holidayDate")),schoolHolidayFilterDto.getYear() ));

		}
//		if() {
//			predicates.add(cb.equal(cb.function("YEAR", Integer.class, schoolHolidayRoot.get("holidayDate")),schoolHolidayFilterDto.getYear() ));
//		}
		if (!predicates.isEmpty()) {
			schoolHolidayQuery.where(cb.and(predicates.toArray(new Predicate[0])));
		}
		TypedQuery<SchoolHolidaysDto> result = entityManager.createQuery(schoolHolidayQuery);
		result.setFirstResult(schoolHolidayFilterDto.getSize());
		result.setMaxResults(schoolHolidayFilterDto.getLength());
			
		return result.getResultList();
		

	}
}
