package com.dph.modules.user.dao;

import java.util.List;

import com.dph.annotation.MybatisDao;
import com.dph.modules.user.entity.User;

@MybatisDao
public interface UserMapper {

	List<User> selectAll();

	int deleteByPrimaryKey(Integer id);

	int insert(User user);

	int insertSelective(User user);

	User selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(User user);

	int updateByPrimaryKey(User user);
}