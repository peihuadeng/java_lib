package com.dph.modules.student.service;

import java.util.List;

import com.dph.modules.student.entity.Student;

public interface StudentService {
	
	List<Student> getAll();
	
	Student get(int id);
	
	int remove(int id);
	
	int save(Student student); 

}