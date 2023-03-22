package com.generic.khatabook.specification.services;

import com.generic.khatabook.common.model.Container;
import com.generic.khatabook.common.model.Containers;
import com.generic.khatabook.specification.model.CustomerSpecificationDTO;
import com.generic.khatabook.specification.model.CustomerSpecificationUpdatable;

public interface CustomerSpecificationService {

    Container<CustomerSpecificationDTO, CustomerSpecificationUpdatable> get(String id);

    CustomerSpecificationDTO save(CustomerSpecificationDTO customerSpecificationService);

    Containers<CustomerSpecificationDTO, CustomerSpecificationUpdatable> getByCustomerId(final String customerId);
}
