package com.generic.khatabook.service.impl;

import com.generic.khatabook.exceptions.AppEntity;
import com.generic.khatabook.exceptions.NotFoundException;
import com.generic.khatabook.exchanger.CustomerSpecificationClient;
import com.generic.khatabook.model.CustomerDTO;
import com.generic.khatabook.model.CustomerSpecificationDTO;
import com.generic.khatabook.service.CustomerSpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Objects;

@Service
public class CustomerSpecificationServiceImpl implements CustomerSpecificationService {
    private final CustomerSpecificationClient customerSpecificationClient;

    @Autowired
    public CustomerSpecificationServiceImpl(CustomerSpecificationClient customerSpecificationClient) {
        this.customerSpecificationClient = customerSpecificationClient;
    }


    @Override
    public CustomerSpecificationDTO getCustomerSpecification(CustomerDTO customerDTO) {
        try {
            final ResponseEntity<CustomerSpecificationDTO> responseEntity = customerSpecificationClient.getById(customerDTO.khatabookId(), customerDTO.customerId(), customerDTO.specificationId());
            if (Objects.isNull(responseEntity)) {
                throw new NotFoundException(AppEntity.SPECIFICATION, customerDTO.specificationId());
            } else if (responseEntity.getBody() != null) {
                return responseEntity.getBody();
            }

        } catch (WebClientResponseException e) {
            throw new NotFoundException(AppEntity.SPECIFICATION, customerDTO.specificationId());
        }
        return null;
    }
}




