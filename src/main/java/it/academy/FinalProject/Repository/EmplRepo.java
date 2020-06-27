package it.academy.FinalProject.Repository;

import it.academy.FinalProject.Entity.Employee;
import it.academy.FinalProject.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmplRepo extends JpaRepository<Employee, Long> {
    List<Employee> findAllByRole(Role role);
    Employee findByLogin(String login);
}
