package com.dph.system.user.dao;

import com.dph.annotation.MybatisDao;
import com.dph.common.persistence.BaseDao;
import com.dph.system.user.entity.User;

@MybatisDao
public interface UserMapper extends BaseDao<User> {
}