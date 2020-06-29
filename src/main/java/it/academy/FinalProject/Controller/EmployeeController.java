package it.academy.FinalProject.Controller;

import it.academy.FinalProject.Model.RegisterEmpl;
import it.academy.FinalProject.Repository.UserRepo;
import it.academy.FinalProject.Service.EmployeeService;
import it.academy.FinalProject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @ModelAttribute("employee")
    public RegisterEmpl newEmpl() {
        return new RegisterEmpl();
    }

    @GetMapping("/registerByAdmin")
    public String showRegistration() {
        return "Employee/registerByAdmin";
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        return "Registration/registerEmpl";
    }

    @GetMapping("/all")
    public String getAllUsers(Model model, Authentication authentication) {
        model.addAttribute("allEmployee", employeeService.getAll());
        model.addAttribute("admin", userService.findByLogin(authentication.getName()));
        return "Employee/employee";
    }

    @GetMapping("/edit/{login}")
    public String editEmployee(Model model, @PathVariable(value = "login") String login){
        model.addAttribute("empl", login == null ? new RegisterEmpl() : employeeService.findByLogin(login));
        model.addAttribute("e", login == null ? new RegisterEmpl() : employeeService.findByLogin(login));
        return "Employee/updateEmployee";
    }
    @PostMapping("/update/{login}")
    public String updateEmployee(@ModelAttribute("employee") @Valid RegisterEmpl e, @PathVariable("login") String login) {
        employeeService.updateEmpl(e, userRepo.findByLogin(login).getId());
        return "redirect:/employee/all";
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
        return "redirect:/employee/all";
    }

    @PostMapping("/registerByAdmin")
    public String registerEmployeeByAdmin(@ModelAttribute("employee") @Valid RegisterEmpl e, BindingResult result) {
        e.setPassword(passwordEncoder.encode(e.getPassword()));
        if (userRepo.findByLogin(e.getLogin()) != null) {
            result.rejectValue("login", null, "Login already exists");
        }
        if (result.hasErrors()) {
            return "Registration/registerEmpl";
        }
        employeeService.saveModelEmpl(e);
        return "redirect:/employee/all";
    }
    @GetMapping("/delete/{login}")
    public String delete(@PathVariable("login") String login) {
        employeeService.delete(employeeService.findByLogin(login).getId());
        return "redirect:/employee/all";
    }
}
