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
        Long numberOfStrings = userSpecification.getNumberOfStrings();
        Set<Character> characters = userSpecification.getCharacters();
        if (minLength > maxLength) {
            throw new SpecificationNotValidException("Minimal length cannot be greater than maximal length");
        }

        Long sumOfPossibleUniqueStrings = calculateNumberOfPossibleUniqueStrings(
                minLength,
                maxLength,
                characters
        );
        if (numberOfStrings > sumOfPossibleUniqueStrings) {
            throw new SpecificationNotValidException(String.format("Cannot obtain this number of unique strings," +
                    " maximal unique number of string for this specification is: %d", sumOfPossibleUniqueStrings));
        }
    }

    private Long calculateNumberOfPossibleUniqueStrings(Integer minLength,
                                                        Integer maxLength,
                                                        Set<Character> characters) {
        /* this method calculates how many unique strings we can create with given specification, example:
        minLength=2 maxLength=3 characters= a,b unique strings: aa,bb,ba,aab,abb,bbb etc. */

        int numberOfCharacters = characters.size();
        long sumOfPossibleUniqueStrings = 0L;
        for (int n = minLength; n <= maxLength; n++) {
            sumOfPossibleUniqueStrings += Math.pow(numberOfCharacters, n);
        }
        return sumOfPossibleUniqueStrings;
    }
}
