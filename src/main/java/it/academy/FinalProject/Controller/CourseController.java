package it.academy.FinalProject.Controller;

import it.academy.FinalProject.Entity.Course;
import it.academy.FinalProject.Model.RegisterCourse;
import it.academy.FinalProject.Model.RegisterUser;
import it.academy.FinalProject.Repository.CourseRepo;
import it.academy.FinalProject.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/course")
public class CourseController {
    @Autowired
    CourseService courseService;
    @Autowired
    CourseRepo courseRepo;

    @ModelAttribute("course")
    public RegisterCourse newUser() {
        return new RegisterCourse();
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        return "Registration/registerCourse";
    }
    @PostMapping("/register")
    public String registerCourse(@ModelAttribute("course") @Valid RegisterCourse u, BindingResult result) {
        if (result.hasErrors()) {
            return "Registration/register";
        }
        courseService.saveModel(u);
        System.out.println(courseService.saveModel(u));
        return "redirect:/Course/courseList";
    }


    @GetMapping("/all")
    public String getAllCourses(Model model){
        model.addAttribute("allCourses", courseService.getAll());
        return "Course/courseList";
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
