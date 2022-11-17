package michalmlynarczyk.recruitmenttask.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class UserSpecificationDTO {
    @NotNull(message = "List of characters is required")
    private Set<Character> characters;

    @NotNull(message = "Minimal length is required")
    private Integer minLength;

    @NotNull(message = "Maximal length is required")
    private Integer maxLength;

    @NotNull(message = "Number of strings is required")
    private Integer numberOfStrings;
}
