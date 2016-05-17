package com.dph.system.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dph.common.service.BaseService;
import com.dph.system.user.dao.UserMapper;
import com.dph.system.user.entity.User;

@Service("userService")
@Transactional(readOnly = true)
public class UserService extends BaseService<User, UserMapper> {
	
	@Autowired
	private PasswordHelper passwordHelper;

	@Transactional(readOnly=false)
	@Override
	public int save(User user) {
		//加密密码,需要调用userRealm.clearCachedAuthenticationInfo(PrincipalCollection principals)清除身份验证信息缓存
		passwordHelper.encryptPassword(user);
		
		return super.save(user);
	}
	
}
