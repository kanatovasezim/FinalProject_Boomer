package it.academy.FinalProject.Controller;

import it.academy.FinalProject.Entity.Donation;
import it.academy.FinalProject.Service.DonationService;
import it.academy.FinalProject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/donation")
public class DonationController {

    @Autowired
    private DonationService donationService;
    @Autowired
    private UserService userService;
    @ModelAttribute("donation")
    public Donation newDonation() {
        return new Donation();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "Registration/registerDonation";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("donation") @Valid Donation d) {
        System.out.println(d);
        donationService.save(d);
        return "redirect:/welcome";
    }

    @GetMapping("/all")
    public String getAllUsers(Model model, Authentication authentication) {
        model.addAttribute("allDonations", donationService.getAll());
        model.addAttribute("admin", userService.findByLogin(authentication.getName()));
        return "Donation/donations";
    }
}
