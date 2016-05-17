package com.dph.system.role.dao;

import java.util.List;

import com.dph.annotation.MybatisDao;
import com.dph.common.persistence.BaseMapper;
import com.dph.system.role.entity.Role;
import com.dph.system.user.entity.User;

@MybatisDao
public interface RoleMapper extends BaseMapper<Role> {

	List<Role> findRolesByUser(User user);
}