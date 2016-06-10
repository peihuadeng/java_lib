package com.dph.modules.area.entity;

import com.dph.common.entity.BaseEntity;

public class Area extends BaseEntity<Area> {

	private static final long serialVersionUID = 1L;
	
	private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}