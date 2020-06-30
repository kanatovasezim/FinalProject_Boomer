package it.academy.FinalProject.Controller;

import it.academy.FinalProject.Entity.Course;
import it.academy.FinalProject.Entity.Post;
import it.academy.FinalProject.Enum.Category;
import it.academy.FinalProject.Model.RegisterCourse;
import it.academy.FinalProject.Service.PostService;
import it.academy.FinalProject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    @ModelAttribute("post")
    public Post newPost() {
        return new Post();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "Registration/registerPost";
    }

    @PostMapping("/register")
    public String registerCourse(@ModelAttribute("post") @Valid Post p, BindingResult result) {
        if (result.hasErrors()) {
            return "Registration/registerPost";
        }
        postService.save(p);
        return "redirect:/post/profile";
    }
    @GetMapping("/profile")
    public String showNotificationPage(Model model, Authentication authentication) {
        model.addAttribute("user", userService.findByLogin(authentication.getName()));
        return "Publisher/publisherProfilePage";
    }

    @GetMapping("/all")
    public String getAllCourses(Model model, Authentication authentication){
        model.addAttribute("allPosts", postService.getAll());
        model.addAttribute("user", userService.findByLogin(authentication.getName()));
        return "Post/allPosts";
    }

}
