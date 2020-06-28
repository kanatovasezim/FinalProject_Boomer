package it.academy.FinalProject.Service.ServiceImpl;

import it.academy.FinalProject.Entity.Employee;
import it.academy.FinalProject.Entity.Role;
import it.academy.FinalProject.Entity.User;
import it.academy.FinalProject.Model.RegisterEmpl;
import it.academy.FinalProject.Repository.EmplRepo;
import it.academy.FinalProject.Repository.RoleRepo;
import it.academy.FinalProject.Repository.UserRepo;
import it.academy.FinalProject.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmplRepo emplRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;

    @Override
    public Employee save(Employee employee) {
        User u = User.builder()
                .login(employee.getLogin())
                .password(employee.getPassword())
                .id(employee.getId())
                .isActive(true)
                .build();
        userRepo.save(u);
        return emplRepo.save(employee);
    }

    @Override
    public List<Employee> getAll() {
        return emplRepo.findAll();
    }

    @Override
    public Employee findByLogin(String login) {
        return emplRepo.findByLogin(login);
    }

    @Override
    public void delete(Long id) {
        emplRepo.deleteById(id);
    }
    @Override
    public Employee saveModelEmpl(RegisterEmpl empl) {
        Employee employee = Employee.builder()
                .id(empl.getId())
                .email(empl.getEmail())
                .login(empl.getLogin())
                .name(empl.getName())
                .gender(empl.getGender())
                .role(roleRepo.findByName(empl.getRole()))
                .createdDate(empl.getCreatedDate())
                .password(empl.getPassword())
                .build();
        User u = User.builder()
                .login(empl.getLogin())
                .role(roleRepo.findByName(empl.getRole()))
                .password(empl.getPassword())
                .isActive(true)
                .build();
        userRepo.save(u);
        return emplRepo.save(employee);
    }
}
