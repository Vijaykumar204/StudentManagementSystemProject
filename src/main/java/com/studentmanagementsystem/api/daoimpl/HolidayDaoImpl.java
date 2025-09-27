package com.studentmanagementsystem.api.daoimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.studentmanagementsystem.api.dao.HolidayDao;
import com.studentmanagementsystem.api.model.custom.CommonFilterDto;
import com.studentmanagementsystem.api.model.custom.schoolholidays.SchoolHolidaysDto;
import com.studentmanagementsystem.api.model.entity.HolidayModel;
import com.studentmanagementsystem.api.util.WebServiceUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

@Repository

public class HolidayDaoImpl implements HolidayDao {

	@Autowired
	private EntityManager entityManager;


	/**
	 * Retrive list of declared holidays 
	 */
	@Override
	@Transactional
	public Map<String, Object> declaredHolidaysList(CommonFilterDto filterDto) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<SchoolHolidaysDto> cq = cb.createQuery(SchoolHolidaysDto.class);
		Root<HolidayModel> holidaysRoot = cq.from(HolidayModel.class);
		
		Map<String, Object> result = new HashMap<>();
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if (filterDto.getMonth() != null && filterDto.getYear() != null) {
			predicates.add(cb.equal(cb.function("MONTH", Integer.class, holidaysRoot.get("holidayDate")),
					filterDto.getMonth()));
			predicates.add(
					cb.equal(cb.function("YEAR", Integer.class, holidaysRoot.get("holidayDate")), filterDto.getYear()));
		}
		
		if (filterDto.getIsHolidayCancelled() != null) {
			predicates.add(cb.equal(holidaysRoot.get("isHolidayCancelled"), filterDto.getIsHolidayCancelled()));
		}
		
		if (filterDto.getSearchValue() != null) {
			predicates
					.add(cb.like(cb.lower(holidaysRoot.get("holidayReason")), "%" + filterDto.getSearchValue().toLowerCase() + "%"));
		}
		
		cq.select(cb.construct(SchoolHolidaysDto.class,
				holidaysRoot.get("holidayId"), 
				holidaysRoot.get("holidayDate"),
				holidaysRoot.get("holidayReason"),
				holidaysRoot.get("isHolidayCancelled"),
				holidaysRoot.get("holidayCancelledReason")
				
				));
		
				if (!predicates.isEmpty()) {
					cq.where(cb.and(predicates.toArray(new Predicate[0])));
				}
				
				if (filterDto.getOrderColumn() != null && filterDto.getOrderType() != null) {
					if (WebServiceUtil.ASCENDING_ORDER.equals(filterDto.getOrderType()))
						cq.orderBy(cb.asc(holidaysRoot.get(filterDto.getOrderColumn())));
					else if (WebServiceUtil.DESCENDING_ORDER.equals(filterDto.getOrderType()))
						cq.orderBy(cb.desc(holidaysRoot.get(filterDto.getOrderColumn())));
				}
		
				List<SchoolHolidaysDto> holidaysList = entityManager.createQuery(cq)
											.setFirstResult(filterDto.getStart())
											.setMaxResults(filterDto.getLength())
											.getResultList();
				//find the fiter count
				CriteriaQuery<Long> filterCountQuery = cb.createQuery(Long.class);
				Root<HolidayModel> filterCountRoot = filterCountQuery.from(HolidayModel.class);
				filterCountQuery.multiselect(cb.count(filterCountRoot))
						.where(cb.and(predicates.toArray(new Predicate[0])));
				Long filterCount = entityManager.createQuery(filterCountQuery).getSingleResult();
				 
				result.put("filterCount", filterCount);
				result.put("data", holidaysList);
		 
		 return result;
	}
}
