package michalmlynarczyk.recruitmenttask.controller;

import michalmlynarczyk.recruitmenttask.dto.UserSpecificationDTO;
import michalmlynarczyk.recruitmenttask.service.Service;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/generator")
public class Controller {
    private final Service service;
    private final ModelMapper modelMapper;

    @Autowired
    public Controller(Service service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<Object> saveUserSpecification(
            @Valid @RequestBody UserSpecificationDTO userSpecificationDTO) {
        UserSpecificationDTO savedUserSpecification = modelMapper.map(
                service.saveUserSpecification(userSpecificationDTO), UserSpecificationDTO.class);

        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/generator")
                .toUriString());
        return ResponseEntity.created(uri).body(savedUserSpecification);
    }

    @GetMapping
    public ResponseEntity<Object> generateFilesWithResultsForEachSubmittedSpecification() {
        service.generateFilesWithResultsForEachSubmittedSpecification();
        return ResponseEntity.ok("Files created in 'src/main/resources/generatedfiles");
    }

    @GetMapping("/thread")
    public ResponseEntity<Object> getNumberOfRunningThreads() {
        return ResponseEntity.ok("Currently running jobs: " + service.getNumberOfRunningThreads());
    }
}