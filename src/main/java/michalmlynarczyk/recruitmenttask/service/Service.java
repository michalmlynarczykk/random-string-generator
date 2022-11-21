package michalmlynarczyk.recruitmenttask.service;

import michalmlynarczyk.recruitmenttask.dto.UserSpecificationDTO;
import michalmlynarczyk.recruitmenttask.entity.UserSpecification;

public interface Service {
    UserSpecification saveUserSpecification(UserSpecificationDTO userSpecificationDTO);

    void generateFilesWithResultsForEachSubmittedSpecification();

    Integer getNumberOfRunningThreads();
}
