package com.dph.modules.area.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dph.common.cache.constEnum.CacheModeEnum;
import com.dph.common.cache.dao.CacheDao;
import com.dph.modules.area.entity.Area;

@Component
public class AreaDao implements CacheDao<Area> {
	
	@Value("${cache.mode:None}")
	private CacheModeEnum cacheModel;
	
	@Autowired
	private AreaMapper AreaMapper;
	
	@Autowired
	private AreaLocalCacheDao AreaLocalCacheDao;
	
	@Autowired
	private AreaRemoteCacheDao AreaRemoteCacheDao;

	@Override
	public int insert(Area Area) {
		switch(cacheModel) {
		case None:
			return AreaMapper.insert(Area);
		case Local:
			return AreaLocalCacheDao.insert(Area);
		case Remote:
			return AreaRemoteCacheDao.insert(Area);
		default:
			throw new RuntimeException("wrong cache model");
		}
	}

	@Override
	public int insertSelective(Area Area) {
		switch(cacheModel) {
		case None:
			return AreaMapper.insertSelective(Area);
		case Local:
			return AreaLocalCacheDao.insertSelective(Area);
		case Remote:
			return AreaRemoteCacheDao.insertSelective(Area);
		default:
			throw new RuntimeException("wrong cache model");
		}
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		switch(cacheModel) {
		case None:
			return AreaMapper.deleteByPrimaryKey(id);
		case Local:
			return AreaLocalCacheDao.deleteByPrimaryKey(id);
		case Remote:
			return AreaRemoteCacheDao.deleteByPrimaryKey(id);
		default:
			throw new RuntimeException("wrong cache model");
		}
	}

	@Override
	public int updateByPrimaryKeySelective(Area Area) {
		switch(cacheModel) {
		case None:
			return AreaMapper.updateByPrimaryKeySelective(Area);
		case Local:
			return AreaLocalCacheDao.updateByPrimaryKeySelective(Area);
		case Remote:
			return AreaRemoteCacheDao.updateByPrimaryKeySelective(Area);
		default:
			throw new RuntimeException("wrong cache model");
		}
	}

	@Override
	public int updateByPrimaryKey(Area Area) {
		switch(cacheModel) {
		case None:
			return AreaMapper.updateByPrimaryKey(Area);
		case Local:
			return AreaLocalCacheDao.updateByPrimaryKey(Area);
		case Remote:
			return AreaRemoteCacheDao.updateByPrimaryKey(Area);
		default:
			throw new RuntimeException("wrong cache model");
		}
	}

	@Override
	public Area selectByPrimaryKey(String id) {
		switch(cacheModel) {
		case None:
			return AreaMapper.selectByPrimaryKey(id);
		case Local:
			return AreaLocalCacheDao.selectByPrimaryKey(id);
		case Remote:
			return AreaRemoteCacheDao.selectByPrimaryKey(id);
		default:
			throw new RuntimeException("wrong cache model");
		}
	}

	@Override
	public Area selectSimpleByPrimaryKey(String id) {
		switch(cacheModel) {
		case None:
			return AreaMapper.selectSimpleByPrimaryKey(id);
		case Local:
			return AreaLocalCacheDao.selectSimpleByPrimaryKey(id);
		case Remote:
			return AreaRemoteCacheDao.selectSimpleByPrimaryKey(id);
		default:
			throw new RuntimeException("wrong cache model");
		}
	}

	@Override
	public List<Area> select(Area Area) {
		switch(cacheModel) {
		case None:
			return AreaMapper.select(Area);
		case Local:
			return AreaLocalCacheDao.select(Area);
		case Remote:
			return AreaRemoteCacheDao.select(Area);
		default:
			throw new RuntimeException("wrong cache model");
		}
	}

	@Override
	public List<Area> selectSimple(Area Area) {
		switch(cacheModel) {
		case None:
			return AreaMapper.selectSimple(Area);
		case Local:
			return AreaLocalCacheDao.selectSimple(Area);
		case Remote:
			return AreaRemoteCacheDao.selectSimple(Area);
		default:
			throw new RuntimeException("wrong cache model");
		}
	}

	@Override
	public Area selectByPrimaryKey(String id, int level) {
		switch(cacheModel) {
		case Local:
			return AreaLocalCacheDao.selectByPrimaryKey(id, level);
		case Remote:
			return AreaRemoteCacheDao.selectByPrimaryKey(id, level);
		default:
			throw new RuntimeException("wrong cache model");
		}
	}
}
