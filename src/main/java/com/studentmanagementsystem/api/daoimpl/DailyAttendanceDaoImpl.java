package com.studentmanagementsystem.api.daoimpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.studentmanagementsystem.api.dao.DailyAttendanceDao;
import com.studentmanagementsystem.api.model.custom.dailyattendance.DailyAttendanceDto;
import com.studentmanagementsystem.api.model.custom.dailyattendance.DailyAttendanceFilterDto;
import com.studentmanagementsystem.api.model.custom.dailyattendance.MonthlyAbsenceDto;
import com.studentmanagementsystem.api.model.entity.DailyAttendanceModel;
import com.studentmanagementsystem.api.model.entity.StudentModel;
import com.studentmanagementsystem.api.util.WebServiceUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
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
       * Retrieve student attendance for a particular date to attendance taken.
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
	public List<DailyAttendanceDto> getStudentAttendanceTaken(DailyAttendanceFilterDto dailyAttendanceFilterDto) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<DailyAttendanceDto> dailyAttendanceQuery=cb.createQuery(DailyAttendanceDto.class);
		Root<DailyAttendanceModel> dailyAttendanceRoot = dailyAttendanceQuery.from(DailyAttendanceModel.class);
		
		Join<DailyAttendanceDto,StudentModel> studentAndDailyAttendanceJoin = dailyAttendanceRoot.join("studentModel");		
		 dailyAttendanceQuery.select(cb.construct(DailyAttendanceDto.class,
				 dailyAttendanceRoot.get("attendanceDate"),
				 dailyAttendanceRoot.get("attendanceStatus").get("description"),
				 studentAndDailyAttendanceJoin.get("studentId"),
				 studentAndDailyAttendanceJoin.get("firstName"),
				 studentAndDailyAttendanceJoin.get("middleName"),
				 studentAndDailyAttendanceJoin.get("lastName")
	    		
	    		 ));
		 
		 List<Predicate> predicates = new ArrayList<Predicate>();
		 
		 if(dailyAttendanceFilterDto.getAttendanceStatus()  !=null) {
				predicates.add( cb.equal(dailyAttendanceRoot.get("attendanceStatus").get("code"),dailyAttendanceFilterDto.getAttendanceStatus()));
		  }
		 predicates.add(cb.equal(dailyAttendanceRoot.get("attendanceDate"),dailyAttendanceFilterDto.getDate() ));
		 predicates.add(cb.equal(dailyAttendanceRoot.get("studentModel").get("classOfStudy"),dailyAttendanceFilterDto.getClassOfStudy() ));

		 
		 if (!predicates.isEmpty()) {
			 dailyAttendanceQuery.where(cb.and(predicates.toArray(new Predicate[0])));
			}
		 
		 TypedQuery<DailyAttendanceDto> result = entityManager.createQuery(dailyAttendanceQuery);
			result.setFirstResult(dailyAttendanceFilterDto.getSize());
			result.setMaxResults(dailyAttendanceFilterDto.getLength());	
			return result.getResultList();
		
	}
	
	/**
     * Retrieve student attendance for a particular date to attendance not taken.
	 */
//	SELECT s.* FROM student s WHERE NOT EXISTS ( SELECT 1 FROM daily_attendance_registration d  WHERE d.student_Id = s.STU_Id  AND d.AT_Date = '2025-08-23');
	@Override
	public List<DailyAttendanceDto> getStudentAttendanceNotTaken(DailyAttendanceFilterDto dailyAttendanceFilterDto) {
		CriteriaBuilder cb=entityManager.getCriteriaBuilder();
		
		CriteriaQuery<DailyAttendanceDto> dailyAttendanceQuery=cb.createQuery(DailyAttendanceDto.class);
		Root<StudentModel> studentRoot = dailyAttendanceQuery.from(StudentModel.class);
		
		Subquery<DailyAttendanceModel> dailyAttendanceSubQuery=dailyAttendanceQuery.subquery(DailyAttendanceModel.class);
		Root<DailyAttendanceModel> dailyAttendanceRoot = dailyAttendanceSubQuery.from(DailyAttendanceModel.class);
		
		Predicate studentIdCondition = cb.equal(dailyAttendanceRoot.get("studentModel").get("studentId"),studentRoot.get("studentId"));
		Predicate attendanceDateCondition =  cb.equal(dailyAttendanceRoot.get("attendanceDate"),dailyAttendanceFilterDto.getDate() );
		dailyAttendanceSubQuery.select(dailyAttendanceRoot).where(cb.and(studentIdCondition,attendanceDateCondition));
		
		Predicate existsCondition = cb.not(cb.exists(dailyAttendanceSubQuery));
		Predicate classOfStudyCondition = cb.equal(studentRoot.get("classOfStudy"), dailyAttendanceFilterDto.getClassOfStudy());
		dailyAttendanceQuery.select(cb.construct(DailyAttendanceDto.class,
				studentRoot.get("studentId"),
				studentRoot.get("firstName"), //Student First Name
				studentRoot.get("middleName"),//Student Middle Name
				studentRoot.get("lastName")   //Student Last Name
	     		
				)
				).where(existsCondition,classOfStudyCondition);
		   
		    TypedQuery<DailyAttendanceDto> result =entityManager.createQuery(dailyAttendanceQuery);
			result.setFirstResult(dailyAttendanceFilterDto.getSize());
			result.setMaxResults(dailyAttendanceFilterDto.getLength());	
			
			return result.getResultList();
	}
	

//	/**
//	 * Retrieve students who were absent in a given month.
//	 */
//	@Override
//	public List<MonthlyAbsenceDto> getMonthlyAbsenceStudents(DailyAttendanceFilterDto dailyAttendanceFilterDto) {
//
//	    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//
//	    //Get total working days
//	    CriteriaQuery<Long> workingDaysQuery = cb.createQuery(Long.class);
//	    Root<DailyAttendanceModel> workingDaysRoot = workingDaysQuery.from(DailyAttendanceModel.class);
//
//	    Predicate monthConditionWorking = cb.equal(cb.function("MONTH", Integer.class, workingDaysRoot.get("attendanceDate")), dailyAttendanceFilterDto.getMonth());
//	    Predicate yearConditionWorking = cb.equal(cb.function("YEAR", Integer.class, workingDaysRoot.get("attendanceDate")), dailyAttendanceFilterDto.getYear());
//	    Predicate classConditionWorking = cb.equal(workingDaysRoot.get("studentModel").get("classOfStudy"), dailyAttendanceFilterDto.getClassOfStudy());
//
//	    workingDaysQuery.select(cb.countDistinct(workingDaysRoot.get("attendanceDate")))
//	            .where(monthConditionWorking, yearConditionWorking, classConditionWorking);
//
//	    Long totalWorkingDays = entityManager.createQuery(workingDaysQuery).getSingleResult();
//
//	    //Get absent students
//	    CriteriaQuery<Tuple> cq = cb.createTupleQuery();
//	    Root<StudentModel> studentRoot = cq.from(StudentModel.class);
//	    Join<StudentModel, DailyAttendanceModel> dailyAttendanceJoin = studentRoot.join("dailyAttendanceModel");
//
//	    Predicate monthCondition = cb.equal(cb.function("MONTH", Integer.class, dailyAttendanceJoin.get("attendanceDate")), dailyAttendanceFilterDto.getMonth());
//	    Predicate yearCondition = cb.equal(cb.function("YEAR", Integer.class, dailyAttendanceJoin.get("attendanceDate")), dailyAttendanceFilterDto.getYear());
//	    Predicate absentCondition = cb.equal(dailyAttendanceJoin.get("attendanceStatus").get("code"), WebServiceUtil.ABSENT);
//	    Predicate classCondition = cb.equal(studentRoot.get("classOfStudy"), dailyAttendanceFilterDto.getClassOfStudy());
//
//	    cq.multiselect(
//	            studentRoot.get("studentId").alias("studentId"),
//	            studentRoot.get("firstName").alias("firstName"),
//	            studentRoot.get("middleName").alias("middleName"),
//	            studentRoot.get("lastName").alias("lastName"),
//	            cb.count(dailyAttendanceJoin).alias("absentCount")
//	    )
//	    .where(cb.and(monthCondition, yearCondition, absentCondition, classCondition))
//	    .groupBy(studentRoot.get("studentId"), studentRoot.get("firstName"),
//	            studentRoot.get("middleName"), studentRoot.get("lastName"));
//
//
//	    List<Tuple> results = entityManager.createQuery(cq).getResultList();
//
//
//	    List<MonthlyAbsenceDto> monthlyAbsenceList = new ArrayList<>();
//	    for (Tuple tuple : results) {
//	        Long studentId = tuple.get("studentId", Long.class);
//	        //get Absent date At particular student Id
//	        List<LocalDate> absentDates = entityManager.createQuery(
//	                "SELECT d.attendanceDate FROM DailyAttendanceModel d " +
//	                        "WHERE d.studentModel.studentId = :studentId " +
//	                        "AND d.attendanceStatus.code = :status " +
//	                        "AND MONTH(d.attendanceDate) = :month " +
//	                        "AND YEAR(d.attendanceDate) = :year", LocalDate.class)
//	                .setParameter("studentId", studentId)
//	                .setParameter("status", WebServiceUtil.ABSENT)
//	                .setParameter("month", dailyAttendanceFilterDto.getMonth())
//	                .setParameter("year", dailyAttendanceFilterDto.getYear())
//	                .getResultList();
//
//	        monthlyAbsenceList.add(new MonthlyAbsenceDto(
//	                studentId,
//	                tuple.get("firstName", String.class),
//	                tuple.get("middleName", String.class),
//	                tuple.get("lastName", String.class),
//	                totalWorkingDays.intValue(),
//	                tuple.get("absentCount", Long.class).intValue(),
//	                absentDates
//	        ));
//	    }
//
//	    return monthlyAbsenceList;
//	}

//	/**
//	 * Retrieve students who have taken extra-curricular activity leave 
//	 * for more than 3 days in a given month.
//	 * And
//	 * Retrieve students who have taken sick leave 
//	 * for more than 6 days in a given month.
//	 *
//	 */	
//	@Override
//	public List<MonthlyAbsenceDto> getStudentleave(DailyAttendanceFilterDto dailyAttendanceFilterDto) {
//		  CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//
//	        	  CriteriaQuery<Tuple> cq = cb.createTupleQuery();
//			    Root<StudentModel> studentRoot = cq.from(StudentModel.class);
//			    Join<StudentModel, DailyAttendanceModel> dailyAttendanceAndStudentJoin = studentRoot.join("dailyAttendanceModel"); 
//
//			    Subquery<Long> subquery = cq.subquery(Long.class);
//			    Root<DailyAttendanceModel> subAttendance = subquery.from(DailyAttendanceModel.class);
//               Predicate statusCondition=null;
//			    System.out.println("outer");
//	              if ("sick".equalsIgnoreCase(dailyAttendanceFilterDto.getMonthlyAbsence())) {
//			    	 System.out.println("if inner");
//			    	 	statusCondition = cb.equal(
//			        		dailyAttendanceAndStudentJoin.get(WebServiceUtil.SICK_LEAVE), WebServiceUtil.YES
//			        );
//			    } 
//			    else if ("activity".equalsIgnoreCase(dailyAttendanceFilterDto.getMonthlyAbsence())) {
//			    	statusCondition = cb.equal(
//			            dailyAttendanceAndStudentJoin.get(WebServiceUtil.EXTRA_CURRICULAR_ACTIVITIES), WebServiceUtil.YES
//			        );
//			    }
//			    Predicate monthCondition = cb.equal(cb.function("MONTH", Integer.class, dailyAttendanceAndStudentJoin.get("attendanceDate")),dailyAttendanceFilterDto.getMonth() );
//			    Predicate yearCondition = cb.equal(cb.function("YEAR", Integer.class, dailyAttendanceAndStudentJoin.get("attendanceDate")), dailyAttendanceFilterDto.getYear());
//			    Predicate classCondition = cb.equal(subAttendance.get("studentModel").get("classOfStudy"), dailyAttendanceFilterDto.getClassOfStudy());
// 
//			    subquery.select(subAttendance.get("studentModel").get("studentId")
//			    		       ).where(statusCondition, monthCondition, yearCondition,classCondition
//			            )
//			            .groupBy(subAttendance.get("studentModel").get("studentId"))
//			            .having(cb.gt(cb.count(subAttendance), dailyAttendanceFilterDto.getRange()));		   
//			    Predicate inSubquery = studentRoot.get("studentId").in(subquery);
//
//			    cq.multiselect(
//			    		studentRoot.get("studentId").alias("studentId"),
//			    		studentRoot.get("firstName").alias("firstName"),
//			    		studentRoot.get("middleName").alias("middleName"),
//			    		studentRoot.get("lastName").alias("lastName")		            
//			    ).distinct(true)
//			     .where(inSubquery);
//
//			    List<Tuple> studentDetailsList= entityManager.createQuery(cq).getResultList();
//			    
//			    List<MonthlyAbsenceDto> monthlyAbsenceList = new ArrayList<>();
//			    for (Tuple tuple : studentDetailsList) {
//		            Long studentId = tuple.get("studentId", Long.class);
//		            List<LocalDate> absentDates = entityManager.createQuery(
//		                    "SELECT d.attendanceDate FROM DailyAttendanceModel d " +
//		                    "WHERE d.studentModel.studentId = :studentId " +
//		                    "AND d.attendanceStatus = 'A' " +
//		                    "AND MONTH(d.attendanceDate) = :month " +
//		                    "AND YEAR(d.attendanceDate) = :year", LocalDate.class) 
//		            		
//		                    .setParameter("studentId", studentId)
//		                    .setParameter("month", dailyAttendanceFilterDto.getMonth())
//		                    .setParameter("year", dailyAttendanceFilterDto.getYear())
//		                    .getResultList();
//			    
//		            monthlyAbsenceList.add(new MonthlyAbsenceDto(
//		            		tuple.get("studentId", Long.class),
//		                    tuple.get("firstName", String.class),
//		                    tuple.get("middleName", String.class),
//		                    tuple.get("lastName", String.class),
//
//		                    absentDates
//		            ));
//		        }
//
//		        return monthlyAbsenceList;
//}
	
	/**
	 * Retrieve Monthly absence ,sick leave and extra acticity list in a given month.
	 */
	@Override
	public List<MonthlyAbsenceDto> getMonthlyAbsenceStudents(DailyAttendanceFilterDto dailyAttendanceFilterDto) {

	    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    
	    //Total working days
	    CriteriaQuery<Long> workingDaysQuery = cb.createQuery(Long.class);
	    Root<DailyAttendanceModel> workingDaysRoot = workingDaysQuery.from(DailyAttendanceModel.class);
	    Predicate monthConditionWorking = cb.equal(cb.function("MONTH", Integer.class, workingDaysRoot.get("attendanceDate")), dailyAttendanceFilterDto.getMonth());
	    Predicate yearConditionWorking = cb.equal(cb.function("YEAR", Integer.class, workingDaysRoot.get("attendanceDate")), dailyAttendanceFilterDto.getYear());
	    Predicate classConditionWorking = cb.equal(workingDaysRoot.get("studentModel").get("classOfStudy"), dailyAttendanceFilterDto.getClassOfStudy());

	    workingDaysQuery.select(cb.countDistinct(workingDaysRoot.get("attendanceDate")))
	            .where(monthConditionWorking, yearConditionWorking, classConditionWorking);

	    Long totalWorkingDays = entityManager.createQuery(workingDaysQuery).getSingleResult();
	    
	    //Absence list
	    CriteriaQuery<Tuple> cq = cb.createTupleQuery();
	    Root<StudentModel> studentRoot = cq.from(StudentModel.class);
	    Join<StudentModel, DailyAttendanceModel> dailyAttendanceJoin = studentRoot.join("dailyAttendanceModel");
	    
	    String attendanceStatus=null;
	   
	    List<Predicate> conditions = new ArrayList<>();
	    conditions.add(cb.equal(cb.function("MONTH", Integer.class, dailyAttendanceJoin.get("attendanceDate")),
	            dailyAttendanceFilterDto.getMonth()));
	    conditions.add(cb.equal(cb.function("YEAR", Integer.class, dailyAttendanceJoin.get("attendanceDate")),
	            dailyAttendanceFilterDto.getYear()));
	    conditions.add(cb.equal(studentRoot.get("classOfStudy"), dailyAttendanceFilterDto.getClassOfStudy()));

	    // Conditional extra filters
	    if (dailyAttendanceFilterDto.getMonthlyAbsence() != null) {
	        String type = dailyAttendanceFilterDto.getMonthlyAbsence();

	        if ("sick".equalsIgnoreCase(type)) {
	            conditions.add(cb.equal(dailyAttendanceJoin.get(WebServiceUtil.SICK_LEAVE), WebServiceUtil.YES));
	            attendanceStatus="AND d.longApprovedSickLeaveFlag = 'Y' ";
	            
	        } else if ("activity".equalsIgnoreCase(type)) {
	            conditions.add(cb.equal(dailyAttendanceJoin.get(WebServiceUtil.EXTRA_CURRICULAR_ACTIVITIES), WebServiceUtil.YES));
	            attendanceStatus="AND d.approvedExtraCurricularActivitiesFlag = 'Y' ";
	        }
	    } else {
	        // Default → Absent students
	        conditions.add(cb.equal(dailyAttendanceJoin.get("attendanceStatus").get("code"), WebServiceUtil.ABSENT));
	        attendanceStatus="AND d.attendanceStatus = 'ABSENT' ";
	    }

	    // Select data
	    cq.multiselect(
	            studentRoot.get("studentId").alias("studentId"),
	            studentRoot.get("firstName").alias("firstName"),
	            studentRoot.get("middleName").alias("middleName"),
	            studentRoot.get("lastName").alias("lastName"),
	            cb.count(dailyAttendanceJoin).alias("absenceCount")
	    )
	    .where(conditions.toArray(new Predicate[0]))
	    .groupBy(studentRoot.get("studentId"),
	             studentRoot.get("firstName"),
	             studentRoot.get("middleName"),
	             studentRoot.get("lastName"));

	    List<Tuple> results = entityManager.createQuery(cq).getResultList();

	    //Fetch absent dates per student
	    List<MonthlyAbsenceDto> monthlyAbsenceList = new ArrayList<>();
	    for (Tuple tuple : results) {
	        Long studentId = tuple.get("studentId", Long.class);

	        List<LocalDate> absentDates = entityManager.createQuery(
	                "SELECT d.attendanceDate FROM DailyAttendanceModel d " +
	                        "WHERE d.studentModel.studentId = :studentId " +
                      	//	"AND d.attendanceStatus = 'ABSENT' "
                      	attendanceStatus +
	                        "AND MONTH(d.attendanceDate) = :month " +
	                        "AND YEAR(d.attendanceDate) = :year", LocalDate.class)
	                .setParameter("studentId", studentId)
	                .setParameter("month", dailyAttendanceFilterDto.getMonth())
	                .setParameter("year", dailyAttendanceFilterDto.getYear())
	                .getResultList();

	        monthlyAbsenceList.add(new MonthlyAbsenceDto(
	                studentId,
	                tuple.get("firstName", String.class),
	                tuple.get("middleName", String.class),
	                tuple.get("lastName", String.class),
	                totalWorkingDays,
	                tuple.get("absenceCount", Long.class).intValue(),
	                absentDates
	        ));
	    }
	    

	    return monthlyAbsenceList;
	}

}
