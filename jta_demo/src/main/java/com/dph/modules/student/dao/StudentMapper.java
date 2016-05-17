package com.dph.modules.student.dao;

import java.util.List;

import com.dph.annotation.MybatisDaoTest2;
import com.dph.modules.student.entity.Student;

@MybatisDaoTest2
public interface StudentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Student student);

    int insertSelective(Student student);

    Student selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Student student);

    int updateByPrimaryKey(Student student);

	List<Student> selectAll();
}