package com.generic.khatabook.specification.services;

import com.generic.khatabook.common.model.Container;
import com.generic.khatabook.common.model.Containers;
import com.generic.khatabook.specification.model.CustomerSpecificationDTO;
import com.generic.khatabook.specification.model.CustomerSpecificationUpdatable;

public interface CustomerSpecificationService {

    Container<CustomerSpecificationDTO, CustomerSpecificationUpdatable> get(String id);

    CustomerSpecificationDTO save(CustomerSpecificationDTO customerSpecificationService);

    Containers<CustomerSpecificationDTO, CustomerSpecificationUpdatable> getByCustomerId(final String customerId);

    Containers<CustomerSpecificationDTO, CustomerSpecificationUpdatable> getCustomerSpecification(String khatabookId, String customerId);

    void delete(CustomerSpecificationDTO customerSpecificationDTO);

    Container<CustomerSpecificationDTO, CustomerSpecificationUpdatable> getCustomerSpecification(String specificationId);

    CustomerSpecificationDTO update(CustomerSpecificationDTO build);

}
