package com.studentmanagementsystem.api.daoimpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.studentmanagementsystem.api.dao.QuarterlyAttendanceReportDao;
import com.studentmanagementsystem.api.model.custom.quarterlyreport.ComplianceAndNonComplianceReportDto;
import com.studentmanagementsystem.api.model.custom.quarterlyreport.QuarterlyAttendanceReportDto;
import com.studentmanagementsystem.api.model.entity.DailyAttendanceModel;
import com.studentmanagementsystem.api.model.entity.QuarterlyAttendanceReportModel;
import com.studentmanagementsystem.api.model.entity.StudentModel;
import com.studentmanagementsystem.api.repository.QuarterlyAttendanceModelRepository;
import com.studentmanagementsystem.api.repository.StudentModelRepository;
import com.studentmanagementsystem.api.util.WebServiceUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class QuarterlyAttendanceReportDaoImp implements QuarterlyAttendanceReportDao {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private QuarterlyAttendanceModelRepository quarterlyAttendanceModelRepository;

	@Autowired
	private StudentModelRepository studentModelRepository;

//     SELECT 
//    d.Student_Id AS studentId,
//    SUM(CASE WHEN d.AT_Status = 'P' THEN 1 ELSE 0 END) AS totalDaysOfPresent,
//    SUM(CASE WHEN d.AT_Status = 'A' THEN 1 ELSE 0 END) AS totalDaysOfAbsents,
//    SUM(CASE WHEN d.AT_Status = 'A' AND d.AT_Ap_SickLeave = 'Y' THEN 1 ELSE 0 END) AS totalApprovedSickdays,
//    SUM(CASE WHEN d.AT_Status = 'A' AND d.AT_Ap_Extra_Cur_Act = 'Y' THEN 1 ELSE 0 END) AS totalApprovedActivitiesPermissionDays
//	FROM daily_attendance_registration d
//	JOIN student s ON d.Student_Id = s.STU_Id
//	WHERE MONTH(d.AT_Date) IN (1,2,3)
//	  AND YEAR(d.AT_Date) = 2025
//	GROUP BY d.Student_Id;

	@Override
	public List<QuarterlyAttendanceReportDto> getAttendanceSummary(List<Integer> quarterMonth) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> totakWorkingDaysQuery = cb.createQuery(Long.class);
		Root<DailyAttendanceModel> dailyAttendanceRoot = totakWorkingDaysQuery.from(DailyAttendanceModel.class);

		Predicate monthPredicate = cb.function("MONTH", Integer.class, dailyAttendanceRoot.get("attendanceDate")).in(quarterMonth);
		Predicate yearPredicate = cb.equal(cb.function("YEAR", Integer.class, dailyAttendanceRoot.get("attendanceDate")), 2025);

		totakWorkingDaysQuery.select(cb.countDistinct(dailyAttendanceRoot.get("attendanceDate"))).where(cb.and(monthPredicate, yearPredicate));

		Long toatalWorkingdays = entityManager.createQuery(totakWorkingDaysQuery).getSingleResult();

		CriteriaQuery<QuarterlyAttendanceReportDto> quarterlyAttendanceReportQuery = cb.createQuery(QuarterlyAttendanceReportDto.class);
		Root<DailyAttendanceModel> quarterlyDailyAttendanceRoot = quarterlyAttendanceReportQuery.from(DailyAttendanceModel.class);
		Join<DailyAttendanceModel, StudentModel> dailyAttendanceAndStudentJoin = quarterlyDailyAttendanceRoot.join("studentModel");

		Expression<Integer> totalPresent = cb.sum(cb.<Integer>selectCase()
				.when(cb.equal(quarterlyDailyAttendanceRoot.get("attendanceStatus"), WebServiceUtil.PRESENT), 1).otherwise(0));

		Expression<Integer> totalAbsent = cb.sum(cb.<Integer>selectCase()
				.when(cb.equal(quarterlyDailyAttendanceRoot.get("attendanceStatus"), WebServiceUtil.ABSENT), 1).otherwise(0));

		Expression<Integer> totalSick = cb
				.sum(cb.<Integer>selectCase().when(cb.and(cb.equal(quarterlyDailyAttendanceRoot.get("attendanceStatus"), WebServiceUtil.ABSENT),
						cb.equal(quarterlyDailyAttendanceRoot.get("longApprovedSickLeaveFlag"), WebServiceUtil.YES)), 1).otherwise(0));

		Expression<Integer> totalExtra = cb
				.sum(cb.<Integer>selectCase()
						.when(cb.and(cb.equal(quarterlyDailyAttendanceRoot.get("attendanceStatus"), WebServiceUtil.ABSENT),
								cb.equal(quarterlyDailyAttendanceRoot.get("approvedExtraCurricularActivitiesFlag"), WebServiceUtil.YES)), 1)
						.otherwise(0));

		quarterlyAttendanceReportQuery.select(cb.construct(QuarterlyAttendanceReportDto.class, cb.literal(toatalWorkingdays), totalPresent,
				totalAbsent, totalExtra, totalSick, dailyAttendanceAndStudentJoin.get("studentId"))).where(cb.and(monthPredicate, yearPredicate))
				.groupBy(dailyAttendanceAndStudentJoin.get("studentId"));

		return entityManager.createQuery(quarterlyAttendanceReportQuery).getResultList();
	}

	@Override
	public StudentModel getStudentModelByStuId(Long studentId) {
		StudentModel student = studentModelRepository.getStudentByStudentId(studentId);
		return student;
	}

	@Override
	public void saveQuarterlyAttendanceReport(QuarterlyAttendanceReportModel quarterlyAttendanceReportModel) {
		quarterlyAttendanceModelRepository.save(quarterlyAttendanceReportModel);

	}

	@Override
	public QuarterlyAttendanceReportModel getStudentIdUpdateReport(Long studentId, String quarterAndYear) {

	QuarterlyAttendanceReportModel quarterlyAttendanceReportModel=quarterlyAttendanceModelRepository.findByStudentAndQuarterAndYear(studentId,quarterAndYear);
		if(quarterlyAttendanceReportModel == null) {
			return new QuarterlyAttendanceReportModel();
		}
		else {
			return quarterlyAttendanceReportModel;
		}
		
	}

	@Override
	public List<ComplianceAndNonComplianceReportDto> getNonComplianceStudents(String quarterAndYear,
			String complianceStatus) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ComplianceAndNonComplianceReportDto> complianceAndNonComplianceQuery  =cb.createQuery(ComplianceAndNonComplianceReportDto.class);
		Root<QuarterlyAttendanceReportModel> QuarterlyAttendanceReportRoot = complianceAndNonComplianceQuery.from(QuarterlyAttendanceReportModel.class);
		Join<QuarterlyAttendanceReportModel,StudentModel> quarterlyAttendanceReporAndStudentJoin = QuarterlyAttendanceReportRoot.join("studentModel");
		
		Predicate quarterAndYearCondition=cb.equal(QuarterlyAttendanceReportRoot.get("quarterAndYear"), quarterAndYear);
		Predicate statusCondition=cb.equal(QuarterlyAttendanceReportRoot.get("attendanceComplianceStatus"),complianceStatus);
		complianceAndNonComplianceQuery.select(cb.construct(ComplianceAndNonComplianceReportDto.class,
				quarterlyAttendanceReporAndStudentJoin.get("studentId"),
				QuarterlyAttendanceReportRoot.get("quarterAndYear"),
				quarterlyAttendanceReporAndStudentJoin.get("studentFirstName"),
				quarterlyAttendanceReporAndStudentJoin.get("studentMiddleName"),
				quarterlyAttendanceReporAndStudentJoin.get("studentLastName"),
				QuarterlyAttendanceReportRoot.get("attendanceComplianceStatus")		
				
				)).where(quarterAndYearCondition,statusCondition);
		
		
		
		return entityManager.createQuery(complianceAndNonComplianceQuery).getResultList();
	}
	
	//select attendanceComplianceStatus from QuarterlyAttendanceReportModel where studentId = studentId && quarterAndYear=quarterAndYear;

	@Override
	public String getComplianceStatus(Long studentId, String quarterAndYear) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<String> complianceStatusQuery = cb.createQuery(String.class);
		Root<QuarterlyAttendanceReportModel> quarterlyAttendancReportRoot = complianceStatusQuery.from(QuarterlyAttendanceReportModel.class);
		
		Predicate stuCondition = cb.equal(quarterlyAttendancReportRoot.get("studentModel").get("studentId"),studentId );
		Predicate quarterandYearCondition = cb.equal(quarterlyAttendancReportRoot.get("quarterAndYear"),quarterAndYear);
			
		complianceStatusQuery.select(
				quarterlyAttendancReportRoot.get("attendanceComplianceStatus")
				).where(stuCondition,quarterandYearCondition);
		
		return entityManager.createQuery(complianceStatusQuery).getSingleResult();
	}

	@Override
	public List<QuarterlyAttendanceReportDto> getQuarterlyAttendanceReport(String quarterAndYear) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<QuarterlyAttendanceReportDto> quarterlyAttendanceReportQuery  =cb.createQuery(QuarterlyAttendanceReportDto.class);
		Root<QuarterlyAttendanceReportModel> quarterlyAttendanceReportRoot = quarterlyAttendanceReportQuery.from(QuarterlyAttendanceReportModel.class);
		Predicate quarterandYearCondition = cb.equal(quarterlyAttendanceReportRoot.get("quarterAndYear"),quarterAndYear);
		quarterlyAttendanceReportQuery.select(cb.construct(QuarterlyAttendanceReportDto.class,
				 
				quarterlyAttendanceReportRoot.get("studentModel").get("studentId"),
				quarterlyAttendanceReportRoot.get("totalSchoolWorkingDays"),
				quarterlyAttendanceReportRoot.get("totalDaysOfPresent"),
				quarterlyAttendanceReportRoot.get("totalDaysOfAbsents"),				
				quarterlyAttendanceReportRoot.get("totalApprovedActivitiesPermissionDays"),
				quarterlyAttendanceReportRoot.get("totalApprovedSickdays"),
				quarterlyAttendanceReportRoot.get("attendanceComplianceStatus")
				
				)).where(quarterandYearCondition)	;	
		

		
		return entityManager.createQuery(quarterlyAttendanceReportQuery).getResultList();
	}


}
