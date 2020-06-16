package it.academy.FinalProject.Controller;

import it.academy.FinalProject.Entity.Course;
import it.academy.FinalProject.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/course")
public class CourseController {
    @Autowired
    CourseService courseService;


    @GetMapping("/all")
    public List<Course> getAllCourses(){
        return courseService.getAll();
    }
    @GetMapping("/{name}")
    public Course getByName(@PathVariable("name") String name){
        return  courseService.findByName(name);
    }
    @PostMapping("/{name}")
    public void delete(@PathVariable("name") String name){
        courseService.deleteByName(name);
    }
}
