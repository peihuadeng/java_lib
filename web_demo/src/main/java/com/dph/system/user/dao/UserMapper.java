package com.dph.system.user.dao;

import com.dph.annotation.MybatisDao;
import com.dph.common.persistence.BaseMapper;
import com.dph.system.user.entity.User;

@MybatisDao
public interface UserMapper extends BaseMapper<User> {
}