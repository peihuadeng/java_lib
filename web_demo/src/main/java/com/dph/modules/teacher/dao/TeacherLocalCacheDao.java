package com.dph.modules.teacher.dao;

import org.springframework.stereotype.Component;

import com.dph.common.cache.local.LocalCacheDao;
import com.dph.modules.teacher.entity.Teacher;

@Component
public class TeacherLocalCacheDao extends LocalCacheDao<Teacher, TeacherMapper> {
}
