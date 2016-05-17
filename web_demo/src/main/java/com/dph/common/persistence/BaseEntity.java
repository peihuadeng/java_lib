package com.dph.common.persistence;

import java.io.Serializable;

import com.dph.common.utils.Generator;
import com.dph.common.utils.StringUtils;

public abstract class BaseEntity<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private Page<T> page;

	public void preInsert() {
		if (StringUtils.isBlank(id)) {
			id = Generator.genUUID();
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public Page<T> getPage() {
		return page;
	}

	public void setPage(Page<T> page) {
		this.page = page;
	}

}
