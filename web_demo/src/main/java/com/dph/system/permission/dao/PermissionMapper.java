package com.dph.system.permission.dao;

import java.util.List;

import com.dph.annotation.MybatisDao;
import com.dph.common.persistence.BaseMapper;
import com.dph.system.permission.entity.Permission;
import com.dph.system.role.entity.Role;

@MybatisDao
public interface PermissionMapper extends BaseMapper<Permission> {

	List<Permission> findPermissionsByRole(Role role);
}