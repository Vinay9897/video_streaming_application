package com.stream.app.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity(name="course_tbl")
public class Course {
	
	@Id
	private String courseId;
	
	private String courseTitle;
	
	@OneToMany
	List<Video> list = new ArrayList<>();

}
