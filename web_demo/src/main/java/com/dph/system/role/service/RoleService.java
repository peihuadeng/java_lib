package com.dph.system.role.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dph.common.service.BaseService;
import com.dph.system.role.dao.RoleMapper;
import com.dph.system.role.entity.Role;
import com.dph.system.user.entity.User;

@Service("roleService")
@Transactional(readOnly = true)
public class RoleService extends BaseService<Role, RoleMapper> {
	//更新角色与用户关联,需要调用userRealm.clearCachedAuthorizationInfo(PrincipalCollection principals)清除身份验证信息缓存

	public List<Role> findRolesByUser(User user) {
		return mapper.findRolesByUser(user);
	}
}
