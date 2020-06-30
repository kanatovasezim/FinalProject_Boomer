package it.academy.FinalProject.Entity;

import com.sun.istack.NotNull;
import it.academy.FinalProject.Enum.Category;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "posts")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @Column(name = "created_Date", updatable = false)
    @CreationTimestamp
    Date createdDate;
    @NotNull
    @ElementCollection(targetClass = Category.class)
    @JoinTable(name = "Category", joinColumns = @JoinColumn(name = "course.id"))
    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    List<Category> category;
    @Column(name = "topic", nullable = false)
    String topic;
    @Column(name = "text", nullable = false)
    String text;
}
