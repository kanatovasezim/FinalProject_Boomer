package it.academy.FinalProject.Repository;
import it.academy.FinalProject.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    @Query( value = "select * from user_u u where u.login = :login", nativeQuery=true)
    User findByLogin(@Param("login") String login);
}
