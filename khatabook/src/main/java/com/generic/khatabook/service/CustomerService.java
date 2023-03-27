package com.generic.khatabook.service;

import com.generic.khatabook.model.CustomerDTO;
import com.generic.khatabook.model.CustomerUpdatable;
import org.springframework.stereotype.Component;

import java.util.Set;
import com.generic.khatabook.common.model.Container;

@Component
public interface CustomerService {
    boolean isValid(CustomerDTO customerDTO);

    Container<CustomerDTO, CustomerUpdatable> getByCustomerId(String msisdn);

    CustomerDTO getByMsisdn(String msisdn);

    CustomerDTO saveAndUpdate(CustomerDTO customerDTO);

    CustomerDTO delete(String customerId, String msidn);

    Set<CustomerDTO> getAll(final String khatabookId);

    CustomerDTO getCustomerByMsisdn(String khatabookId, String msisdn);
}
