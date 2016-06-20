package com.dph.modules.teacher.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dph.common.cache.CacheModeEnum;
import com.dph.common.persistence.BaseDao;
import com.dph.modules.teacher.entity.Teacher;

@Component
public class TeacherDao implements BaseDao<Teacher> {
	
	@Value("${cache.mode:Local}")
	private CacheModeEnum cacheModel;
	
	@Autowired
	private TeacherMapper teacherMapper;
	
	@Autowired
	private TeacherLocalCacheDao teacherLocalCacheDao;

	@Override
	public int insert(Teacher teacher) {
		switch(cacheModel) {
		case None:
			return teacherMapper.insert(teacher);
		case Local:
			return teacherLocalCacheDao.insert(teacher);
		default:
			throw new RuntimeException("wrong cache model");
		}
	}

	@Override
	public int insertSelective(Teacher teacher) {
		switch(cacheModel) {
		case None:
			return teacherMapper.insertSelective(teacher);
		case Local:
			return teacherLocalCacheDao.insertSelective(teacher);
		default:
			throw new RuntimeException("wrong cache model");
		}
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		switch(cacheModel) {
		case None:
			return teacherMapper.deleteByPrimaryKey(id);
		case Local:
			return teacherLocalCacheDao.deleteByPrimaryKey(id);
		default:
			throw new RuntimeException("wrong cache model");
		}
	}

	@Override
	public int updateByPrimaryKeySelective(Teacher teacher) {
		switch(cacheModel) {
		case None:
			return teacherMapper.updateByPrimaryKeySelective(teacher);
		case Local:
			return teacherLocalCacheDao.updateByPrimaryKeySelective(teacher);
		default:
			throw new RuntimeException("wrong cache model");
		}
	}

	@Override
	public int updateByPrimaryKey(Teacher teacher) {
		switch(cacheModel) {
		case None:
			return teacherMapper.updateByPrimaryKey(teacher);
		case Local:
			return teacherLocalCacheDao.updateByPrimaryKey(teacher);
		default:
			throw new RuntimeException("wrong cache model");
		}
	}

	@Override
	public Teacher selectByPrimaryKey(String id) {
		switch(cacheModel) {
		case None:
			return teacherMapper.selectByPrimaryKey(id);
		case Local:
			return teacherLocalCacheDao.selectByPrimaryKey(id);
		default:
			throw new RuntimeException("wrong cache model");
		}
	}

	@Override
	public Teacher selectSimpleByPrimaryKey(String id) {
		switch(cacheModel) {
		case None:
			return teacherMapper.selectSimpleByPrimaryKey(id);
		case Local:
			return teacherLocalCacheDao.selectSimpleByPrimaryKey(id);
		default:
			throw new RuntimeException("wrong cache model");
		}
	}

	@Override
	public List<Teacher> select(Teacher teacher) {
		switch(cacheModel) {
		case None:
			return teacherMapper.select(teacher);
		case Local:
			return teacherLocalCacheDao.select(teacher);
		default:
			throw new RuntimeException("wrong cache model");
		}
	}

	@Override
	public List<Teacher> selectSimple(Teacher teacher) {
		switch(cacheModel) {
		case None:
			return teacherMapper.selectSimple(teacher);
		case Local:
			return teacherLocalCacheDao.selectSimple(teacher);
		default:
			throw new RuntimeException("wrong cache model");
		}
	}
}