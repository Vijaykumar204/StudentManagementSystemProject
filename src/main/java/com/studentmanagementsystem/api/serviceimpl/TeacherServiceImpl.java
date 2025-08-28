package com.studentmanagementsystem.api.serviceimpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentmanagementsystem.api.dao.TeacherRequestDao;
import com.studentmanagementsystem.api.model.custom.teacher.TeacherModelListDto;
import com.studentmanagementsystem.api.model.custom.teacher.TeacherSaveRequestDto;
import com.studentmanagementsystem.api.service.TeacherService;
@Service
public class TeacherServiceImpl implements TeacherService {
	
	
//	@Autowired
//	private TeacherRepository teacherRepository;

	@Autowired
	private TeacherRequestDao teacherRequestDao;
	
//	@Autowired
//	private TeacherSaveRequestDao teacherSaveRequestDao;
//	
//	@Autowired
//	private TeacherFilterRequestDao teacherFilterRequestDao;
	
	@Override
	public List<TeacherModelListDto> listAllTeachers() {
		// TODO Auto-generated method stub
		return teacherRequestDao.listAllTeachers();
	}


	@Override
	public Object saveTeacher(TeacherSaveRequestDto teacherSaveRequestDto,Long teacherId) {
		// TODO Auto-generated method stub
		return teacherRequestDao.saveTeacher(teacherSaveRequestDto,teacherId);
	}


	@Override
	public List<TeacherModelListDto> filterTeacher(Long teacherId, String teacherName, String teacherPhoneNumber) {
		// TODO Auto-generated method stub
		return teacherRequestDao.filterTeacher(teacherId,teacherName,teacherPhoneNumber);
	}






}
