package com.dph.modules.student.entity;

import com.dph.common.entity.BaseEntity;

public class Student extends BaseEntity<Student> {
	
	private static final long serialVersionUID = 1L;

	private String name;

	private Integer age;

	private String teacherId;

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

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId == null ? null : teacherId.trim();
	}
}