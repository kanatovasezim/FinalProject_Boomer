package it.academy.FinalProject.Bootstrap;
import it.academy.FinalProject.Entity.Employee;
import it.academy.FinalProject.Entity.Role;
import it.academy.FinalProject.Repository.EmployeeRepo;
import it.academy.FinalProject.Repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Bootstrap implements CommandLineRunner {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Override
    public void run(String... args) throws Exception {
        DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        Employee employee1 = Employee.builder()
                .login("admin")
                .dateOfBirth(df.parse("01-01-1997"))
                .email("admin@gmail.com")
                .name("Aigul")
                .password(passwordEncoder.encode("456"))
                .build();
        employeeRepo.save(employee1);

        Employee employee2 = Employee.builder()
                .login("publisher")
                .dateOfBirth(df.parse("12-10-1999"))
                .email("publisher@gmail.com")
                .name("Amantur")
                .password(passwordEncoder.encode("123"))
                .build();
        employeeRepo.save(employee2);

        Role publisher = Role.builder()
                .roleName("Publisher")
                .employee(employee2)
                .build();

        roleRepo.save(publisher);

        Role admin = Role.builder()
                .roleName("Admin")
                .employee(employee1)
                .build();

        roleRepo.save(admin);

    }
}
