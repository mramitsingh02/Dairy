package com.generic.khatabook.controller;

import com.generic.khatabook.model.OperatorDTO;
import com.generic.khatabook.service.CustomerService;
import com.generic.khatabook.service.IdGeneratorService;
import com.generic.khatabook.service.OperatorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class OperatorController {

    private static final String NULL = null;
    @Autowired
    private OperatorService myOperatorService;
    @Autowired
    private CustomerService myCustomerService;
    @Autowired
    private IdGeneratorService myIdGeneratorService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/operator/operators")
    public ResponseEntity<List<OperatorDTO>> operatorList() {
        return ok(myOperatorService.getAll());
    }

    @PostMapping("/operator/operator")
    public ResponseEntity<OperatorDTO> createOperator(@Valid @RequestBody OperatorDTO operator) {

//        val operatorRequest = operator.copyOf(myIdGeneratorService.generateId());

        if (!myOperatorService.isValid(operator)) {
            myOperatorService.create(operator);
            return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/operatorId/{operatorId}").buildAndExpand(operator.operatorId()).toUri()).body(operator);
        } else {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), operator.operatorId() + " already exist.")).build();
        }
    }


    @GetMapping("/operator/operator/operatorId/{operatorId}")
    public ResponseEntity<OperatorDTO> getById(@PathVariable String operatorId) {
        return ok(myOperatorService.getByOperatorId(operatorId));
    }


    @DeleteMapping("/operator/operator/operatorId/{operatorId}")
    public ResponseEntity deleteById(@PathVariable String operatorId) {
        myOperatorService.delete(operatorId);
        return ok().build();
    }


    @PutMapping("/operator/operator")
    public ResponseEntity<OperatorDTO> updateOperator(@RequestBody OperatorDTO OperatorDTO) {
        return ok(myOperatorService.update(OperatorDTO));
    }

}
