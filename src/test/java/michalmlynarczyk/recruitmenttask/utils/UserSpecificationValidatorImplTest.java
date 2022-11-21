package michalmlynarczyk.recruitmenttask.utils;

import michalmlynarczyk.recruitmenttask.dto.UserSpecificationDTO;
import michalmlynarczyk.recruitmenttask.exception.SpecificationNotValidException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class UserSpecificationValidatorImplTest {
    private final UserSpecificationValidator underTest;

    @Autowired
    UserSpecificationValidatorImplTest(UserSpecificationValidator underTest) {
        this.underTest = underTest;
    }

    @Test
    void shouldValidateCorrectInput() {
        //given
        UserSpecificationDTO userSpecification = new UserSpecificationDTO(
                Set.of('a', 'b', 'c'),
                3,
                3,
                27L);

        //when
        //then
        assertThatNoException()
                .isThrownBy(() -> underTest.validate(userSpecification));
    }

    @Test
    void shouldThrowExceptionWhenMinLengthGreaterThanMax() {
        //given
        UserSpecificationDTO userSpecification = new UserSpecificationDTO(
                Set.of('a', 'b', 'c'),
                4,
                3,
                21L);

        //when
        //then
        assertThatThrownBy(() -> underTest.validate(userSpecification))
                .isInstanceOf(SpecificationNotValidException.class)
                .hasMessage("Minimal length cannot be greater than maximal length");
    }

    @Test
    void shouldThrowExceptionWhenRequiredNumberOfStringsGratedThanPossible() {
        //given
        long sumOfPossibleUniqueStrings = 3750;
        UserSpecificationDTO userSpecification = new UserSpecificationDTO(
                Set.of('a', 'b', 'c', 'v', 'w'),
                4,
                5,
                555521L);

        //when
        //then
        assertThatThrownBy(() -> underTest.validate(userSpecification))
                .isInstanceOf(SpecificationNotValidException.class)
                .hasMessage(String.format("Cannot obtain this number of unique strings," +
                        " maximal unique number of string for this specification is: %d", sumOfPossibleUniqueStrings));
    }
}