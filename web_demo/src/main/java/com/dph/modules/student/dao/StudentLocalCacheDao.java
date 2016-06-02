package com.dph.modules.student.dao;

import org.springframework.stereotype.Component;

import com.dph.common.cache.local.LocalCacheDao;
import com.dph.modules.student.entity.Student;

@Component
public class StudentLocalCacheDao extends LocalCacheDao<Student, StudentMapper> {
}
