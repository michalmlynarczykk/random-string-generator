package michalmlynarczyk.recruitmenttask.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSpecificationDTO {
    @NotNull(message = "List of characters is required")
    private Set<Character> characters;

    @NotNull(message = "Minimal length is required")
    @Min(value = 1, message = "Length must be greater than 0")
    private Integer minLength;

    @NotNull(message = "Maximal length is required")
    @Min(value = 1, message = "Length must be greater than 0")
    private Integer maxLength;

    @NotNull(message = "Number of strings is required")
    @Min(value = 1, message = "Number of strings must be greater than 0")
    private Long numberOfStrings;
}
