package it.academy.FinalProject.Controller;
import it.academy.FinalProject.Entity.Course;
import it.academy.FinalProject.Entity.Role;
import it.academy.FinalProject.Entity.User;
import it.academy.FinalProject.Repository.CourseRepo;
import it.academy.FinalProject.Repository.RoleRepo;
import it.academy.FinalProject.Repository.UserRepo;
import it.academy.FinalProject.Service.ServiceImpl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/user")
    public void registerUser(@RequestBody User u) {
        User user = new User();
        String encodedPassword  = passwordEncoder.encode(u.getPassword());
        user.setId(u.getId());
        user.setPassword(encodedPassword);
        user.setLogin(u.getLogin());
        user.setIsActive(true);
        user.getCreatedDate();
        user.setDateOfBirth(u.getDateOfBirth());
        user.setBalance((long) 100);
        user.setEmail(u.getEmail());
        userRepo.save(user);
    }

    @PostMapping("/course")
    public void  registerCourse(@RequestBody Course c){
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
                .type(c.getType())
                .build();
        courseRepo.save(course);
    }

}
