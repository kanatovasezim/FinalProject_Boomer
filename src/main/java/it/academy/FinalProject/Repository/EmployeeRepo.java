package it.academy.FinalProject.Repository;

import it.academy.FinalProject.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface EmployeeRepo extends JpaRepository<Employee, Long> {
//    @Query( value = "select * from employee e where e.login = :login", nativeQuery=true)
//    Employee findByLogin(@Param("login") String login);
//
//    @Query( value = "select * from employee e where e.name = :nameE", nativeQuery=true)
//    Employee findByName(@Param("name") String nameE);
}