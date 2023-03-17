package com.generic.khatabook.exchanger;

import com.generic.khatabook.model.SpecificationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange("specification")

public interface SpecificationClientExchanger {

    @GetMapping(path = "/specifications")
    public ResponseEntity<List<SpecificationDTO>> getAllSpecification();

    @GetMapping("specification/id/{id}")
    public ResponseEntity<SpecificationDTO> getSpecificationId(@PathVariable String id);


}
