package com.dph.system.permission.entity;

import com.dph.common.entity.BaseEntity;

public class Permission extends BaseEntity<Permission> {

	private static final long serialVersionUID = -6120942339083796808L;

	private String permission;

    private String description;

    private Boolean available;

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission == null ? null : permission.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}