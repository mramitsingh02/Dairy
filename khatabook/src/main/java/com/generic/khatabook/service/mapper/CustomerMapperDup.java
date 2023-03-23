package com.generic.khatabook.service.mapper;

import com.generic.khatabook.entity.Customer;
import com.generic.khatabook.model.CustomerDTO;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapperDup {

    public CustomerDTO mapToPojo(Customer myCustomer) {

        if (myCustomer == null) {
            return null;
        }
        return new CustomerDTO(myCustomer.getCustomerId(), myCustomer.getKhatabookId(), myCustomer.getMsisdn(), myCustomer.getFirstName(), myCustomer.getLastName(), myCustomer.getProductId(), myCustomer.getSpecificationId());
    }

    public Customer mapToDTO(CustomerDTO myCustomer) {
        if (myCustomer == null) {
            return null;
        }
        return Customer.builder().productId(myCustomer.productId()).specificationId(myCustomer.specificationId()).customerId(myCustomer.customerId()).khatabookId(myCustomer.khatabookId()).firstName(myCustomer.firstName()).lastName(myCustomer.lastName()).msisdn(myCustomer.msisdn()).build();
    }
}
