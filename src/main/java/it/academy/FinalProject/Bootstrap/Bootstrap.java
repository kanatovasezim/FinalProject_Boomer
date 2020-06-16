package it.academy.FinalProject.Bootstrap;
import it.academy.FinalProject.Entity.Role;
import it.academy.FinalProject.Entity.User;
import it.academy.FinalProject.Repository.RoleRepo;
import it.academy.FinalProject.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Component
public class Bootstrap implements CommandLineRunner {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepo employeeRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Override
    public void run(String... args) throws Exception {
        DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        User employee1 = User.builder()
                .login("admin")
                .dateOfBirth(df.parse("01-01-1997"))
                .email("admin@gmail.com")
                .isActive(true)
                .password(passwordEncoder.encode("456"))
                .build();
        employeeRepo.save(employee1);

        User employee2 = User.builder()
                .login("publisher")
                .dateOfBirth(df.parse("12-10-1999"))
                .email("publisher@gmail.com")
                .password(passwordEncoder.encode("123"))
                .build();
        employeeRepo.save(employee2);


        Role publisher = Role.builder()
                .roleName("ROLE_PUBLISHER")
                .user(employee2)
                .build();

        roleRepo.save(publisher);

        Role admin = Role.builder()
                .roleName("ROLE_ADMIN")
                .user(employee1)
                .build();

        roleRepo.save(admin);

    }
}
