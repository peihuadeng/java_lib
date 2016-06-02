package com.dph.system.role.entity;

import com.dph.common.entity.BaseEntity;

public class Role extends BaseEntity<Role> {

	private static final long serialVersionUID = -4268930802965052513L;

	private String role;

    private String description;

    private Boolean available;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
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