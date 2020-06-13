package it.academy.FinalProject.Service.ServiceImpl;

import it.academy.FinalProject.Entity.Course;
import it.academy.FinalProject.Repository.CourseRepo;
import it.academy.FinalProject.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepo courseRepo;

    @Override
    public List<Course> getAll() {
        return courseRepo.findAll();
    }

    @Override
    public Course getById(Long id) {
        return courseRepo.findById(id).get();
    }

    @Override
    public Course save(Course course) {
        return courseRepo.save(course);
    }

    @Override
    public void delete(Long id) {
        courseRepo.deleteById(id);
    }
}
