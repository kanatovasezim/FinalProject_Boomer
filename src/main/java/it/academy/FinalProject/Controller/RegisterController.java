package it.academy.FinalProject.Controller;
import it.academy.FinalProject.Entity.Role;
import it.academy.FinalProject.Entity.User;
import it.academy.FinalProject.Repository.RoleRepo;
import it.academy.FinalProject.Repository.UserRepo;
import it.academy.FinalProject.Service.ServiceImpl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleServiceImpl roleService;
    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/user")
    public ResponseEntity<String> doRegister(@RequestBody User u) {
        User user = new User();
        String encodedPassword  = passwordEncoder.encode(u.getPassword());
        user.setId(u.getId());
        user.setPassword(encodedPassword);
        user.setLogin(u.getLogin());
        user.setIsActive(true);
        user.setDateOfBirth(u.getDateOfBirth());
        user.setBalance((long) 100);
        user.setCourseGet(u.getCourseGet());
        user.setCourseOffer(u.getCourseOffer());
        user.setEmail(u.getEmail());
        userRepo.save(user);

        return ResponseEntity.status(HttpStatus.OK)
                .body("You have successfully registered: " + user.getLogin());
    }

}
