package it.academy.FinalProject.Model;

import it.academy.FinalProject.Config.Constraint.FieldMatch;
import it.academy.FinalProject.Entity.Course;
import it.academy.FinalProject.Entity.Role;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldMatch.List({
        @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
})
public class RegisterEmpl {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @NotEmpty
    String login;
    @NotEmpty
    String password;
    @NotEmpty
    String confirmPassword;
    @NotEmpty
    String name;
    @Email
    @NotEmpty
    String email;
    Role role;
    @CreationTimestamp
    LocalDateTime createdDate;
}
