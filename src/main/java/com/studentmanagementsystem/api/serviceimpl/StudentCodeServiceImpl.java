package com.studentmanagementsystem.api.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentmanagementsystem.api.model.custom.Response;
import com.studentmanagementsystem.api.model.custom.studentcode.StudentCodeDto;
import com.studentmanagementsystem.api.model.entity.StudentCodeModel;
import com.studentmanagementsystem.api.model.entity.TeacherModel;
import com.studentmanagementsystem.api.repository.StudentCodeRespository;
import com.studentmanagementsystem.api.repository.TeacherRepository;
import com.studentmanagementsystem.api.service.StudentCodeService;
import com.studentmanagementsystem.api.util.WebServiceUtil;

@Service
public class StudentCodeServiceImpl implements StudentCodeService {
	
	
	@Autowired
	private StudentCodeRespository studentCodeRespository;
	
	@Autowired
	private TeacherRepository teacherRepository;

	@Override
	public Response addStudentCode(List<StudentCodeDto> studentCodeDto) {
		
		Response response = new Response();
				
		
		
		for(StudentCodeDto code : studentCodeDto ) {
			
			StudentCodeModel studentCode = studentCodeRespository.findStudentCodeByCode(code.getCode());
			if(studentCode == null) {
				studentCode = new StudentCodeModel();
				TeacherModel teacher = teacherRepository.findTeacherIdByTeacherId(code.getTeacherId());
				if(teacher == null) {
					response.setStatus(WebServiceUtil.WARNING);
					response.setData(WebServiceUtil.TEACHER_ID_ERROR);
				}
			}
			
		}
		return null;
	}

	

}
