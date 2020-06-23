package it.academy.FinalProject.Service;
import it.academy.FinalProject.Entity.Employee;
import it.academy.FinalProject.Model.RegisterEmpl;

import java.util.List;

public interface EmployeeService {
    Employee save(Employee employee);
    List<Employee> getAll();
    void delete(Long id);
    Employee saveModelEmpl(RegisterEmpl empl);
}
