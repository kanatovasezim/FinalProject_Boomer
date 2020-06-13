package it.academy.FinalProject.Entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "login")
    String login;

    @Column(name = "password")
    String password;

    @Column(name = "name")
    String name;

    @Column(name = "date_Of_Birth")
    Date dateOfBirth;

    @Column(name = "email")
    String email;

    @Column(name = "is_Active")
    Boolean isActive;

    @Column(name = "created_Date")
    @CreatedDate
    Date createdDate;
}
