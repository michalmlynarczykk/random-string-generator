package michalmlynarczyk.recruitmenttask.controller;

import michalmlynarczyk.recruitmenttask.dto.UserSpecificationDTO;
import michalmlynarczyk.recruitmenttask.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/generator")
public class Controller {
    private final Service service;

    @Autowired
    public Controller(Service service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> saveUserSpecification(
            @Valid @RequestBody UserSpecificationDTO userSpecificationDTO) {
        service.saveUserSpecification(userSpecificationDTO);
        return ResponseEntity.ok().build();
    }
}