package michalmlynarczyk.recruitmenttask.service;

import michalmlynarczyk.recruitmenttask.dto.UserSpecificationDTO;
import michalmlynarczyk.recruitmenttask.entity.UserSpecification;
import michalmlynarczyk.recruitmenttask.entity.UserSpecificationRepository;
import michalmlynarczyk.recruitmenttask.exception.SpecificationNotValidException;
import michalmlynarczyk.recruitmenttask.utils.UserSpecificationValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {
    private final UserSpecificationValidator validator;
    private final UserSpecificationRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public ServiceImpl(UserSpecificationValidator validator,
                       UserSpecificationRepository repository,
                       ModelMapper modelMapper) {
        this.validator = validator;
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void saveUserSpecification(UserSpecificationDTO userSpecificationDTO) {
        try {
            validator.validate(userSpecificationDTO);
        } catch (SpecificationNotValidException e) {
            throw new RuntimeException(e);
        }
        UserSpecification specification = modelMapper.map(userSpecificationDTO, UserSpecification.class);
        repository.save(specification);
    }
}
