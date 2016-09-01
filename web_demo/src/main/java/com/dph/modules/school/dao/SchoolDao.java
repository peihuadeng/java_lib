package com.dph.modules.school.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dph.common.cache.CacheDao;
import com.dph.common.cache.CacheModeEnum;
import com.dph.modules.school.entity.School;

@Component
public class SchoolDao implements CacheDao<School> {
	
	@Value("${cache.mode:None}")
	private CacheModeEnum cacheModel;
	
	@Autowired
	private SchoolMapper SchoolMapper;
	
	@Autowired
	private SchoolLocalCacheDao SchoolLocalCacheDao;
	
	@Autowired
	private SchoolRemoteCacheDao SchoolRemoteCacheDao;

	@Override
	public int insert(School School) {
		switch(cacheModel) {
		case None:
			return SchoolMapper.insert(School);
		case Local:
			return SchoolLocalCacheDao.insert(School);
		case Remote:
			return SchoolRemoteCacheDao.insert(School);
		default:
			throw new RuntimeException("wrong cache model");
		}
	}

	@Override
	public int insertSelective(School School) {
		switch(cacheModel) {
		case None:
			return SchoolMapper.insertSelective(School);
		case Local:
			return SchoolLocalCacheDao.insertSelective(School);
		case Remote:
			return SchoolRemoteCacheDao.insertSelective(School);
		default:
			throw new RuntimeException("wrong cache model");
		}
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		switch(cacheModel) {
		case None:
			return SchoolMapper.deleteByPrimaryKey(id);
		case Local:
			return SchoolLocalCacheDao.deleteByPrimaryKey(id);
		case Remote:
			return SchoolRemoteCacheDao.deleteByPrimaryKey(id);
		default:
			throw new RuntimeException("wrong cache model");
		}
	}

	@Override
	public int updateByPrimaryKeySelective(School School) {
		switch(cacheModel) {
		case None:
			return SchoolMapper.updateByPrimaryKeySelective(School);
		case Local:
			return SchoolLocalCacheDao.updateByPrimaryKeySelective(School);
		case Remote:
			return SchoolRemoteCacheDao.updateByPrimaryKeySelective(School);
		default:
			throw new RuntimeException("wrong cache model");
		}
	}

	@Override
	public int updateByPrimaryKey(School School) {
		switch(cacheModel) {
		case None:
			return SchoolMapper.updateByPrimaryKey(School);
		case Local:
			return SchoolLocalCacheDao.updateByPrimaryKey(School);
		case Remote:
			return SchoolRemoteCacheDao.updateByPrimaryKey(School);
		default:
			throw new RuntimeException("wrong cache model");
		}
	}

	@Override
	public School selectByPrimaryKey(String id) {
		switch(cacheModel) {
		case None:
			return SchoolMapper.selectByPrimaryKey(id);
		case Local:
			return SchoolLocalCacheDao.selectByPrimaryKey(id);
		case Remote:
			return SchoolRemoteCacheDao.selectByPrimaryKey(id);
		default:
			throw new RuntimeException("wrong cache model");
		}
	}

	@Override
	public School selectSimpleByPrimaryKey(String id) {
		switch(cacheModel) {
		case None:
			return SchoolMapper.selectSimpleByPrimaryKey(id);
		case Local:
			return SchoolLocalCacheDao.selectSimpleByPrimaryKey(id);
		case Remote:
			return SchoolRemoteCacheDao.selectSimpleByPrimaryKey(id);
		default:
			throw new RuntimeException("wrong cache model");
		}
	}

	@Override
	public List<School> select(School School) {
		switch(cacheModel) {
		case None:
			return SchoolMapper.select(School);
		case Local:
			return SchoolLocalCacheDao.select(School);
		case Remote:
			return SchoolRemoteCacheDao.select(School);
		default:
			throw new RuntimeException("wrong cache model");
		}
	}

	@Override
	public List<School> selectSimple(School School) {
		switch(cacheModel) {
		case None:
			return SchoolMapper.selectSimple(School);
		case Local:
			return SchoolLocalCacheDao.selectSimple(School);
		case Remote:
			return SchoolRemoteCacheDao.selectSimple(School);
		default:
			throw new RuntimeException("wrong cache model");
		}
	}

	@Override
	public School selectByPrimaryKey(String id, int level) {
		switch(cacheModel) {
		case Local:
			return SchoolLocalCacheDao.selectByPrimaryKey(id, level);
		case Remote:
			return SchoolRemoteCacheDao.selectByPrimaryKey(id, level);
		default:
			throw new RuntimeException("wrong cache model");
		}
	}
}
