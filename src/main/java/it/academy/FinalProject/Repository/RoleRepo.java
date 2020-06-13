package it.academy.FinalProject.Repository;

import it.academy.FinalProject.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface RoleRepo extends JpaRepository<Role, Long> {
    @Query( value = "select * from roles r where r.role_name = :role", nativeQuery=true)
    Role findByName(@Param("role") String role);
}
