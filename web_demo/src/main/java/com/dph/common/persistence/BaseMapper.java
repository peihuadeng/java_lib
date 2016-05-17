package com.dph.common.persistence;

import java.util.List;

public interface BaseMapper<T> {

	int insert(T t);

	int insertSelective(T t);

	int deleteByPrimaryKey(String id);

	int updateByPrimaryKeySelective(T t);

	int updateByPrimaryKey(T t);

	T selectByPrimaryKey(String id);

	public List<T> select(T t);

}
