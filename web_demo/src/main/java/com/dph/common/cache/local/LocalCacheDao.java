package com.dph.common.cache.local;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.dph.common.entity.BaseEntity;
import com.dph.common.persistence.BaseDao;

public abstract class LocalCacheDao<T extends BaseEntity<T>, Mapper extends BaseDao<T>> implements BaseDao<T> {

	@Autowired
	private Mapper mapper;

	@Autowired
	private LocalCacheManager cacheManager;
	private LocalCache<T> localCache;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		boolean success = doInit();
		if (!success) {
			Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
			localCache = cacheManager.getCache("Object:" + entityClass.getName(), entityClass, true);
		}
	}

	protected boolean doInit() {
		return false;
	}

	@Override
	public int insert(T t) {
		return mapper.insert(t);
	}

	@Override
	public int insertSelective(T t) {
		return mapper.insertSelective(t);
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		int result = mapper.deleteByPrimaryKey(id);
		// remove cache
		localCache.remove(id);
		return result;
	}

	@Override
	public int updateByPrimaryKeySelective(T t) {
		int result = mapper.updateByPrimaryKeySelective(t);
		// remove cache
		localCache.remove(t.getId());
		return result;
	}

	@Override
	public int updateByPrimaryKey(T t) {
		int result = mapper.updateByPrimaryKey(t);
		// remove cache
		localCache.remove(t.getId());
		return result;
	}

	@Override
	public T selectByPrimaryKey(String id) {
		// get from cache, if null, get from db and put into cache
		T t = localCache.getValue(id);

		if (t == null) {// get from db and put into cache
			t = mapper.selectByPrimaryKey(id);
			localCache.putValue(id, t);
		}

		return t;
	}

	@Override
	public List<T> select(T t) {
		List<T> list = mapper.select(t);
		for (T tt : list) {
			// TODO:join cache or db
		}
		return list;
	}

}
