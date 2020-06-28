package it.academy.FinalProject.Entity;

import com.sun.istack.NotNull;
import it.academy.FinalProject.Enum.Category;
import it.academy.FinalProject.Enum.Language;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Setter
@Getter
@ToString(exclude = "requests")
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "course")
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
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "author", nullable = false)
    User author;

    @NotNull
    @Column(name = "description", nullable = false)
    String description;

    @NotNull
    @Column(name = "cost", nullable = false)
    Integer cost;

    @NotNull
    @ElementCollection(targetClass = Category.class)
    @JoinTable(name = "Category", joinColumns = @JoinColumn(name = "s_user.id"))
    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    List<Category> category;

    @NotNull
    @Column(name = "duration", nullable = false)
    String duration;

    @NotNull
    @Column(name = "freePlaces", nullable = false)
    Integer freePlaces;

    @NotNull
    @ElementCollection(targetClass = Language.class)
    @JoinTable(name = "Language", joinColumns = @JoinColumn(name = "s_user.id"))
    @Column(name = "languageList")
    @Enumerated(EnumType.STRING)
    List<Language> languageList;

    @ManyToMany(cascade=CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(name="requests", joinColumns=@JoinColumn(name="course_id"), inverseJoinColumns=@JoinColumn(name="s_user_id"))
    List<User> requests;

    @Column(name = "created_Date", updatable = false, nullable = false)
    @CreationTimestamp
    LocalDateTime createdDate;
}
