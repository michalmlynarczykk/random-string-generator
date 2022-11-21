package michalmlynarczyk.recruitmenttask.service;

import michalmlynarczyk.recruitmenttask.dto.UserSpecificationDTO;
import michalmlynarczyk.recruitmenttask.entity.UserSpecification;
import michalmlynarczyk.recruitmenttask.entity.UserSpecificationRepository;
import michalmlynarczyk.recruitmenttask.exception.ServiceLayerException;
import michalmlynarczyk.recruitmenttask.exception.SpecificationNotValidException;
import michalmlynarczyk.recruitmenttask.task.GenerateRandomStringsTask;
import michalmlynarczyk.recruitmenttask.utils.UserSpecificationValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
class ServiceImplTest {
    private Service underTest;
    @Mock
    @Qualifier("taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;
    @Mock
    private UserSpecificationValidator validator;
    @Mock
    private UserSpecificationRepository repository;
    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        underTest = new ServiceImpl(taskExecutor, validator, repository, modelMapper);
    }

    @Test
    void shouldSaveUserSpecification() throws SpecificationNotValidException {
        //given
        UserSpecificationDTO userSpecification = new UserSpecificationDTO(
                Set.of('a', 'b', 'c'),
                3,
                3,
                27L);
        //when
        underTest.saveUserSpecification(userSpecification);

        //then
        ArgumentCaptor<UserSpecificationDTO> validatorCaptor = ArgumentCaptor.forClass(UserSpecificationDTO.class);
        verify(validator).validate(validatorCaptor.capture());
        UserSpecificationDTO capturedUserSpecificationValidator = validatorCaptor.getValue();

        ArgumentCaptor<UserSpecificationDTO> modelCaptor = ArgumentCaptor.forClass(UserSpecificationDTO.class);
        verify(modelMapper).map(modelCaptor.capture(), any());
        UserSpecificationDTO capturedUserSpecificationMapper = validatorCaptor.getValue();

        assertThat(userSpecification).isEqualTo(capturedUserSpecificationValidator);
        assertThat(userSpecification).isEqualTo(capturedUserSpecificationMapper);
    }

    @Test
    void shouldNotSaveWhenBadInputProvided() throws SpecificationNotValidException {
        //given
        doThrow(SpecificationNotValidException.class).when(validator).validate(any());
        //when
        //then
        assertThatThrownBy(() -> underTest.saveUserSpecification(any()))
                .isInstanceOf(ServiceLayerException.class);
    }

    @Test
    void shouldGenerateFilesWithResultsForEachSubmittedSpecification() {
        //given
        List<UserSpecification> userSpecificationList = List.of(
                new UserSpecification(
                        1L,
                        2,
                        3,
                        10L,
                        Set.of('a', 'b', 'c')
                ));
        given(repository.findAll())
                .willReturn(userSpecificationList);

        //when
        underTest.generateFilesWithResultsForEachSubmittedSpecification();

        //then
        ArgumentCaptor<GenerateRandomStringsTask> captor = ArgumentCaptor.forClass(GenerateRandomStringsTask.class);
        verify(taskExecutor).execute(captor.capture());
        UserSpecification captured = captor
                .getValue()
                .getUserSpecification();

        assertThat(userSpecificationList.get(0)).isEqualTo(captured);
    }

    @Test
    void shouldNotGenerateFilesWhenUserSpecificationIsEmpty() {
        //given
        given(repository.findAll())
                .willReturn(new ArrayList<>());

        //when
        //then
        assertThatThrownBy(() -> underTest.generateFilesWithResultsForEachSubmittedSpecification())
                .isInstanceOf(ServiceLayerException.class)
                .hasMessage("No specification to process");
    }


    @Test
    void getNumberOfRunningThreads() {
        //given
        Integer count = 2;
        given(taskExecutor.getActiveCount()).willReturn(count);

        //when
        Integer returnedCount = underTest.getNumberOfRunningThreads();

        //then
        assertThat(count).isEqualTo(returnedCount);
    }
}