package com.studentmanagementsystem.api.daoimpl;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.studentmanagementsystem.api.dao.StudentRequestDao;
import com.studentmanagementsystem.api.model.custom.student.StudentListRequestDto;
import com.studentmanagementsystem.api.model.custom.student.StudentSaveRequestDto;
import com.studentmanagementsystem.api.model.entity.StudentModel;
import com.studentmanagementsystem.api.model.entity.TeacherModel;
import com.studentmanagementsystem.api.repository.StudentModelRepository;
import com.studentmanagementsystem.api.repository.TeacherRepository;
import com.studentmanagementsystem.api.util.WebServiceUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
@Repository
@Transactional
public class StudentRequestDaoImpl  implements StudentRequestDao{
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private TeacherRepository teacherRepo;
	
	@Autowired
	private StudentModelRepository studentModelRepository;

	@Override
	public List<StudentListRequestDto> listAllDetailsStudent() {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<StudentListRequestDto> cq= cb.createQuery(StudentListRequestDto.class);
		Root<StudentModel> root = cq.from(StudentModel.class);
//		Join<StudentModel, TeacherModel> stteach = root.join("teacherModel");
		
		cq.multiselect(
				root.get("studentId"),
				root.get("studentFirstName"),
				root.get("studentMiddleName"),
				root.get("studentLastName"),
				root.get("studentGender"),
				root.get("studentDateOfBirth"),
				root.get("studentClassOfStudy"),
				root.get("studentResidingStatus"),
				root.get("studentPhoneNumber"),
				root.get("emergencyContactPersonName"),
				root.get("emergencyContactPhoneNumber"),
				root.get("homeStreetName"),
				root.get("homeCityName"),
				root.get("homePostalCode"),
				root.get("studentActiveStatus"),
				root.get("studentEmail")
			
				);
		
	
		
		
		return entityManager.createQuery(cq).getResultList();
	}

	@Override
	public Object saveStudent(StudentSaveRequestDto studentSaveRequestDto) {
     
		
		LocalDate today=LocalDate.now();
		StudentModel student;
		
		if(studentSaveRequestDto.getStudentId() == null)
		{
		    student=new StudentModel();
			student.setStudentCreateDate(today);	
			TeacherModel teacherData=teacherRepo.getTeacherByTeacherId(studentSaveRequestDto.getTeacherId());
			student.setTeacherModel(teacherData);
		}	
		
		else {
			
			Optional<StudentModel> student1=studentModelRepository.findById(studentSaveRequestDto.getStudentId());
		       student = student1.get();
			student.setUpdateDate(today);
			student.setUpdateTeacher(studentSaveRequestDto.getTeacherId());
			
			
		}
		student.setStudentFirstName(studentSaveRequestDto.getStudentFirstName());
		student.setStudentMiddleName(studentSaveRequestDto.getStudentMiddleName());
		student.setStudentLastName(studentSaveRequestDto.getStudentLastName());
		student.setStudentDateOfBirth(studentSaveRequestDto.getStudentDateOfBirth());
		student.setStudentGender(studentSaveRequestDto.getStudentGender());
		student.setStudentClassOfStudy(studentSaveRequestDto.getStudentClassOfStudy());
		student.setStudentResidingStatus(studentSaveRequestDto.getStudentResidingStatus());
		student.setStudentPhoneNumber(studentSaveRequestDto.getStudentPhoneNumber());
		student.setEmergencyContactPersonName(studentSaveRequestDto.getEmergencyContactPersonName());
		student.setEmergencyContactPhoneNumber(studentSaveRequestDto.getEmergencyContactPhoneNumber());
		student.setHomeStreetName(studentSaveRequestDto.getHomeStreetName());
		student.setHomeCityName(studentSaveRequestDto.getHomeCityName());
		student.setHomePostalCode(studentSaveRequestDto.getHomePostalCode());
		student.setStudentEmail(studentSaveRequestDto.getStudentEmail());

		return studentModelRepository.save(student) ;
	}

	@Override
	public List<StudentListRequestDto> getAllHostelStudents(char studentActiveStatus, char hostel) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<StudentListRequestDto> cq= cb.createQuery(StudentListRequestDto.class);
		Root<StudentModel> root = cq.from(StudentModel.class);
		
	       Predicate residingStatus = cb.equal(root.get("studentResidingStatus"), hostel);
	        Predicate activeStatus = cb.equal(root.get("studentActiveStatus"),studentActiveStatus );

	        cq.multiselect(
					root.get("studentId"),
					root.get("studentFirstName"),
					root.get("studentMiddleName"),
					root.get("studentLastName"),
					root.get("studentGender"),
					root.get("studentDateOfBirth"),
					root.get("studentClassOfStudy"),
					root.get("studentResidingStatus"),
					root.get("studentPhoneNumber"),
					root.get("emergencyContactPersonName"),
					root.get("emergencyContactPhoneNumber"),
					root.get("homeStreetName"),
					root.get("homeCityName"),
					root.get("homePostalCode"),
					root.get("studentActiveStatus"),
					root.get("studentEmail")
				
					).where(cb.and(residingStatus, activeStatus));
		
		
		return entityManager.createQuery(cq).getResultList();
	}

	@Override
	public List<StudentListRequestDto> getStudentsBy(Long studentId, String studentEmail, String studentPhoneNumber) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<StudentListRequestDto> cq= cb.createQuery(StudentListRequestDto.class);
		Root<StudentModel> root = cq.from(StudentModel.class);
		cq.multiselect(
				root.get("studentId"),
				root.get("studentFirstName"),
				root.get("studentMiddleName"),
				root.get("studentLastName"),
				root.get("studentGender"),
				root.get("studentDateOfBirth"),
				root.get("studentClassOfStudy"),
				root.get("studentResidingStatus"),
				root.get("studentPhoneNumber"),
				root.get("emergencyContactPersonName"),
				root.get("emergencyContactPhoneNumber"),
				root.get("homeStreetName"),
				root.get("homeCityName"),
				root.get("homePostalCode"),
				root.get("studentActiveStatus"),
				root.get("studentEmail")
			
				);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if(studentId!= null) {
			predicates.add(cb.equal(root.get("studentId"), studentId));
		}
		if(studentEmail!=null) {
			predicates.add(cb.equal(root.get("studentEmail"), studentEmail));
		}
		if(studentPhoneNumber!=null) {
			predicates.add(cb.equal(root.get("studentPhoneNumber"), studentPhoneNumber));
		}
		
		if(!predicates.isEmpty()) {
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		}
		
		return entityManager.createQuery(cq).getResultList();
	}

	@Override
	public List<StudentListRequestDto> getBystudentStatus(char studentActiveStatus) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<StudentListRequestDto> cq= cb.createQuery(StudentListRequestDto.class);
		Root<StudentModel> root = cq.from(StudentModel.class);
		
		Predicate predictes = cb.equal(root.get("studentActiveStatus"),studentActiveStatus);
		cq.multiselect(
				root.get("studentId"),
				root.get("studentFirstName"),
				root.get("studentMiddleName"),
				root.get("studentLastName"),
				root.get("studentGender"),
				root.get("studentDateOfBirth"),
				root.get("studentClassOfStudy"),
				root.get("studentResidingStatus"),
				root.get("studentPhoneNumber"),
				root.get("emergencyContactPersonName"),
				root.get("emergencyContactPhoneNumber"),
				root.get("homeStreetName"),
				root.get("homeCityName"),
				root.get("homePostalCode"),
				root.get("studentActiveStatus"),
				root.get("studentEmail")
			
				).where(predictes);
		
		return entityManager.createQuery(cq).getResultList();
	}

	@Override
	public Object activeOrDeactiveByStudentId(char studentActiveStatus, Long studentId) {
	    LocalDate today = LocalDate.now();
	    Optional<StudentModel> student1 = studentModelRepository.findById(studentId);

	    if (student1.isEmpty()) {
	        throw new RuntimeException("Student not found with ID: " + studentId);
	    }

	    StudentModel student = student1.get();

	    if (studentActiveStatus == WebServiceUtil.ACTIVE && 
	            studentActiveStatus != student.getStudentActiveStatus()) {

	        student.setStudentActiveStatus(studentActiveStatus);
	        student.setLasteffectivedate(null);
	    } 
	    else if (studentActiveStatus == WebServiceUtil.DEACTIVE && 
	               studentActiveStatus != student.getStudentActiveStatus()) {

	        student.setStudentActiveStatus(studentActiveStatus);
	        student.setLasteffectivedate(today);
	    } 
	    else {
	    	throw new RuntimeException("No need to change");
	    }
	    return studentModelRepository.save(student);
	}






}
