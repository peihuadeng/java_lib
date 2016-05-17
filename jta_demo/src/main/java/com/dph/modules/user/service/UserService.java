package com.dph.modules.user.service;

import java.util.List;

import com.dph.modules.user.entity.User;

public interface UserService {

	public List<User> getAll();

	public User get(int id);

	public int delete(int id);

	public int save(User user);
}
