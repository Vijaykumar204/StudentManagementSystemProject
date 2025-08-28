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

		CriteriaQuery<Long> working = cb.createQuery(Long.class);
		Root<DailyAttendanceModel> root = working.from(DailyAttendanceModel.class);

		Predicate monthPredicate = cb.function("MONTH", Integer.class, root.get("attendanceDate")).in(quarterMonth);
		Predicate yearPredicate = cb.equal(cb.function("YEAR", Integer.class, root.get("attendanceDate")), 2025);

		working.select(cb.countDistinct(root.get("attendanceDate"))).where(cb.and(monthPredicate, yearPredicate));

		Long toatalWorkingdays = entityManager.createQuery(working).getSingleResult();

		CriteriaQuery<QuarterlyAttendanceReportDto> cq = cb.createQuery(QuarterlyAttendanceReportDto.class);
		Root<DailyAttendanceModel> d = cq.from(DailyAttendanceModel.class);
		Join<DailyAttendanceModel, StudentModel> s = d.join("studentModel");

		Expression<Integer> totalPresent = cb.sum(cb.<Integer>selectCase()
				.when(cb.equal(d.get("attendanceStatus"), WebServiceUtil.PRESENT), 1).otherwise(0));

		Expression<Integer> totalAbsent = cb.sum(cb.<Integer>selectCase()
				.when(cb.equal(d.get("attendanceStatus"), WebServiceUtil.ABSENT), 1).otherwise(0));

		Expression<Integer> totalSick = cb
				.sum(cb.<Integer>selectCase().when(cb.and(cb.equal(d.get("attendanceStatus"), WebServiceUtil.ABSENT),
						cb.equal(d.get("longApprovedSickLeaveFlag"), WebServiceUtil.YES)), 1).otherwise(0));

		Expression<Integer> totalExtra = cb
				.sum(cb.<Integer>selectCase()
						.when(cb.and(cb.equal(d.get("attendanceStatus"), WebServiceUtil.ABSENT),
								cb.equal(d.get("approvedExtraCurricularActivitiesFlag"), WebServiceUtil.YES)), 1)
						.otherwise(0));

		cq.select(cb.construct(QuarterlyAttendanceReportDto.class, cb.literal(toatalWorkingdays), totalPresent,
				totalAbsent, totalExtra, totalSick, s.get("studentId"))).where(cb.and(monthPredicate, yearPredicate))
				.groupBy(s.get("studentId"));

		return entityManager.createQuery(cq).getResultList();
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
//		StudentModel student = studentModelRepository.getStudentByStudentId(studentId);
	QuarterlyAttendanceReportModel quarterlyAttendanceReportModel=quarterlyAttendanceModelRepository.findByStudentAndQuarterAndYear(studentId,quarterAndYear);
//		QuarterlyAttendanceReportModel quarterlyAttendanceReportModel=quarterlyAttendanceModelRepository.findByStudentModel_StudentIdAndQuarterAndYear(studentId,quarterAndYear);
		if(quarterlyAttendanceReportModel == null) {
//			QuarterlyAttendanceReportModel quarterlyAttendanceReportModel = new QuarterlyAttendanceReportModel();
			return new QuarterlyAttendanceReportModel();
		}
		else {
//			QuarterlyAttendanceReportModel quarterlyAttendanceReportModel=quarterlyAttendanceModelRepository.findByStudentId(studentId);
			return quarterlyAttendanceReportModel;
		}
		
	}

	@Override
	public List<ComplianceAndNonComplianceReportDto> getNonComplianceStudents(String quarterAndYear,
			String nonComplianceComment) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ComplianceAndNonComplianceReportDto>  cq=cb.createQuery(ComplianceAndNonComplianceReportDto.class);
		Root<QuarterlyAttendanceReportModel> quarterly = cq.from(QuarterlyAttendanceReportModel.class);
		Join<QuarterlyAttendanceReportModel,StudentModel> student = quarterly.join("studentModel");
		
		Predicate quarterAndYearCondition=cb.equal(quarterly.get("quarterAndYear"), quarterAndYear);
		Predicate statusCondition=cb.equal(quarterly.get("attendanceComplianceStatus"), WebServiceUtil.NON_COMPLIANCE);
		cq.select(cb.construct(ComplianceAndNonComplianceReportDto.class,
				student.get("studentId"),
				quarterly.get("quarterAndYear"),
				student.get("studentFirstName"),
				student.get("studentMiddleName"),
				student.get("studentLastName"),
				quarterly.get("attendanceComplianceStatus")		
				
				)).where(quarterAndYearCondition,statusCondition);
		
		
		
		return entityManager.createQuery(cq).getResultList();
	}


}
