package it.academy.FinalProject.Controller;

import it.academy.FinalProject.Model.RegisterEmpl;
import it.academy.FinalProject.Model.RegisterUser;
import it.academy.FinalProject.Repository.UserRepo;
import it.academy.FinalProject.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @ModelAttribute("employee")
    public RegisterEmpl newEmpl() {
        return new RegisterEmpl();
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        return "Registration/registerEmpl";
    }

    @GetMapping("/all")
    public String getAllUsers(Model model) {
        model.addAttribute("allEmployee", employeeService.getAll());
        return "Employee/allEmployee";
    }


    @PostMapping("/register")
    public String registerEmployee(@ModelAttribute("employee") @Valid RegisterEmpl e, BindingResult result) {
        e.setPassword(passwordEncoder.encode(e.getPassword()));
        if (userRepo.findByLogin(e.getLogin()) != null) {
            result.rejectValue("login", null, "Login already exists");
        }
        if (result.hasErrors()) {
            return "Registration/registerEmpl";
        }
        employeeService.saveModelEmpl(e);
        return "redirect:/User/userList";
    }
}
