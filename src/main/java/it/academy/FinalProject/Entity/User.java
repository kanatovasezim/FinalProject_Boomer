package it.academy.FinalProject.Entity;

import it.academy.FinalProject.Enum.UserRole;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")

//User: id, login, password, dateOfBirth, email, role, list<Course> courseOffered, list<Course> courseGet, balance, created_date
class User {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    Long id;

    @Column
    String login;

    @Column
    String password;

    @Column
    Date dateOfBirth;

    @Column
    String email;

    @Column
    UserRole role;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Column
    List<Course> courseOffer;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Column
    List<Course> courseGet;

    @Column
    Long balance;

    @Column
    @CreatedDate
    Date createdDate;

}
