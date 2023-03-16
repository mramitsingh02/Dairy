package com.generic.khatabook.controller;

import com.generic.khatabook.model.Greeting;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @GetMapping(path = "/hello-world")
    public Greeting helloWorld() {
        return new Greeting("");
    }


    @GetMapping(path = "/hello/to/{to}")
    public Greeting helloWorldToPerson(final @PathVariable String to) {
        return new Greeting(to);
    }


}
