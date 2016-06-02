package com.dph.modules.student.dao;

import com.dph.annotation.MybatisDao;
import com.dph.common.persistence.BaseDao;
import com.dph.modules.student.entity.Student;

@MybatisDao
public interface StudentMapper extends BaseDao<Student> {
}