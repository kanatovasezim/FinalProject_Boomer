package it.academy.FinalProject.Controller;

import it.academy.FinalProject.Model.Mail;
import it.academy.FinalProject.Service.EmployeeService;
import it.academy.FinalProject.Service.MailSenderService;
import it.academy.FinalProject.Service.ServiceImpl.MailSenderServiceImpl;
import it.academy.FinalProject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/mail")
public class MailSenderController {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    UserService userService;
    @Autowired
    MailSenderService mailSenderService;

    @ModelAttribute("mail")
    public Mail newMail() {
        return new Mail();
    }

    @GetMapping("/employee")
    public String showMailSenderPageEmployee(Model model, Authentication authentication) {
        model.addAttribute("employeeList", employeeService.getAll());
        model.addAttribute("admin", userService.findByLogin(authentication.getName()));
        return "MailSender/mailEmployee";
    }

    @GetMapping("/user")
    public String showMailSenderPageUser(Model model, Authentication authentication) {
        model.addAttribute("userList", userService.findAllByRole("user"));
        model.addAttribute("admin", userService.findByLogin(authentication.getName()));
        return "MailSender/mailUser";
    }


    @PostMapping("/sendMailEmployee")
    public String sendEmailEmployee(@ModelAttribute("mail") @Valid Mail m, RedirectAttributes redirectAttributes) {
        if (m.getMessage() == null || m.getTo() == null) {
            redirectAttributes.addFlashAttribute("Error", "Fill in message and subject blanks");
        } else {
            mailSenderService.send(m.getTo(), m.getSubject(), m.getMessage());
            redirectAttributes.addFlashAttribute("Success", "Successfully sent");
        }
        return "redirect:/mail/employee";
    }
    @PostMapping("/sendMailUser")
    public String sendEmailUser(@ModelAttribute("mail") @Valid Mail m, RedirectAttributes redirectAttributes) {
        if (m.getMessage() == null || m.getTo() == null) {
            redirectAttributes.addFlashAttribute("Error", "Fill in message and subject blanks");
        } else {
            mailSenderService.send(m.getTo(), m.getSubject(), m.getMessage());
            redirectAttributes.addFlashAttribute("Success", "Successfully sent");
        }
        return "redirect:/mail/user";
    }
}
