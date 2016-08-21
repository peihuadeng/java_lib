package com.dph.modules.teacher.entity;

import com.dph.common.cache.annotation.LinkTo;
import com.dph.common.entity.BaseEntity;
import com.dph.modules.school.dao.SchoolRemoteCacheDao;
import com.dph.modules.school.entity.School;

public class Teacher extends BaseEntity<Teacher> {

	private static final long serialVersionUID = 1L;

	private String name;

	private Integer age;
	
	private String schoolId;
	
	@LinkTo(field = "schoolId", dao=SchoolRemoteCacheDao.class)
	private School school;

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

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}
}