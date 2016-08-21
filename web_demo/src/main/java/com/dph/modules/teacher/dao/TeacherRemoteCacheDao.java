package com.dph.modules.teacher.dao;

import org.springframework.stereotype.Component;

import com.dph.common.cache.local.RemoteCacheDao;
import com.dph.modules.teacher.entity.Teacher;

@Component
public class TeacherRemoteCacheDao extends RemoteCacheDao<Teacher, TeacherMapper> {
}
