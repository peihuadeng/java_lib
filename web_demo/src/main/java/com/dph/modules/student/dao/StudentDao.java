package com.dph.modules.student.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dph.common.cache.CacheModelEnum;
import com.dph.common.persistence.BaseDao;
import com.dph.modules.student.entity.Student;

@Component
public class StudentDao implements BaseDao<Student> {
	
	@Value("${cache.cacheModel:Local}")
	private CacheModelEnum cacheModel;
	
	@Autowired
	private StudentMapper studentMapper;
	
	@Autowired
	private StudentLocalCacheDao studentLocalCacheDao;

	@Override
	public int insert(Student student) {
		switch(cacheModel) {
		case None:
			return studentMapper.insert(student);
		case Local:
			return studentLocalCacheDao.insert(student);
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
		default:
			throw new RuntimeException("wrong cache model");
		}
	}

}
