package com.studentmanagementsystem.api.serviceimpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.studentmanagementsystem.api.model.custom.Response;
import com.studentmanagementsystem.api.model.custom.studentcode.StudentCodeDto;
import com.studentmanagementsystem.api.model.entity.StudentCodeModel;
import com.studentmanagementsystem.api.repository.StudentCodeRespository;
import com.studentmanagementsystem.api.service.StudentCodeService;
import com.studentmanagementsystem.api.util.WebServiceUtil;

@Service
public class StudentCodeServiceImpl implements StudentCodeService {
	
	
	@Autowired
	private StudentCodeRespository studentCodeRespository;
	


	@Override
	public Response addStudentCode(List<StudentCodeDto> studentCodeDto) {
		
		Response response = new Response();
		List<StudentCodeModel> codeList = new ArrayList<>();
		LocalDateTime today = LocalDateTime.now();
		
		for(StudentCodeDto code : studentCodeDto ) {
			
			StudentCodeModel studentCode = studentCodeRespository.findStudentCodeByCode(code.getCode());
//			TeacherModel teacher = teacherRepository.findTeacherIdByTeacherId(code.getTeacherId());
//			if(teacher == null) {
//				response.setStatus(WebServiceUtil.WARNING);
//				response.setData(WebServiceUtil.TEACHER_ID_ERROR);
//				return response;
//			}

			if(studentCode == null) {
				studentCode = new StudentCodeModel();
				
				studentCode.setCreateUser(code.getTeacherId());
				studentCode.setCreateDate(today);			
			}
			else {
				studentCode.setUpdateUser(code.getTeacherId());
				studentCode.setUpdatdDate(today);
			}
			
			studentCode.setCode(code.getCode());
			studentCode.setDescription(code.getDescription());
			studentCode.setGroupCode(code.getGroupCode());
			studentCode.setSubGroupCode(code.getSubGroupCode());
			if(code.getIsActiveFlag()!=null) {
			studentCode.setIsActiveFlag(code.getIsActiveFlag());
			}
			codeList.add(studentCode);
				
		}
		studentCodeRespository.saveAll(codeList);
		response.setStatus(WebServiceUtil.SUCCESS);
		response.setData(WebServiceUtil.SAVE);
		return response;
	}



	@Override
	public Response loadStudentCode(String groupCode) {
		
		Response response = new Response();
		response.setStatus(WebServiceUtil.SUCCESS);
		response.setData(studentCodeRespository.findByGroupCode(groupCode));
		
		return response;
	}

	

}
