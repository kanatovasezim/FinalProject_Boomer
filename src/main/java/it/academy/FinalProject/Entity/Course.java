package it.academy.FinalProject.Entity;

import it.academy.FinalProject.Enum.Category;
import it.academy.FinalProject.Enum.Language;
import it.academy.FinalProject.Enum.Type;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "course")
@FieldDefaults(level = AccessLevel.PRIVATE)
//Course: id, name,User author, description, Type type, list<Category> category, duration, list<Language> language, likes
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column
    String name;

    @Column
    User author;

    @Column
    String description;

    @Column
    Type type;

    @Column
    List<Category> categoryList;

    @Column
    String duration;

    @Column
    List<Language> languageList;

}
