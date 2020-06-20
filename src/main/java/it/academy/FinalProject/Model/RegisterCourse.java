package it.academy.FinalProject.Model;
import it.academy.FinalProject.Entity.User;
import it.academy.FinalProject.Enum.Language;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @NotEmpty
    String name;
    User author;
    @NotEmpty
    String description;
    @NotEmpty
    Integer cost;
    @NotEmpty
    Integer freePlaces;
    @NotEmpty
    String duration;
    String category;
    List<Language> languageList;
    List<User> requests;
}
