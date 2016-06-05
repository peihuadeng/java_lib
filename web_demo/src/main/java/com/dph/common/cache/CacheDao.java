package com.dph.common.cache;

import com.dph.common.persistence.BaseDao;

public interface CacheDao<T> extends BaseDao<T> {

	public T selectByPrimaryKey(String id, int level);
}
