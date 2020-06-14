package it.academy.FinalProject.Entity;

import com.sun.istack.NotNull;
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

    @NotNull
    @Column(name = "name", unique = true)
    String name;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    User author;

    @NotNull
    @Column(name = "description")
    String description;

    @NotNull
    @Column(name = "type")
    @Enumerated(EnumType.ORDINAL)
    Type type;

    @NotNull
    @ElementCollection(targetClass = Category.class)
    @JoinTable(name = "Category", joinColumns = @JoinColumn(name = "user_u.id"))
    @Column(name = "categoryList")
    @Enumerated(EnumType.STRING)
    List<Category> categoryList;

    @NotNull
    @Column(name = "duration")
    String duration;

    @NotNull
    @ElementCollection(targetClass = Language.class)
    @JoinTable(name = "Language", joinColumns = @JoinColumn(name = "user_u.id"))
    @Column(name = "language_List")
    @Enumerated(EnumType.STRING)
    List<Language> languageList;

}
