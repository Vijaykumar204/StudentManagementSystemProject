package com.studentmanagementsystem.api.daoimpl;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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

//	@Override
//	public Object declareMultipleHolidays(List<SchoolHolidaysDto> schoolHolidaysDto) {
//		List<SchoolHolidaysModel> holidays = new ArrayList<>();
//		for (SchoolHolidaysDto schooldto : schoolHolidaysDto) {
//			LocalDate date = schooldto.getHolidayDate();
//			if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
//				throw new RuntimeException("the Day is Sunday,so no need to declsre");
//			}
//			SchoolHolidaysModel holiday;
//
//			if (schooldto.getHolidayId() == null) {
//
//				holiday = new SchoolHolidaysModel();
//			} else {
////			Optional<SchoolHolidaysModel> holidayOpt = schoolHolidaysRepository.findById(schoolHolidaysDto.getHolidayId());
//				holiday = schoolHolidaysRepository.findById(schooldto.getHolidayId())
//						.orElseThrow(() -> new RuntimeException("Holiday id not found"));
//			}
//			holiday.setHolidayDate(schooldto.getHolidayDate());
//			holiday.setHolidayReason(schooldto.getHolidayReason());
//			holidays.add(holiday);
//		}
//		return schoolHolidaysRepository.saveAll(holidays);
//	}

	@Override
	public List<SchoolHolidaysDto> getAllHolidays(Boolean holiday) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<SchoolHolidaysDto> cq = cb.createQuery(SchoolHolidaysDto.class);
		Root<SchoolHolidaysModel> root = cq.from(SchoolHolidaysModel.class);
		
		Predicate predicate = cb.equal(root.get("isHolidayCancelled"),holiday);

		cq.multiselect(root.get("holidayId"), root.get("holidayDate"), root.get("holidayReason"),
				root.get("isHolidayCancelled"), root.get("holidayCancelledReason")).where(predicate);
		return entityManager.createQuery(cq).getResultList();
	}

//	@Override
//	public Object cancelHolidayByDate(SchoolHolidaysDto schoolHolidaysDto) {
//		Optional<SchoolHolidaysModel> holidayOpt = schoolHolidaysRepository
//				.getHolidayByHolidayDate(schoolHolidaysDto.getHolidayDate());
//		SchoolHolidaysModel holiday = holidayOpt.get();
//
//		if (Boolean.TRUE.equals(holiday.getIsHolidayCancelled())) {
//			throw new RuntimeException("This holiday is already cancelled");
//		}
//
//		holiday.setIsHolidayCancelled(true);
//		holiday.setHolidayCancelledReason(schoolHolidaysDto.getHolidayCancelledReason());
//
//		return schoolHolidaysRepository.save(holiday);
//	}

//	@Override
//	public Object cancelMultipleHoliday(List<SchoolHolidaysDto> schoolHolidaysDto) {
//		List<SchoolHolidaysModel> holidays = new ArrayList<>();
//		for (SchoolHolidaysDto schooldto : schoolHolidaysDto) {
//			Optional<SchoolHolidaysModel> holidayOpt = schoolHolidaysRepository
//					.getHolidayByHolidayDate(schooldto.getHolidayDate());
//			SchoolHolidaysModel holiday = holidayOpt.get();
//
//			if (Boolean.TRUE.equals(holiday.getIsHolidayCancelled())) {
//				throw new RuntimeException("This holiday is already cancelled");
//			}
//
//			holiday.setIsHolidayCancelled(true);
//			holiday.setHolidayCancelledReason(schooldto.getHolidayCancelledReason());
//
//			holidays.add(holiday);
//		}
//
//		return schoolHolidaysRepository.saveAll(holidays);
//	}

	@Override
	public SchoolHolidaysModel findHolidayId(Long holidayId) {
		SchoolHolidaysModel holidays = schoolHolidaysRepository.findById(holidayId)
				.orElseThrow(() -> new RuntimeException("Holiday id not found : " + holidayId));
		
		return holidays;
	}

	@Override
	public Object declareHoliday(SchoolHolidaysModel holiday) {
		
		return schoolHolidaysRepository.save(holiday);
	}

	@Override
	public Object declareMultipleHolidays(List<SchoolHolidaysModel> holidays) {

		return schoolHolidaysRepository.saveAll(holidays);
	}

	@Override
	public SchoolHolidaysModel findHolidayId(LocalDate holidayDate) {
		Optional<SchoolHolidaysModel> holidayOpt = schoolHolidaysRepository
			.getHolidayByHolidayDate(holidayDate);
		SchoolHolidaysModel holiday = holidayOpt.get();
		return holiday;
	}

	@Override
	public Object cancelHolidayByDate(SchoolHolidaysModel holiday) {
		
		return schoolHolidaysRepository.save(holiday);
	}

	@Override
	public Object cancelMultipleHoliday(List<SchoolHolidaysModel> holidays) {

		return schoolHolidaysRepository.saveAll(holidays);
	}



}
