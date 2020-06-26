package it.academy.FinalProject.Bootstrap;
import it.academy.FinalProject.Entity.Course;
import it.academy.FinalProject.Entity.Employee;
import it.academy.FinalProject.Entity.Role;
import it.academy.FinalProject.Entity.User;
import it.academy.FinalProject.Model.RegisterEmpl;
import it.academy.FinalProject.Repository.CourseRepo;
import it.academy.FinalProject.Repository.RoleRepo;
import it.academy.FinalProject.Repository.UserRepo;
import it.academy.FinalProject.Service.EmployeeService;
import it.academy.FinalProject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class Bootstrap implements CommandLineRunner {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    CourseRepo courseRepo;
    @Override
    public void run(String... args) throws Exception {
        Role employee = Role.builder()
                .roleName("ROLE_EMPLOYEE")
                .build();
        roleRepo.save(employee);

        Role user = Role.builder()
                .roleName("ROLE_USER")
                .build();
        roleRepo.save(user);

        Role admin = Role.builder()
                .roleName("ROLE_ADMIN")
                .build();
        roleRepo.save(admin);

        Employee employee1 = Employee.builder()
                .login("empl1")
                .email("qwerty@gmail.com")
                .name("Sezim")
                .password(passwordEncoder.encode("123"))
                .role(employee)
                .build();
        employeeService.save(employee1);

        Employee employee2 = Employee.builder()
                .login("empl2")
                .email("qwert@gmail.com")
                .name("Amantur")
                .password(passwordEncoder.encode("123"))
                .role(employee)
                .build();
        employeeService.save(employee2);

        User adm = User.builder()
                .login("Admin")
                .password(passwordEncoder.encode("123"))
                .isActive(true)
                .name("Admin")
                .role(admin)
                .email("admin@gmail.com")
                .build();
        userRepo.save(adm);

        User u = User.builder()
                .login("Sezim")
                .name("Sezim")
                .role(user)
                .balance((long)500)
                .email("sezim@gmail.com")
                .password(passwordEncoder.encode("123"))
                .build();
        userRepo.save(u);

        Course c = Course.builder()
                .freePlaces(5)
                .duration("2")
                .author(u)
                .description("Cool")
                .name("Guitar")
                .cost(100)
                .build();
        courseRepo.save(c);
    }
}
