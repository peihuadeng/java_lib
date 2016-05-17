package com.dph.modules.cross_db_tm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dph.modules.cross_db_tm.service.CrossService;
import com.dph.modules.student.dao.StudentMapper;
import com.dph.modules.student.entity.Student;
import com.dph.modules.user.dao.UserMapper;
import com.dph.modules.user.entity.User;

@Service
@Transactional(readOnly = true)
public class CrossServiceImpl implements CrossService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private StudentMapper studentMapper;

	@Override
	@Transactional(readOnly = false)
	public int update(User user, Student student) {
		int result = userMapper.updateByPrimaryKey(user);
		result += studentMapper.updateByPrimaryKey(student);
		int i = 1 / 0;

		return result;
	}

}
