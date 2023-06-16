package com.generic.khatabook.service.mapper;

import com.generic.khatabook.common.model.Container;
import com.generic.khatabook.common.model.Mapper;
import com.generic.khatabook.entity.Customer;
import com.generic.khatabook.entity.CustomerProduct;
import com.generic.khatabook.model.CustomerDTO;
import com.generic.khatabook.model.CustomerUpdatable;
import com.generic.khatabook.model.Product;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CustomerMapper implements Mapper<Customer, CustomerDTO, CustomerUpdatable> {

    @Override
    public Customer mapToEntity(final CustomerDTO myCustomer) {
        if (myCustomer == null) {
            return null;
        }
        return Customer.builder().specificationId(myCustomer.specificationId()).customerId(myCustomer.customerId()).khatabookId(myCustomer.khatabookId()).firstName(myCustomer.firstName()).lastName(myCustomer.lastName()).msisdn(myCustomer.msisdn()).build();
    }

    @Override
    public Container<CustomerDTO, CustomerUpdatable> mapToContainer(final Customer customer) {
        if (Objects.isNull(customer)) {
            return Container.empty();
        }

        final CustomerDTO customerDTO = mapToDTO(customer);

        return Container.of(customerDTO, customerDTO.updatable());
    }

    @Override
    public CustomerDTO mapToDTO(final Customer customer) {
        return new CustomerDTO(customer.getCustomerId(), customer.getKhatabookId(), customer.getMsisdn(), customer.getFirstName(), customer.getLastName(), customer.getProducts() != null ? customer.getProducts().stream().map(this::buildProduct).collect(Collectors.toList()) : Collections.emptyList(), customer.getSpecificationId());
    }

    private Product buildProduct(CustomerProduct customerProduct) {
        return new Product(customerProduct.getProductId(), customerProduct.getProductName());
    }
}
