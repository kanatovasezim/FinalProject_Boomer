package it.academy.FinalProject.Controller;

import it.academy.FinalProject.Entity.User;
import it.academy.FinalProject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<User> getAllUsers(){
        return userService.getAll();
    }
    @GetMapping("/{login}")
    public User getByLogin(@PathVariable("login") String login){
        return userService.findByLogin(login);
    }
    @PostMapping("/{login}")
    public void delete(@PathVariable("login") String login){
        userService.deleteByLogin(login);
    }


}
