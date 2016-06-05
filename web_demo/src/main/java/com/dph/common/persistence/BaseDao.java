package com.dph.common.persistence;

import java.util.List;

public interface BaseDao<T> {

	int insert(T t);

	int insertSelective(T t);

	int deleteByPrimaryKey(String id);

	int updateByPrimaryKeySelective(T t);

	int updateByPrimaryKey(T t);

	T selectByPrimaryKey(String id);

	T selectSimpleByPrimaryKey(String id);

	public List<T> select(T t);

	public List<T> selectSimple(T t);

}
