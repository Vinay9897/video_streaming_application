package com.stream.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stream.app.entities.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, String>{

}
