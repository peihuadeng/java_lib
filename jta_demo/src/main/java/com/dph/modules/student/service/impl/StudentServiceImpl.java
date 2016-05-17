package com.dph.modules.student.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dph.modules.student.dao.StudentMapper;
import com.dph.modules.student.entity.Student;
import com.dph.modules.student.service.StudentService;

@Service
@Transactional(readOnly = true)
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentMapper studentMapper;

	@Override
	public List<Student> getAll() {
		return studentMapper.selectAll();
	}

	@Override
	public Student get(int id) {
		return studentMapper.selectByPrimaryKey(id);
	}

	@Override
	@Transactional(readOnly = false)
	public int remove(int id) {
		return studentMapper.deleteByPrimaryKey(id);
	}

	@Override
	@Transactional(readOnly = false)
	public int save(Student student) {
		if (student.getId() != null) {
			int result = studentMapper.updateByPrimaryKey(student);
			int i = 1 / 0;
			return result;
		} else {
			return studentMapper.insert(student);
		}
	}

}
