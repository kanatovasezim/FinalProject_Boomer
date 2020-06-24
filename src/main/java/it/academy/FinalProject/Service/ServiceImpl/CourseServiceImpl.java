package it.academy.FinalProject.Service.ServiceImpl;

import it.academy.FinalProject.Entity.Course;
import it.academy.FinalProject.Entity.User;
import it.academy.FinalProject.Model.RegisterCourse;
import it.academy.FinalProject.Model.RegisterUser;
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
        return courseRepo.findById(id).orElse(new Course());
    }

    @Override
    public Course save(Course course) {
        return courseRepo.save(course);
    }

    @Override
    public void delete(Long id) {
        courseRepo.deleteById(id);
    }
    @Override
    public Course findByName(String name){
        return courseRepo.findByName(name);
    }
    @Override
    public void deleteByName(String name){
        if (courseRepo.existsById(courseRepo.findByName(name).getId())){
            courseRepo.deleteById(courseRepo.findByName(name).getId());
        }
    }
    @Override
    public Course saveModel(RegisterCourse c) {
       Course course = Course.builder()
               .id(c.getId())
               .name(c.getName())
               .author(c.getAuthor())
               .languageList(c.getLanguageList())
               .duration(c.getDuration())
               .cost(c.getCost())
               .description(c.getDescription())
               .freePlaces(c.getFreePlaces())
               .categoryList(c.getCategory())
               .requests(c.getRequests())
               .build();
       courseRepo.save(course);
        return course;
    }
}
