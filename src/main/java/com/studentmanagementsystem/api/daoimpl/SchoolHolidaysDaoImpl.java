package com.studentmanagementsystem.api.daoimpl;


import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.studentmanagementsystem.api.dao.SchoolHolidaysDao;
import com.studentmanagementsystem.api.model.custom.schoolholidays.SchoolHolidaysDto;
import com.studentmanagementsystem.api.model.entity.SchoolHolidaysModel;
import com.studentmanagementsystem.api.repository.SchoolHolidaysRepository;
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

	@Autowired
	private SchoolHolidaysRepository schoolHolidaysRepository;



	/**
	 *  Retrieve the list of active holidays(iscancelHoliday = false).
	 *  And
	 *  Retrieve the list of cancel holidays(iscancelHoliday = true).
	 */
	
	@Override
	public List<SchoolHolidaysDto> getAllHolidays(Boolean holiday) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<SchoolHolidaysDto> schoolHolidayQuery = cb.createQuery(SchoolHolidaysDto.class);
		Root<SchoolHolidaysModel> schoolHolidayRoot = schoolHolidayQuery.from(SchoolHolidaysModel.class);
		
		Predicate holidayCancelledCondition = cb.equal(schoolHolidayRoot.get("isHolidayCancelled"),holiday);

		schoolHolidayQuery.multiselect(schoolHolidayRoot.get("holidayId"), schoolHolidayRoot.get("holidayDate"), schoolHolidayRoot.get("holidayReason"),
				schoolHolidayRoot.get("isHolidayCancelled"), schoolHolidayRoot.get("holidayCancelledReason")).where(holidayCancelledCondition);
		return entityManager.createQuery(schoolHolidayQuery).getResultList();
	}


	@Override
	public SchoolHolidaysModel findHolidayId(Long holidayId) {
		SchoolHolidaysModel holidays = schoolHolidaysRepository.findById(holidayId)
				.orElseThrow(() -> new RuntimeException("Holiday id not found : " + holidayId));
		
		return holidays;
	}



	@Override
	public SchoolHolidaysModel getHolidayByHolidayDate(LocalDate holidayDate) {
		
		return schoolHolidaysRepository.getHolidayByHolidayDate(holidayDate) ;
	}



}
