package it.academy.FinalProject.Bootstrap;
import it.academy.FinalProject.Entity.Role;
import it.academy.FinalProject.Entity.User;
import it.academy.FinalProject.Repository.RoleRepo;
import it.academy.FinalProject.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;

@Component
public class Bootstrap implements CommandLineRunner {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepo employeeRepo;
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    UserRepo userRepo;
    @Override
    public void run(String... args) throws Exception {
        Role publisher = Role.builder()
                .roleName("ROLE_PUBLISHER")
                .build();
        roleRepo.save(publisher);

        Role user = Role.builder()
                .roleName("ROLE_USER")
                .build();
        roleRepo.save(user);

        Role admin = Role.builder()
                .roleName("ROLE_ADMIN")
                .build();
        roleRepo.save(admin);

        User employee1 = User.builder()
                .login("admin")
                .role(admin)
                .email("admin@gmail.com")
                .isActive(true)
                .password(passwordEncoder.encode("456"))
                .build();
        employeeRepo.save(employee1);

        User employee2 = User.builder()
                .login("publisher")
                .role(publisher)
                .email("publisher@gmail.com")
                .password(passwordEncoder.encode("123"))
                .build();
        employeeRepo.save(employee2);

        User u = User.builder()
                .login("Sezim")
                .role(user)
                .email("sezim@gmail.com")
                .password(passwordEncoder.encode("123"))
                .build();
        userRepo.save(u);

    }
}
