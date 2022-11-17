package michalmlynarczyk.recruitmenttask.utils;

import michalmlynarczyk.recruitmenttask.dto.UserSpecificationDTO;
import michalmlynarczyk.recruitmenttask.exception.SpecificationNotValidException;

public interface UserSpecificationValidator {
    void validate(UserSpecificationDTO userSpecification) throws SpecificationNotValidException;
}
