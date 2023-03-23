package com.generic.khatabook.service.mapper;

import com.generic.khatabook.common.model.Container;
import com.generic.khatabook.common.model.Mapper;
import com.generic.khatabook.entity.Customer;
import com.generic.khatabook.model.CustomerDTO;
import com.generic.khatabook.model.CustomerUpdatable;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CustomerMapper implements Mapper<Customer, CustomerDTO, CustomerUpdatable> {

    @Override
    public Customer mapToEntity(final CustomerDTO myCustomer) {
        if (myCustomer == null) {
            return null;
        }
        return Customer.builder()
                       .productId(myCustomer.productId())
                       .specificationId(myCustomer.specificationId())
                       .customerId(myCustomer.customerId())
                       .khatabookId(myCustomer.khatabookId())
                       .firstName(myCustomer.firstName())
                       .lastName(myCustomer.lastName())
                       .msisdn(myCustomer.msisdn())
                       .build();
    }

    @Override
    public Container<CustomerDTO, CustomerUpdatable> mapToContainer(final Customer customer) {
        if (Objects.isNull(customer)) {
            return Container.empty();
        }

        final CustomerDTO customerDTO = new CustomerDTO(customer.getCustomerId(),
                                                        customer.getKhatabookId(),
                                                        customer.getMsisdn(),
                                                        customer.getFirstName(),
                                                        customer.getLastName(),
                                                        customer.getProductId(),
                                                        customer.getSpecificationId());

        return Container.of(customerDTO, customerDTO.updatable());
    }

    @Override
    public CustomerDTO mapToDTO(final Customer customer) {
        return new CustomerDTO(customer.getCustomerId(), customer.getKhatabookId(), customer.getMsisdn(),
                               customer.getFirstName(), customer.getLastName(), customer.getProductId(),
                               customer.getSpecificationId());
    }
}
