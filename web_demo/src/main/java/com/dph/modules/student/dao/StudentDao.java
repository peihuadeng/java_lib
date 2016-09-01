package com.dph.modules.student.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dph.common.cache.CacheDao;
import com.dph.common.cache.CacheModeEnum;
import com.dph.modules.student.entity.Student;

@Component
public class StudentDao implements CacheDao<Student> {
	
	@Value("${cache.mode:None}")
	private CacheModeEnum cacheModel;
	
	@Autowired
	private StudentMapper studentMapper;
	
	@Autowired
	private StudentLocalCacheDao studentLocalCacheDao;
	
	@Autowired
	private StudentRemoteCacheDao studentRemoteCacheDao;

	@Override
	public int insert(Student student) {
		switch(cacheModel) {
		case None:
			return studentMapper.insert(student);
		case Local:
			return studentLocalCacheDao.insert(student);
		case Remote:
			return studentRemoteCacheDao.insert(student);
		default:
			throw new RuntimeException("wrong cache model");
		}
	}

	@Override
	public int insertSelective(Student student) {
		switch(cacheModel) {
		case None:
			return studentMapper.insertSelective(student);
		case Local:
			return studentLocalCacheDao.insertSelective(student);
		case Remote:
			return studentRemoteCacheDao.insertSelective(student);
		default:
			throw new RuntimeException("wrong cache model");
		}
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		switch(cacheModel) {
		case None:
			return studentMapper.deleteByPrimaryKey(id);
		case Local:
			return studentLocalCacheDao.deleteByPrimaryKey(id);
		case Remote:
			return studentRemoteCacheDao.deleteByPrimaryKey(id);
		default:
			throw new RuntimeException("wrong cache model");
		}
	}

	@Override
	public int updateByPrimaryKeySelective(Student student) {
		switch(cacheModel) {
		case None:
			return studentMapper.updateByPrimaryKeySelective(student);
		case Local:
			return studentLocalCacheDao.updateByPrimaryKeySelective(student);
		case Remote:
			return studentRemoteCacheDao.updateByPrimaryKeySelective(student);
		default:
			throw new RuntimeException("wrong cache model");
		}
	}

	@Override
	public int updateByPrimaryKey(Student student) {
		switch(cacheModel) {
		case None:
			return studentMapper.updateByPrimaryKey(student);
		case Local:
			return studentLocalCacheDao.updateByPrimaryKey(student);
		case Remote:
			return studentRemoteCacheDao.updateByPrimaryKey(student);
		default:
			throw new RuntimeException("wrong cache model");
		}
	}

	@Override
	public Student selectByPrimaryKey(String id) {
		switch(cacheModel) {
		case None:
			return studentMapper.selectByPrimaryKey(id);
		case Local:
			return studentLocalCacheDao.selectByPrimaryKey(id);
		case Remote:
			return studentRemoteCacheDao.selectByPrimaryKey(id);
		default:
			throw new RuntimeException("wrong cache model");
		}
	}

	@Override
	public Student selectSimpleByPrimaryKey(String id) {
		switch(cacheModel) {
		case None:
			return studentMapper.selectSimpleByPrimaryKey(id);
		case Local:
			return studentLocalCacheDao.selectSimpleByPrimaryKey(id);
		case Remote:
			return studentRemoteCacheDao.selectSimpleByPrimaryKey(id);
		default:
			throw new RuntimeException("wrong cache model");
		}
	}

	@Override
	public List<Student> select(Student student) {
		switch(cacheModel) {
		case None:
			return studentMapper.select(student);
		case Local:
			return studentLocalCacheDao.select(student);
		case Remote:
			return studentRemoteCacheDao.select(student);
		default:
			throw new RuntimeException("wrong cache model");
		}
	}

	@Override
	public List<Student> selectSimple(Student student) {
		switch(cacheModel) {
		case None:
			return studentMapper.selectSimple(student);
		case Local:
			return studentLocalCacheDao.selectSimple(student);
		case Remote:
			return studentRemoteCacheDao.selectSimple(student);
		default:
			throw new RuntimeException("wrong cache model");
		}
	}

	@Override
	public Student selectByPrimaryKey(String id, int level) {
		switch(cacheModel) {
		case Local:
			return studentLocalCacheDao.selectByPrimaryKey(id, level);
		case Remote:
			return studentRemoteCacheDao.selectByPrimaryKey(id, level);
		default:
			throw new RuntimeException("wrong cache model");
		}
	}

}
