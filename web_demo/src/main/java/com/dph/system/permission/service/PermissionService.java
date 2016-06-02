package com.dph.system.permission.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dph.common.service.BaseService;
import com.dph.system.permission.dao.PermissionMapper;
import com.dph.system.permission.entity.Permission;
import com.dph.system.role.entity.Role;

@Service("permissionService")
@Transactional(readOnly = true)
public class PermissionService extends BaseService<Permission, PermissionMapper> {
	//更新权限与角色关联,需要调用userRealm.clearCachedAuthorizationInfo(PrincipalCollection principals)清除身份验证信息缓存

	public List<Permission> findPermissionsByRole(Role role) {
		return dao.findPermissionsByRole(role);
	}
}
