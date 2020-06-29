package it.academy.FinalProject.Model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Mail {
    @NotEmpty
    List<String> to;
    String subject;
    @NotEmpty
    String message;
}
