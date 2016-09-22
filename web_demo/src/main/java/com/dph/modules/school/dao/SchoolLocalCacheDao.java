package com.dph.modules.school.dao;

import org.springframework.stereotype.Component;

import com.dph.common.cache.dao.LocalCacheDao;
import com.dph.modules.school.entity.School;

@Component
public class SchoolLocalCacheDao extends LocalCacheDao<School, SchoolMapper> {
}
