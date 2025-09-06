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

import com.studentmanagementsystem.api.util.WebServiceUtil;

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


	
	/**
	 * Retrieve student attendance.
	 * <p>
	 * By default, retrieves today's attendance. If a date is provided, retrieves attendance for that particular date.
	 */
	
//	SELECT 
//    da.attendance_date,
//    da.attendance_status,
//    s.student_id,
//    s.student_first_name,
//    s.student_middle_name,
//    s.student_last_name
//    FROM daily_attendance_registration da
//    INNER JOIN student s ON da.student_id = s.student_id
//    WHERE da.attendance_date = :today;

	@Override
	public List<DailyAttendanceDto> getStudentAttendance(LocalDate today) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<DailyAttendanceDto> dailyAttendanceQuery=cb.createQuery(DailyAttendanceDto.class);
		Root<DailyAttendanceModel> dailyAttendanceRoot = dailyAttendanceQuery.from(DailyAttendanceModel.class);
		
		Join<DailyAttendanceDto,StudentModel> studentAndDailyAttendanceJoin = dailyAttendanceRoot.join("studentModel");
		 Predicate predicate = cb.equal(dailyAttendanceRoot.get("attendanceDate"), today);
		
		 dailyAttendanceQuery.select(cb.construct(DailyAttendanceDto.class,
				 dailyAttendanceRoot.get("attendanceDate"),
				 dailyAttendanceRoot.get("attendanceStatus").get("description"),
				 studentAndDailyAttendanceJoin.get("studentId"),
				 studentAndDailyAttendanceJoin.get("studentFirstName"),
				 studentAndDailyAttendanceJoin.get("studentMiddleName"),
				 studentAndDailyAttendanceJoin.get("studentLastName")
	    		
	    		 )).where(predicate);
		
		return entityManager.createQuery(dailyAttendanceQuery).getResultList();
	}
	
	/**
	 * Retrieve students with no attendance marked.
	 * By default, retrieves students without attendance for today. 
	 * If a date is provided, retrieves students without attendance for that particular date.
	 */
//	SELECT s.* FROM student s WHERE NOT EXISTS ( SELECT 1 FROM daily_attendance_registration d  WHERE d.student_Id = s.STU_Id  AND d.AT_Date = '2025-08-23');
	@Override
	public List<DailyAttendanceDto> getStudentAttendanceNotTakeByToday(LocalDate today) {
		CriteriaBuilder cb=entityManager.getCriteriaBuilder();
		
		CriteriaQuery<DailyAttendanceDto> dailyAttendanceQuery=cb.createQuery(DailyAttendanceDto.class);
		Root<StudentModel> studentRoot = dailyAttendanceQuery.from(StudentModel.class);
		
		Subquery<DailyAttendanceModel> dailyAttendanceSubQuery=dailyAttendanceQuery.subquery(DailyAttendanceModel.class);
		Root<DailyAttendanceModel> dailyAttendanceRoot = dailyAttendanceSubQuery.from(DailyAttendanceModel.class);
		
		Predicate studentIdCondition = cb.equal(dailyAttendanceRoot.get("studentModel").get("studentId"),studentRoot.get("studentId"));
		Predicate attendanceDateCondition =  cb.equal(dailyAttendanceRoot.get("attendanceDate"), today);
		dailyAttendanceSubQuery.select(dailyAttendanceRoot).where(cb.and(studentIdCondition,attendanceDateCondition));
		
		Predicate existsCondition = cb.not(cb.exists(dailyAttendanceSubQuery));
		dailyAttendanceQuery.select(cb.construct(DailyAttendanceDto.class,
				studentRoot.get("studentId"),
				studentRoot.get("studentFirstName"),
				studentRoot.get("studentMiddleName"),
				studentRoot.get("studentLastName")
	     		
				)
				).where(existsCondition);
		return entityManager.createQuery(dailyAttendanceQuery).getResultList();
	}
	


	
	/**
	 * Retrieve students who were absent in a given month.
	 */
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
        Root<StudentModel> studentRoot = cq.from(StudentModel.class);
        Join<StudentModel, DailyAttendanceModel> dailyAttendanceAndStudentJoin = studentRoot.join("dailyAttendanceModel");

        Predicate monthCondition = cb.equal(cb.function("MONTH", Integer.class, dailyAttendanceAndStudentJoin.get("attendanceDate")), month);
        Predicate yearCondition = cb.equal(cb.function("YEAR", Integer.class, dailyAttendanceAndStudentJoin.get("attendanceDate")), year);
        Predicate absentCondition = cb.equal(dailyAttendanceAndStudentJoin.get("attendanceStatus"), WebServiceUtil.ABSENT);
        
        cq.multiselect(
        		studentRoot.get("studentId").alias("studentId"),
        		studentRoot.get("studentFirstName").alias("firstName"),
        		studentRoot.get("studentMiddleName").alias("middleName"),
        		studentRoot.get("studentLastName").alias("lastName"),
                cb.count(dailyAttendanceAndStudentJoin).alias("absentCount")
        )
        .where(cb.and(monthCondition, yearCondition, absentCondition))
        .groupBy(studentRoot.get("studentId"), studentRoot.get("studentFirstName"),
        		studentRoot.get("studentMiddleName"), studentRoot.get("studentLastName"))
        .having(cb.gt(cb.count(dailyAttendanceAndStudentJoin), totalWorkingDays / 2));
        
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
                    tuple.get("absentCount", Integer.class),
                    absentDates
            ));
        }

        return dtoList;
		
	}

	/**
	 * Retrieve students who have taken extra-curricular activity leave 
	 * for more than 3 days in a given month.
	 * And
	 * Retrieve students who have taken sick leave 
	 * for more than 6 days in a given month.
	 *
	 */	
	@Override
	public List<ExceedingDaysLeaveDto> getStudentleaveForExtraActivitiesd(int month, int year, String leaveStatus,int leaveCount) {
		  CriteriaBuilder cb = entityManager.getCriteriaBuilder();

	        	  CriteriaQuery<Tuple> cq = cb.createTupleQuery();
			    Root<StudentModel> studentRoot = cq.from(StudentModel.class);
			    Join<StudentModel, DailyAttendanceModel> dailyAttendanceAndStudentJoin = studentRoot.join("dailyAttendanceModel"); 

			    Subquery<Long> subquery = cq.subquery(Long.class);
			    Root<DailyAttendanceModel> subAttendance = subquery.from(DailyAttendanceModel.class);

			    Predicate statusCondition = cb.equal(dailyAttendanceAndStudentJoin.get(leaveStatus), WebServiceUtil.YES);
			    Predicate monthCondition = cb.equal(cb.function("MONTH", Integer.class, dailyAttendanceAndStudentJoin.get("attendanceDate")), month);
			    Predicate yearCondition = cb.equal(cb.function("YEAR", Integer.class, dailyAttendanceAndStudentJoin.get("attendanceDate")), year);
			    
			    subquery.select(subAttendance.get("studentModel").get("studentId"))
			            .where(statusCondition, monthCondition, yearCondition
			            )
			            .groupBy(subAttendance.get("studentModel").get("studentId"))
			            .having(cb.gt(cb.count(subAttendance), leaveCount));		   
			    Predicate inSubquery = studentRoot.get("studentId").in(subquery);

			    cq.multiselect(
			    		studentRoot.get("studentId").alias("studentId"),
			    		studentRoot.get("studentFirstName").alias("firstName"),
			    		studentRoot.get("studentMiddleName").alias("middleName"),
			    		studentRoot.get("studentLastName").alias("lastName")		            
			    ).distinct(true)
			     .where(inSubquery);

			    List<Tuple> studentDetailsList= entityManager.createQuery(cq).getResultList();
			    
			    List<ExceedingDaysLeaveDto> exceedingDaysLeaveList = new ArrayList<>();
			    for (Tuple tuple : studentDetailsList) {
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
			    
		            exceedingDaysLeaveList.add(new ExceedingDaysLeaveDto(
		            		tuple.get("studentId", Long.class),
		                    tuple.get("firstName", String.class),
		                    tuple.get("middleName", String.class),
		                    tuple.get("lastName", String.class),

		                    absentDates
		            ));
		        }

		        return exceedingDaysLeaveList;
}

}
