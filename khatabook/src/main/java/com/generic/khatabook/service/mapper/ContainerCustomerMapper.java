package com.generic.khatabook.service.mapper;

import com.generic.khatabook.entity.Customer;
import com.generic.khatabook.model.Container;
import com.generic.khatabook.model.CustomerDTO;
import com.generic.khatabook.model.CustomerUpdatable;
import org.springframework.stereotype.Component;

@Component
public class ContainerCustomerMapper {

    public Container<CustomerDTO, CustomerUpdatable> mapToPojo(Customer myCustomer) {

        if (myCustomer == null) {
            return null;
        }


        final CustomerDTO customerDTO = new CustomerDTO(myCustomer.getCustomerId(), myCustomer.getKhatabookId(), myCustomer.getMsisdn(),
                                                        myCustomer.getFirstName(), myCustomer.getLastName(), null,
                                                        myCustomer.getSpecificationId());

        return Container.of(customerDTO, customerDTO.updatable());
    }

    public Customer mapToDTO(CustomerDTO myCustomer) {
        if (myCustomer == null) {
            return null;
        }
        return Customer.builder().productId(myCustomer.productId()).specificationId(myCustomer.specificationId()).customerId(myCustomer.customerId()).khatabookId(myCustomer.khatabookId()).firstName(myCustomer.firstName()).lastName(myCustomer.lastName()).msisdn(myCustomer.msisdn()).build();
    }
}
