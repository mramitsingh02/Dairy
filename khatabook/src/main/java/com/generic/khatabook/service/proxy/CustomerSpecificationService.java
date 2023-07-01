package com.generic.khatabook.service.proxy;

import com.generic.khatabook.model.CustomerDTO;
import com.generic.khatabook.model.CustomerSpecificationDTO;

public interface CustomerSpecificationService {
    public CustomerSpecificationDTO getCustomerSpecification(CustomerDTO customerDTO);
}
