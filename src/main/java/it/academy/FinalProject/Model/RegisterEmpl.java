package it.academy.FinalProject.Model;
import it.academy.FinalProject.Enum.Gender;
import it.academy.FinalProject.Enum.Role;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterEmpl {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @NotEmpty
    String login;
    @NotEmpty
    String password;
    @NotEmpty
    String name;
    @Email
    @NotEmpty
    String email;
    @Enumerated(EnumType.STRING)
    Gender gender;
    @Enumerated(EnumType.STRING)
    Role role;
    @CreationTimestamp
    LocalDateTime createdDate;
}
