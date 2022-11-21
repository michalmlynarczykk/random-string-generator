package michalmlynarczyk.recruitmenttask.service;

import lombok.RequiredArgsConstructor;
import michalmlynarczyk.recruitmenttask.dto.UserSpecificationDTO;
import michalmlynarczyk.recruitmenttask.entity.UserSpecification;
import michalmlynarczyk.recruitmenttask.entity.UserSpecificationRepository;
import michalmlynarczyk.recruitmenttask.exception.ServiceLayerException;
import michalmlynarczyk.recruitmenttask.exception.SpecificationNotValidException;
import michalmlynarczyk.recruitmenttask.task.GenerateRandomStringsTask;
import michalmlynarczyk.recruitmenttask.utils.UserSpecificationValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceImpl implements Service {
    @Qualifier("taskExecutor")
    private final ThreadPoolTaskExecutor taskExecutor;
    private final UserSpecificationValidator validator;
    private final UserSpecificationRepository repository;
    private final ModelMapper modelMapper;

    @Override
    public UserSpecification saveUserSpecification(UserSpecificationDTO userSpecificationDTO) {
        try {
            validator.validate(userSpecificationDTO);
        } catch (SpecificationNotValidException e) {
            throw new ServiceLayerException(e);
        }
        UserSpecification specification = modelMapper.map(userSpecificationDTO, UserSpecification.class);
        return repository.save(specification);
    }

    @Override
    public void generateFilesWithResultsForEachSubmittedSpecification() {
        List<UserSpecification> userSpecificationList = repository.findAll();
        if (userSpecificationList.isEmpty()) {
            throw new ServiceLayerException("No specification to process");
        }
        userSpecificationList
                .forEach(specification -> taskExecutor.execute(new GenerateRandomStringsTask(specification)));
    }

    @Override
    public Integer getNumberOfRunningThreads() {
        return taskExecutor.getActiveCount();
    }
}
