package it.academy.FinalProject.Controller;
import it.academy.FinalProject.Entity.Course;
import it.academy.FinalProject.Entity.User;
import it.academy.FinalProject.Model.RegisterUser;
import it.academy.FinalProject.Repository.CourseRepo;
import it.academy.FinalProject.Repository.UserRepo;
import it.academy.FinalProject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CourseRepo courseRepo;
    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public RegisterUser newUser() {
        return new RegisterUser();
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        return "Registration/register";
    }

    @PostMapping("/user")
    public String registerUser(@ModelAttribute("user") @Valid RegisterUser u, BindingResult result) {
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        if (userRepo.findByLogin(u.getLogin()) != null) {
            result.rejectValue("login", null, "Login already exists");
        }
        if (result.hasErrors()) {
            return "Registration/register";
        }
        userService.saveModel(u);
        return "User/userList";
    }

    @PostMapping("/course")
    public String  registerCourse(@RequestBody Course c){
        Course course = Course.builder()
                .description(c.getDescription())
                .duration(c.getDuration())
                .name(c.getName())
                .cost(c.getCost())
                .freePlaces(c.getFreePlaces())
                .author(c.getAuthor())
                .categoryList(c.getCategoryList())
                .languageList(c.getLanguageList())
                .id(c.getId())
                .build();
        courseRepo.save(course);
        return "redirect:/Course/courseList";
    }

}
