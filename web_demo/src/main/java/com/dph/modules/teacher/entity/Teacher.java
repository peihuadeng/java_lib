package com.dph.modules.teacher.entity;

import com.dph.common.entity.BaseEntity;

public class Teacher extends BaseEntity<Teacher> {

	private static final long serialVersionUID = 1L;

	private String name;

	private Integer age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
}