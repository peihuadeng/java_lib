package com.dph.system.userRole.dao;

import com.dph.system.userRole.entity.UserRoleKey;

public interface UserRoleMapper {
    int deleteByPrimaryKey(UserRoleKey key);

    int insert(UserRoleKey record);

    int insertSelective(UserRoleKey record);
}