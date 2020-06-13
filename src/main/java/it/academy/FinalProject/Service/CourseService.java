package it.academy.FinalProject.Service;

import it.academy.FinalProject.Entity.Course;

import java.util.List;

public interface CourseService {
    List<Course> getAll();
    Course getById(Long id);
    Course save(Course Course);
    void delete(Long id);
}
