package com.dph.modules.area.dao;

import org.springframework.stereotype.Component;

import com.dph.common.cache.dao.LocalCacheDao;
import com.dph.modules.area.entity.Area;

@Component
public class AreaLocalCacheDao extends LocalCacheDao<Area, AreaMapper> {
}
