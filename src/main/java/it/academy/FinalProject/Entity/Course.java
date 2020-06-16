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
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
//Course: id, name,User author, description, Type type, list<Category> category, duration, list<Language> language, likes
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "name", nullable = false)
    String name;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "author", nullable = false)
    User author;

    @NotNull
    @Column(name = "description", nullable = false)
    String description;

    @NotNull
    @Column(name = "cost", nullable = false)
    Integer cost;


//    @NotNull
//    @Column(name = "type", nullable = false)
//    @Enumerated(EnumType.STRING)
//    Type type;

    @NotNull
    @ElementCollection(targetClass = Category.class)
    @JoinTable(name = "Category", joinColumns = @JoinColumn(name = "s_user.id"))
    @Column(name = "categoryList", nullable = false)
    @Enumerated(EnumType.STRING)
    List<Category> categoryList;

    @NotNull
    @Column(name = "duration", nullable = false)
    String duration;

    @NotNull
    @Column(name = "freePlaces", nullable = false)
    Integer freePlaces;

    @NotNull
    @ElementCollection(targetClass = Language.class)
    @JoinTable(name = "Language", joinColumns = @JoinColumn(name = "s_user.id"))
    @Column(name = "languageList", nullable = false)
    @Enumerated(EnumType.STRING)
    List<Language> languageList;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="requests", joinColumns=@JoinColumn(name="course_id"), inverseJoinColumns=@JoinColumn(name="s_user_id"))
    List<User> requests;
}
