package com.generic.khatabook.service;

import com.generic.khatabook.model.CustomerDTO;
import com.generic.khatabook.model.CustomerSpecificationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

public interface CustomerSpecificationService {
    public CustomerSpecificationDTO getCustomerSpecification(CustomerDTO customerDTO);
}
