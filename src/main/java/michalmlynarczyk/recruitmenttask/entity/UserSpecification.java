package michalmlynarczyk.recruitmenttask.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "user_specification")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSpecification {
    @Id
    @SequenceGenerator(
            name = "user_specification_sequence",
            sequenceName = "user_specification_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_specification_sequence"
    )
    private Long id;

    private Integer minLength;

    private Integer maxLength;

    private Integer numberOfStrings;

    @ElementCollection
    private Set<Character> characters;
}
