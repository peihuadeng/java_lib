package com.dph.system.shiro.realm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.dph.system.permission.entity.Permission;
import com.dph.system.permission.service.PermissionService;
import com.dph.system.role.entity.Role;
import com.dph.system.role.service.RoleService;
import com.dph.system.user.entity.User;

public class WebCasRealm extends CasRealm {

	private RoleService roleService;
	private PermissionService permissionService;

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

	//授权信息获取
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String username = (String)principals.getPrimaryPrincipal();
		
		User user = new User();
		user.setUsername(username);
		List<Role> roleList = roleService.findRolesByUser(user);
		
		Set<String> roleSet = new HashSet<String>();
		Set<String> permissionSet = new HashSet<String>();
		for (Role role : roleList) {
			if (Boolean.FALSE.equals(role.getAvailable())) {
				continue;//不可用的角色
			}
			roleSet.add(role.getRole());
			
			List<Permission> permissionList = permissionService.findPermissionsByRole(role);
			for(Permission permission : permissionList) {
				if (Boolean.FALSE.equals(permission.getAvailable())) {
					continue;//不可用的权限
				}
				permissionSet.add(permission.getPermission());
			}
		}

		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.setRoles(roleSet);
		authorizationInfo.setStringPermissions(permissionSet);
		
		return authorizationInfo;
	}


}
