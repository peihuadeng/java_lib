package com.dph.modules.student.dao;

import org.springframework.stereotype.Component;

import com.dph.common.cache.local.RemoteCacheDao;
import com.dph.modules.student.entity.Student;

@Component
public class StudentRemoteCacheDao extends RemoteCacheDao<Student, StudentMapper> {
}
