package com.dph.system.rolePermission.dao;

import com.dph.system.rolePermission.entity.RolePermissionKey;

public interface RolePermissionMapper {
    int deleteByPrimaryKey(RolePermissionKey key);

    int insert(RolePermissionKey record);

    int insertSelective(RolePermissionKey record);
}