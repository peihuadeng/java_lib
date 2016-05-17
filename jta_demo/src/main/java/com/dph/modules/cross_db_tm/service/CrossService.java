package com.dph.modules.cross_db_tm.service;

import com.dph.modules.student.entity.Student;
import com.dph.modules.user.entity.User;

public interface CrossService {

	int update(User user, Student student);

}