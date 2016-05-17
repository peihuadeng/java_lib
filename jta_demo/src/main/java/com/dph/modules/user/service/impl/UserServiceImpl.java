package com.dph.modules.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dph.modules.user.dao.UserMapper;
import com.dph.modules.user.entity.User;
import com.dph.modules.user.service.UserService;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	@Transactional
	public List<User> getAll() {
		return userMapper.selectAll();
	}

	@Override
	public User get(int id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	@Transactional(readOnly = false)
	public int delete(int id) {
		return userMapper.deleteByPrimaryKey(id);
	}

	@Override
	@Transactional(readOnly = false)
	public int save(User user) {
		if (user.getId() != null) {
			int result = userMapper.updateByPrimaryKey(user);
			int i = 1 / 0;
			return result;
		} else {
			return userMapper.insert(user);
		}
	}

}
