package com.dph.system.shiro.realm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.dph.system.permission.entity.Permission;
import com.dph.system.permission.service.PermissionService;
import com.dph.system.role.entity.Role;
import com.dph.system.role.service.RoleService;
import com.dph.system.user.entity.User;
import com.dph.system.user.service.UserService;

/**
 * Realm实现
 *
 */
public class UserRealm extends AuthorizingRealm {
	
	private UserService userService;
	private RoleService roleService;
	private PermissionService permissionService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

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

	//身份认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();
		
		User condition = new User();
		condition.setUsername(username);
		List<User> userList = userService.getList(condition);
		
		if (userList.size() == 0) {
			throw new UnknownAccountException();//没有找到帐号
		}
		
		User user = userList.get(0);
		if (Boolean.TRUE.equals(user.getLocked())) {
			throw new LockedAccountException();//帐号锁定
		}
		
		//交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，可以自定义实现
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				user.getUsername(),//用户名
				user.getPassword(),//密码
				ByteSource.Util.bytes(user.getCredentialsSalt()),//salt = username+salt
				getName()//realm name
				);
		
		return authenticationInfo;
	}

}
