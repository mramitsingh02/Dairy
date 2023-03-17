package com.generic.khatabook.specification.controller;

import com.generic.khatabook.exceptions.AppEntity;
import com.generic.khatabook.exceptions.NotFoundException;
import com.generic.khatabook.specification.model.SpecificationDTO;
import com.generic.khatabook.specification.services.SpecificationManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static java.util.Objects.isNull;

@RestController("specification")
public class SpecificationManagementController {

    private SpecificationManagementService mySpecificationManagementService;

    @Autowired
    public SpecificationManagementController(final SpecificationManagementService specificationManagementService) {
        mySpecificationManagementService = specificationManagementService;
    }

    @PostMapping(path = "/specification")
    public ResponseEntity<?> create(@RequestBody SpecificationDTO specification) {

        if (mySpecificationManagementService.findById(specification.id())) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), specification.id() + " already exist.")).build();
        }
        final SpecificationDTO savedSpecification = mySpecificationManagementService.addSpecification(specification);

        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/id/{id}").buildAndExpand(savedSpecification.id()).toUri()).body(savedSpecification);
    }

    @GetMapping(path = "/specifications")
    public ResponseEntity<?> getAllSpecification() {
        return ResponseEntity.ok(mySpecificationManagementService.findAll());
    }

    @GetMapping("specification/id/{id}")
    public ResponseEntity<SpecificationDTO> getSpecificationId(@PathVariable String id) {

        final SpecificationDTO specification = mySpecificationManagementService.find(id);
        if (isNull(specification)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.SPECIFICATION, id).get()).build();
        }
        return ResponseEntity.ok(specification);
    }

    @DeleteMapping("specification/id/{id}")
    public ResponseEntity<SpecificationDTO> deleteSpecificationId(@PathVariable String id) {
        final SpecificationDTO specification = mySpecificationManagementService.find(id);
        if (isNull(specification)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.SPECIFICATION, id).get()).build();
        }
        mySpecificationManagementService.deleteSpecifications(specification);
        return ResponseEntity.ok(specification);
    }

    @PutMapping("specification/id/{id}")
    public ResponseEntity<?> updateSpecificationId(@PathVariable String id, @RequestBody SpecificationDTO specificationDTO) {
        final SpecificationDTO specification = mySpecificationManagementService.find(id);
        if (isNull(specification)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.SPECIFICATION, id).get()).build();
        }
        return ResponseEntity.ok(mySpecificationManagementService.updateSpecification(specificationDTO));
    }
}

