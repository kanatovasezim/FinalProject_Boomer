package it.academy.FinalProject.Controller;
import it.academy.FinalProject.Entity.Course;
import it.academy.FinalProject.Model.RegisterCourse;
import it.academy.FinalProject.Service.CourseService;
import it.academy.FinalProject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    UserService userService;

    @ModelAttribute("course")
    public RegisterCourse newUser() {
        return new RegisterCourse();
    }

//    @ModelAttribute("category")
//    public RegisterCategory newCategory() {
//        return new RegisterCategory();
//    }
//
//    @PostMapping("/search")
//    public String searchText(@ModelAttribute @Valid RegisterCategory category, Model model, Authentication authentication){
//        System.out.println(category);
//        model.addAttribute("allCourses", courseService.findByCategory(category));
//        model.addAttribute("user", userService.findByLogin(authentication.getName()));
//        return "Course/selectedCoursesByCategory";
//    }

    @GetMapping
    public String showRegistrationForm() {
        return "Registration/registerCourse";
    }

    @PostMapping("/register")
    public String registerCourse(@ModelAttribute("course") @Valid RegisterCourse u, BindingResult result, Authentication authentication) {
        if (result.hasErrors()) {
            return "Registration/registerCourse";
        }
        u.setAuthor(userService.findByLogin(authentication.getName()));
        courseService.saveModel(u);
        return "redirect:/user/profile";
    }

    @GetMapping("/all")
    public String getAllCourses(Model model, Authentication authentication){
        model.addAttribute("allCourses", courseService.getAllOther(authentication.getName()));
        model.addAttribute("user", userService.findByLogin(authentication.getName()));
        return "Course/allCourses";
    }
    @GetMapping("/taking")
    public String getTakingCourses(Model model, Authentication authentication){
        model.addAttribute("takingCourses", courseService.getTakingCourses(authentication.getName()));
        model.addAttribute("user", userService.findByLogin(authentication.getName()));
        return "Course/takingCourses";
    }
    @GetMapping("/requested")
    public String getRequestedCourses(Model model, Authentication authentication){
        model.addAttribute("requestedCourses", courseService.getRequestedCourses(authentication.getName()));
        model.addAttribute("user", userService.findByLogin(authentication.getName()));
        return "Course/requestedCourses";
    }
    @GetMapping("/requesting")
    public String getRequestingUsers(Model model, Authentication authentication){
        List<Course> courseList = courseService.findOfferingCourses(authentication.getName());
        model.addAttribute("requestingCourseUser", courseService.getRequestingUsers(courseList));
        model.addAttribute("user", userService.findByLogin(authentication.getName()));
        return "Course/requestingCourses";
    }
    @GetMapping("/offering")
    public String getOfferingCourses(Model model, Authentication authentication){
        model.addAttribute("offeringCourseUser", courseService.getOfferingCourses(userService.findByLogin(authentication.getName()).getLogin()));
        model.addAttribute("user", userService.findByLogin(authentication.getName()));
        return "Course/offeringCourses";
    }
    @GetMapping("/completed")
    public String getCompletedCourses(Model model, Authentication authentication){
        model.addAttribute("completedCourses", courseService.getCompletedCourses(authentication.getName()));
        model.addAttribute("user", userService.findByLogin(authentication.getName()));
        return "Course/completedCourses";
    }
    @GetMapping("/owning")
    public String getOwningCourses(Model model, Authentication authentication){
        model.addAttribute("owningCourses", courseService.findOfferingCourses(authentication.getName()));
        model.addAttribute("user", userService.findByLogin(authentication.getName()));
        return "Course/owningCourses";
    }
    @GetMapping("/{name}")
    public Course getByName(@PathVariable("name") String name){
        return  courseService.findByName(name);
    }
    @PostMapping("/delete/{name}")
    public void deleteByName(@PathVariable("name") String name){
        courseService.deleteByName(name);
    }
    @PostMapping("/delete/{id}")
    public void deleteById(@PathVariable("id") Long id){
        courseService.delete(id);
    }
}
