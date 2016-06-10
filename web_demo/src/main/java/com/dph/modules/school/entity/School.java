package com.dph.modules.school.entity;

import com.dph.common.cache.annotation.LinkTo;
import com.dph.common.entity.BaseEntity;
import com.dph.modules.area.dao.AreaLocalCacheDao;
import com.dph.modules.area.entity.Area;

public class School extends BaseEntity<School> {

	private static final long serialVersionUID = 1L;

	private String name;

    private String areaId;
    
    @LinkTo(field = "areaId", dao=AreaLocalCacheDao.class)
    private Area area;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId == null ? null : areaId.trim();
    }

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
}