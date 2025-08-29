package com.studentmanagementsystem.api.daoimpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import com.studentmanagementsystem.api.dao.DailyAttendanceDao;
import com.studentmanagementsystem.api.model.custom.dailyattendance.DailyAttendanceDto;
import com.studentmanagementsystem.api.model.custom.dailyattendance.ExceedingDaysLeaveDto;
import com.studentmanagementsystem.api.model.custom.dailyattendance.MonthlyAbsenceDto;
import com.studentmanagementsystem.api.model.entity.DailyAttendanceModel;
import com.studentmanagementsystem.api.model.entity.StudentModel;
import com.studentmanagementsystem.api.repository.DailyAttendanceRepository;
import com.studentmanagementsystem.api.repository.StudentModelRepository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class DailyAttendanceDaoImpl implements DailyAttendanceDao {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private StudentModelRepository studentModelRepository;

	@Autowired
	private DailyAttendanceRepository dailyAttendanceRepository;

//	@Override
//	public Object setAttendanceInSingleStudent(DailyAttendanceDto dailyAttendanceDto) {
//
//		DailyAttendanceModel attend;
//		if (dailyAttendanceDto.getAttendanceId() == null) {
//			attend = new DailyAttendanceModel();
//			StudentModel student = studentModelRepository.getStudentByStudentId(dailyAttendanceDto.getStudentId());
//			attend.setStudentModel(student);
//		} else {
//			Optional<DailyAttendanceModel> attendance = dailyAttendanceRepository
//					.findById(dailyAttendanceDto.getAttendanceId());
//			attend = attendance.get();
//		}
//		attend.setAttendanceDate(dailyAttendanceDto.getAttendanceDate());
//		attend.setAttendanceStatus(dailyAttendanceDto.getAttendanceStatus());
//		if (dailyAttendanceDto.getAttendanceStatus() == 'A') {
//			attend.setLongApprovedSickLeaveFlag(dailyAttendanceDto.getLongApprovedSickLeaveFlag());
//			attend.setApprovedExtraCurricularActivitiesFlag(
//					dailyAttendanceDto.getApprovedExtraCurricularActivitiesFlag());
//		}
//		else {
//			attend.setLongApprovedSickLeaveFlag('N');
//			attend.setApprovedExtraCurricularActivitiesFlag('N');
//		}
//
//		DailyAttendanceModel saved = dailyAttendanceRepository.save(attend);
//		return new DailyAttendanceDto(saved.getAttendanceId(), saved.getAttendanceDate(), saved.getAttendanceStatus(),
//				saved.getLongApprovedSickLeaveFlag(), saved.getApprovedExtraCurricularActivitiesFlag(),
//				saved.getStudentModel().getStudentId());
//	}
//
//	

//	@Override
//	public Object setAttandanceMultiSameDate(List<DailyAttendanceDto> dailyAttendanceDto, LocalDate attendanceDate) {
//		
//List<DailyAttendanceModel> attendance = new ArrayList<>();
//		
//		for(DailyAttendanceDto attend : dailyAttendanceDto) {
//			DailyAttendanceModel  dailyAttendance;
//			if(attend.getAttendanceId() == null) {
//				dailyAttendance = new DailyAttendanceModel();
//				StudentModel student = studentModelRepository.getStudentByStudentId(attend.getStudentId());
//				dailyAttendance.setStudentModel(student);
//			}
//			else {
//				Optional<DailyAttendanceModel> dailyattend = dailyAttendanceRepository.findById(attend.getAttendanceId());
//				dailyAttendance = dailyattend.get();
//			}
//			
//			if(attendanceDate != null) {
//			dailyAttendance.setAttendanceDate(attendanceDate);
//			}
//			else {
//				dailyAttendance.setAttendanceDate(attend.getAttendanceDate());
//			}
//			dailyAttendance.setAttendanceStatus(attend.getAttendanceStatus());
//			if(attend.getAttendanceStatus() == 'A') {
//				dailyAttendance.setLongApprovedSickLeaveFlag(attend.getLongApprovedSickLeaveFlag());
//				dailyAttendance.setApprovedExtraCurricularActivitiesFlag(attend.getApprovedExtraCurricularActivitiesFlag());
//			}
//			else {
//				attend.setLongApprovedSickLeaveFlag('N');
//				attend.setApprovedExtraCurricularActivitiesFlag('N');
//			}
//			attendance.add(dailyAttendance);
//			
//		}
//		
//		List<DailyAttendanceModel> dailyAttendanceModel =dailyAttendanceRepository.saveAll(attendance);
//
//		return dailyAttendanceModel.stream().map(saved -> new DailyAttendanceDto(saved.getAttendanceId(), saved.getAttendanceDate(), saved.getAttendanceStatus(),
//				saved.getLongApprovedSickLeaveFlag(), saved.getApprovedExtraCurricularActivitiesFlag(),
//				saved.getStudentModel().getStudentId())).toList();
//	}

	

	@Override
	public DailyAttendanceModel createNewAttendance(Long studentId) {
		  StudentModel student = studentModelRepository.getStudentByStudentId(studentId);
	        DailyAttendanceModel dailyAttendance = new DailyAttendanceModel();
	        dailyAttendance.setStudentModel(student);
	        return dailyAttendance;
	}

	@Override
	public DailyAttendanceModel findAttendanceById(Long attendanceId) {
		 return dailyAttendanceRepository.findById(attendanceId)
	                .orElseThrow(() -> new RuntimeException("Attendance not found with ID: " + attendanceId));
	}

	@Override
	public Object setAttendanceToSingleStudent(DailyAttendanceModel dailyAttendance) {
		
		return dailyAttendanceRepository.save(dailyAttendance);
	}
	
	@Override
	public Object setAttandanceMultiStudents(List<DailyAttendanceModel> attendance) {
		 return dailyAttendanceRepository.saveAll(attendance);
	}

	@Override
	public List<DailyAttendanceDto> getStudentAttendance(LocalDate today) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<DailyAttendanceDto> cq=cb.createQuery(DailyAttendanceDto.class);
		Root<DailyAttendanceModel> attendance = cq.from(DailyAttendanceModel.class);
		
		Join<DailyAttendanceDto,StudentModel> student = attendance.join("studentModel");
		 Predicate predicate = cb.equal(attendance.get("attendanceDate"), today);
		
	     cq.select(cb.construct(DailyAttendanceDto.class,
	    		 attendance.get("attendanceDate"),
		    		attendance.get("attendanceStatus"),
	    		 student.get("studentId"),
	     		student.get("studentFirstName"),
	     		student.get("studentMiddleName"),
	     		student.get("studentLastName")
	    		
	    		 )).where(predicate);
		
		return entityManager.createQuery(cq).getResultList();
	}
	
	
	
//	SELECT s.* FROM student s WHERE NOT EXISTS ( SELECT 1 FROM daily_attendance_registration d    WHERE d.student_Id = s.STU_Id  AND d.AT_Date = '2025-08-23');
	

	@Override
	public List<DailyAttendanceDto> getStudentAttendanceNotTakeByToday(LocalDate today) {
		CriteriaBuilder cb=entityManager.getCriteriaBuilder();
		
		CriteriaQuery<DailyAttendanceDto> cq=cb.createQuery(DailyAttendanceDto.class);
		Root<StudentModel> student = cq.from(StudentModel.class);
		
		Subquery<DailyAttendanceModel> sq=cq.subquery(DailyAttendanceModel.class);
		Root<DailyAttendanceModel> attendance = sq.from(DailyAttendanceModel.class);
		
		Predicate condition1 = cb.equal(attendance.get("studentModel").get("studentId"),student.get("studentId"));
		Predicate condition2 =  cb.equal(attendance.get("attendanceDate"), today);
		sq.select(attendance).where(cb.and(condition1,condition2));
		
		Predicate condition3 = cb.not(cb.exists(sq));
		cq.select(cb.construct(DailyAttendanceDto.class,
				student.get("studentId"),
				student.get("studentFirstName"),
	     		student.get("studentMiddleName"),
	     		student.get("studentLastName")
	     		
				)
				).where(condition3);
		return entityManager.createQuery(cq).getResultList();
	}
	


//	@Override
//	public List<DailyAttendanceDto> getStudentleaveForExtraActivities(int month, int year, String leaveStatus) {
//	    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//
//		 CriteriaQuery<DailyAttendanceDto> cq = cb.createQuery(DailyAttendanceDto.class);
//		    Root<StudentModel> student = cq.from(StudentModel.class);
//		    Join<StudentModel, DailyAttendanceModel> attendance = student.join("dailyAttendanceModel"); 
//
//		    Subquery<Long> subquery = cq.subquery(Long.class);
//		    Root<DailyAttendanceModel> subAttendance = subquery.from(DailyAttendanceModel.class);
//
//		    Predicate statusCondition = cb.equal(attendance.get(leaveStatus), 'Y');
//		    Predicate monthCondition = cb.equal(cb.function("MONTH", Integer.class, attendance.get("attendanceDate")), month);
//		    Predicate yearCondition = cb.equal(cb.function("YEAR", Integer.class, attendance.get("attendanceDate")), year);
//		    
//		    subquery.select(subAttendance.get("studentModel").get("studentId"))
//		            .where(statusCondition, monthCondition, yearCondition
//
//		            )
//		            .groupBy(subAttendance.get("studentModel").get("studentId"))
//		            .having(cb.gt(cb.count(subAttendance), 3));
//
//
//		   
//		    Predicate inSubquery = student.get("studentId").in(subquery);
//
//		    cq.select(
//		        cb.construct(
//		            DailyAttendanceDto.class,
//		            attendance.get("attendanceDate"),
//		            student.get("studentId"),
//		            student.get("studentFirstName"),
//		            student.get("studentMiddleName"),
//		     		student.get("studentLastName")
//		            
//		        )
//		    ).where(cb.and(statusCondition, monthCondition, yearCondition, inSubquery));
//
//		    return entityManager.createQuery(cq).getResultList();
//	}
//	
//	
	
	

	@Override
	public List<MonthlyAbsenceDto> getMonthlyAbsenceStudents(int month, int year) {
		
		
		CriteriaBuilder cb=entityManager.getCriteriaBuilder();		
		CriteriaQuery<Long> workingDaysQuery =cb.createQuery(Long.class);
		Root<DailyAttendanceModel> workingDaysRoot = workingDaysQuery.from(DailyAttendanceModel.class);
		
		  Predicate monthConditionWorking = cb.equal(cb.function("MONTH", Integer.class,workingDaysRoot .get("attendanceDate")), month);
	      Predicate yearConditionWorking = cb.equal(cb.function("YEAR", Integer.class, workingDaysRoot.get("attendanceDate")), year);
	        
		workingDaysQuery.select(cb.countDistinct(workingDaysRoot.get("attendanceDate"))).where(monthConditionWorking,yearConditionWorking);
		
		Long totalWorkingDays = entityManager.createQuery(workingDaysQuery).getSingleResult();
		
		CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<StudentModel> student = cq.from(StudentModel.class);
        Join<StudentModel, DailyAttendanceModel> attendance = student.join("dailyAttendanceModel");

        Predicate monthCondition = cb.equal(cb.function("MONTH", Integer.class, attendance.get("attendanceDate")), month);
        Predicate yearCondition = cb.equal(cb.function("YEAR", Integer.class, attendance.get("attendanceDate")), year);
        Predicate absentCondition = cb.equal(attendance.get("attendanceStatus"), 'A');
        
        cq.multiselect(
                student.get("studentId").alias("studentId"),
                student.get("studentFirstName").alias("firstName"),
                student.get("studentMiddleName").alias("middleName"),
                student.get("studentLastName").alias("lastName"),
                cb.count(attendance).alias("absentCount")
        )
        .where(cb.and(monthCondition, yearCondition, absentCondition))
        .groupBy(student.get("studentId"), student.get("studentFirstName"),
                 student.get("studentMiddleName"), student.get("studentLastName"))
        .having(cb.gt(cb.count(attendance), totalWorkingDays / 2));
        
        List<Tuple> results = entityManager.createQuery(cq).getResultList();
        
        List<MonthlyAbsenceDto> dtoList = new ArrayList<>();
        for (Tuple tuple : results) {
            Long studentId = tuple.get("studentId", Long.class);

            List<LocalDate> absentDates = entityManager.createQuery(
                    "SELECT d.attendanceDate FROM DailyAttendanceModel d " +
                    "WHERE d.studentModel.studentId = :studentId " +
                    "AND d.attendanceStatus = 'A' " +
                    "AND MONTH(d.attendanceDate) = :month " +
                    "AND YEAR(d.attendanceDate) = :year", LocalDate.class)
                    .setParameter("studentId", studentId)
                    .setParameter("month", month)
                    .setParameter("year", year)
                    .getResultList();

            dtoList.add(new MonthlyAbsenceDto(
                    tuple.get("studentId", Long.class),
                    tuple.get("firstName", String.class),
                    tuple.get("middleName", String.class),
                    tuple.get("lastName", String.class),
                    totalWorkingDays.intValue(),
                    tuple.get("absentCount", Long.class),
                    absentDates
            ));
        }

        return dtoList;
		
	}

	@Override
	public List<ExceedingDaysLeaveDto> getStudentleaveForExtraActivitiesd(int month, int year, String leaveStatus,int leaveCount) {
		  CriteriaBuilder cb = entityManager.getCriteriaBuilder();

	        	  CriteriaQuery<Tuple> cq = cb.createTupleQuery();
			    Root<StudentModel> student = cq.from(StudentModel.class);
			    Join<StudentModel, DailyAttendanceModel> attendance = student.join("dailyAttendanceModel"); 

			    Subquery<Long> subquery = cq.subquery(Long.class);
			    Root<DailyAttendanceModel> subAttendance = subquery.from(DailyAttendanceModel.class);

			    Predicate statusCondition = cb.equal(attendance.get(leaveStatus), 'Y');
			    Predicate monthCondition = cb.equal(cb.function("MONTH", Integer.class, attendance.get("attendanceDate")), month);
			    Predicate yearCondition = cb.equal(cb.function("YEAR", Integer.class, attendance.get("attendanceDate")), year);
			    
			    subquery.select(subAttendance.get("studentModel").get("studentId"))
			            .where(statusCondition, monthCondition, yearCondition
			            )
			            .groupBy(subAttendance.get("studentModel").get("studentId"))
			            .having(cb.gt(cb.count(subAttendance), leaveCount));		   
			    Predicate inSubquery = student.get("studentId").in(subquery);

			    cq.multiselect(
			    		    student.get("studentId").alias("studentId"),
			                student.get("studentFirstName").alias("firstName"),
			                student.get("studentMiddleName").alias("middleName"),
			                student.get("studentLastName").alias("lastName")		            
			    ).distinct(true)
			     .where(inSubquery);

			    List<Tuple> results= entityManager.createQuery(cq).getResultList();
			    
			    List<ExceedingDaysLeaveDto> dtoList = new ArrayList<>();
			    for (Tuple tuple : results) {
		            Long studentId = tuple.get("studentId", Long.class);
		            List<LocalDate> absentDates = entityManager.createQuery(
		                    "SELECT d.attendanceDate FROM DailyAttendanceModel d " +
		                    "WHERE d.studentModel.studentId = :studentId " +
		                    "AND d.attendanceStatus = 'A' " +
		                    "AND MONTH(d.attendanceDate) = :month " +
		                    "AND YEAR(d.attendanceDate) = :year", LocalDate.class) 
		            		
		                    .setParameter("studentId", studentId)
		                    .setParameter("month", month)
		                    .setParameter("year", year)
		                    .getResultList();
			    
		            dtoList.add(new ExceedingDaysLeaveDto(
		            		tuple.get("studentId", Long.class),
		                    tuple.get("firstName", String.class),
		                    tuple.get("middleName", String.class),
		                    tuple.get("lastName", String.class),

		                    absentDates
		            ));
		        }

		        return dtoList;
}

	@Override
	public DailyAttendanceModel getStudentIdAndAttendanceDate(Long studentId, LocalDate attendanceDate) {
		
		return dailyAttendanceRepository.findStudentIdAndAttendanceDate(studentId,attendanceDate) ;
	}
}
