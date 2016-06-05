package com.dph.modules.teacher.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dph.common.service.BaseService;
import com.dph.modules.teacher.dao.TeacherDao;
import com.dph.modules.teacher.entity.Teacher;

@Service
@Transactional(readOnly=true)
public class TeacherService extends BaseService<Teacher, TeacherDao> {
}
