package com.generic.khatabook.specification.services;

import com.generic.khatabook.specification.repository.CustomerSpecificationRepository;
import com.generic.khatabook.specification.services.CustomerSpecificationService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CustomerSpecificationServiceImpl implements CustomerSpecificationService {

    private CustomerSpecificationRepository myCustomerSpecificationRepository;

    @Autowired
    public CustomerSpecificationServiceImpl(final CustomerSpecificationRepository thatCustomerSpecificationRepository) {
        this.myCustomerSpecificationRepository = thatCustomerSpecificationRepository;
    }


}
