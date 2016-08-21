package com.dph.modules.school.dao;

import org.springframework.stereotype.Component;

import com.dph.common.cache.local.RemoteCacheDao;
import com.dph.modules.school.entity.School;

@Component
public class SchoolRemoteCacheDao extends RemoteCacheDao<School, SchoolMapper> {
}
