package com.dph.modules.student.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dph.common.service.BaseService;
import com.dph.modules.student.dao.StudentDao;
import com.dph.modules.student.entity.Student;

@Service
@Transactional(readOnly=true)
public class StudentService extends BaseService<Student, StudentDao> {
}
