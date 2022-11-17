package michalmlynarczyk.recruitmenttask.utils;

import michalmlynarczyk.recruitmenttask.dto.UserSpecificationDTO;
import michalmlynarczyk.recruitmenttask.exception.SpecificationNotValidException;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UserSpecificationValidatorImpl implements UserSpecificationValidator {

    @Override
    public void validate(UserSpecificationDTO userSpecification) throws SpecificationNotValidException {
        Integer minLength = userSpecification.getMinLength();
        Integer maxLength = userSpecification.getMaxLength();
        Integer numberOfStrings = userSpecification.getNumberOfStrings();
        Set<Character> characters = userSpecification.getCharacters();
        if (minLength < 1) {
            throw new SpecificationNotValidException("Minimal length cannot be less than 1");
        }
        if (minLength > maxLength) {
            throw new SpecificationNotValidException("Minimal length cannot be greater than maximal length");
        }

        int sumOfPossibleUniqueStrings = calculateNumberOfPossibleUniqueStrings(
                minLength,
                maxLength,
                characters
        );
        if (numberOfStrings > sumOfPossibleUniqueStrings) {
            throw new SpecificationNotValidException(String.format("Cannot obtain this number of unique strings," +
                    " maximal unique number of string for this specification is: %d", sumOfPossibleUniqueStrings));
        }
    }

    private int calculateNumberOfPossibleUniqueStrings(Integer minLength,
                                                       Integer maxLength,
                                                       Set<Character> characters) {
        int numberOfCharacters = characters.size();
        int sumOfPossibleUniqueStrings = 0;
        for (int n = minLength; n <= maxLength; n++) {
            sumOfPossibleUniqueStrings += Math.pow(numberOfCharacters, n);
        }
        return sumOfPossibleUniqueStrings;
    }
}
