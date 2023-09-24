package com.generic.khatabook.controller;

import com.generic.khatabook.common.model.Greeting;
import com.generic.khatabook.entity.Employee;
import com.generic.khatabook.entity.WorkStation;
import com.generic.khatabook.repository.EmployeeRepository;
import com.generic.khatabook.repository.WorkStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @Autowired
    private EmployeeRepository repository;
    @Autowired
    private WorkStationRepository workStationRepository;

    @GetMapping(path = "/hello-world")
    public Greeting helloWorld() {

        WorkStation workShop = WorkStation.builder().build();
        Employee employee = Employee.builder()
                .workStation(workShop)
                .build();

        repository.save(employee);
        workShop.setEmployee(employee);
        workStationRepository.save(workShop);


        return new Greeting("");
    }


    @GetMapping(path = "/hello/to/{to}")
    public Greeting helloWorldToPerson(final @PathVariable String to) {
        return new Greeting(to);
    }


}
