package it.academy.FinalProject.Model;


import it.academy.FinalProject.Config.Constraint.FieldMatch;
import it.academy.FinalProject.Entity.Course;
import it.academy.FinalProject.Enum.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @NotEmpty
    @Size(min = 3, message = "login should be longer than 3 characters")
    String login;
    @NotEmpty
    @Size(min = 6, message = "Password should be longer than 6 characters")
    String password;
    @NotEmpty
    String name;
    @Email
    @NotEmpty
    String email;
    @NotEmpty
    @Enumerated(EnumType.STRING)
    Gender gender;
    List<Course> courseList;
    @CreationTimestamp
    Date createdDate;
    List<Course> requested;
}
