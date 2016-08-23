package com.dph.modules.area.dao;

import org.springframework.stereotype.Component;

import com.dph.common.cache.RemoteCacheDao;
import com.dph.modules.area.entity.Area;

@Component
public class AreaRemoteCacheDao extends RemoteCacheDao<Area, AreaMapper> {
}
